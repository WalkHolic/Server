package com.promenade.promenadeapp.controller.Park;

import com.promenade.promenadeapp.domain.Park.Park;
import com.promenade.promenadeapp.domain.Park.ParkReview;
import com.promenade.promenadeapp.domain.User.User;
import com.promenade.promenadeapp.domain.User.UserRoad;
import com.promenade.promenadeapp.dto.Park.ParkReviewResponseDto;
import com.promenade.promenadeapp.dto.ResponseDto;
import com.promenade.promenadeapp.dto.ReviewRequestDto;
import com.promenade.promenadeapp.dto.User.UserRoadUpdateRequestDto;
import com.promenade.promenadeapp.service.Park.ParkReviewService;
import com.promenade.promenadeapp.service.Park.ParkService;
import com.promenade.promenadeapp.service.Road.StorageService;
import com.promenade.promenadeapp.service.User.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/park")
public class ParkReviewController {

    private final ParkReviewService parkReviewService;

    private final UserService userService;

    private final ParkService parkService;

    private final StorageService storageService;

    @GetMapping("/{id}/review")
    public ResponseEntity<?> findByParkId(@PathVariable Long id) {
        List<ParkReview> parkReviews = parkReviewService.findByParkId(id);
        if (parkReviews.isEmpty()) {
            ResponseDto response = ResponseDto.builder()
                    .error("해당 공원의 리뷰가 없습니다.")
                    .build();
            return ResponseEntity.badRequest().body(response);
        }
        List<ParkReviewResponseDto> responseDtos = parkReviews.stream().map(ParkReviewResponseDto::new).collect(Collectors.toList());
        ResponseDto response = ResponseDto.<ParkReviewResponseDto>builder()
                .data(responseDtos)
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/review")
    public ResponseEntity<?> postReview(@AuthenticationPrincipal String googleId,
                                        @PathVariable Long id,
                                        @RequestPart(required = false) MultipartFile thumbnail,
                                        @RequestPart ReviewRequestDto reviewRequestDto) {
        try {
            User userByGoogleId = userService.findByGoogleId(googleId);
            Park parkById = parkService.findById(id);

            // 사진 파일 있을때만 s3 접근해서 업로드 처리
            String pictureUrl = null;
            if (!(thumbnail == null || thumbnail.isEmpty())) {
                pictureUrl = storageService.uploadFile(thumbnail);
            }

            ParkReview parkReview = ParkReview.builder()
                    .id(null) // save하면서 자동 저장
                    .score(reviewRequestDto.getScore())
                    .content(reviewRequestDto.getContent())
                    .pngPath(pictureUrl)
                    .user(userByGoogleId)
                    .park(parkById)
                    .build();
            Long reviewId = parkReviewService.save(parkReview);
            log.info("review saved. id=" + reviewId);

            List<ParkReview> parkReviews = parkReviewService.findByParkId(id);
            List<ParkReviewResponseDto> responseDtos = parkReviews.stream().map(ParkReviewResponseDto::new).collect(Collectors.toList());

            ResponseDto response = ResponseDto.<ParkReviewResponseDto>builder()
                    .data(responseDtos)
                    .build();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ResponseDto response = ResponseDto.builder()
                    .error(e.getMessage())
                    .build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    @DeleteMapping("/review/{id}")
    public ResponseEntity<?> deleteReview(@AuthenticationPrincipal String googleId,
                                          @PathVariable Long id) {
        try {
            User userByGoogleId = userService.findByGoogleId(googleId);
            ParkReview reviewById = parkReviewService.findById(id);

            if (userByGoogleId.getId() != reviewById.getUser().getId()) {
                ResponseDto response = ResponseDto.builder()
                        .error("요청한 공원리뷰 id가 당신의 리뷰가 아닙니다. id = " + id)
                        .build();
                return ResponseEntity.badRequest().body(response);
            }
            parkReviewService.delete(id);
            log.info("요청된 공원 리뷰가 삭제되었습니다. id=" + id);

            return findByParkId(reviewById.getPark().getId());
        } catch (Exception e) {
            ResponseDto response = ResponseDto.builder()
                    .error(e.getMessage())
                    .build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping("/review/{id}")
    public ResponseEntity<?> updateReview(@AuthenticationPrincipal String googleId,
                                          @PathVariable Long id,
                                          @RequestPart(required = false) MultipartFile thumbnail,
                                          @RequestPart ReviewRequestDto reviewRequestDto) {
        try {

            User foundUser = userService.findByGoogleId(googleId);
            ParkReview foundParkReview = parkReviewService.findById(id);

            if (foundUser.getId() != foundParkReview.getUser().getId()) {
                ResponseDto response = ResponseDto.builder()
                        .error("접근 가능한 공원 리뷰가 아닙니다.")
                        .build();
                return ResponseEntity.badRequest().body(response);
            }

            // 사진 파일 있을때만 s3 접근해서 업로드 처리
            String pictureUrl = null;
            if (!(thumbnail == null || thumbnail.isEmpty())) {
                pictureUrl = storageService.uploadFile(thumbnail);
            } else if (foundParkReview.getPngPath() != null) {
                pictureUrl = foundParkReview.getPngPath();
            }
            ParkReview updatedParkReview = parkReviewService.update(id, reviewRequestDto, pictureUrl);
            Long savedId = parkReviewService.save(updatedParkReview); // 업데이트 후 저장해줘야 DB에 반영된다.
            log.info("공원 리뷰 업데이트 완료. id=" + savedId);

            List<ParkReview> parkReviews = parkReviewService.findByUserId(foundUser.getId());
            List<ParkReviewResponseDto> responseDtos = parkReviews.stream().map(ParkReviewResponseDto::new).collect(Collectors.toList());
            ResponseDto response = ResponseDto.<ParkReviewResponseDto>builder()
                    .data(responseDtos)
                    .build();
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            ResponseDto response = ResponseDto.builder().error(e.getMessage()).build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/user/review")
    public ResponseEntity<?> getMyReviews(@AuthenticationPrincipal String googleId) {
        try {
            User user = userService.findByGoogleId(googleId);
            List<ParkReview> parkReviews = parkReviewService.findByUserId(user.getId());
            List<ParkReviewResponseDto> responseDtos = parkReviews.stream().map(ParkReviewResponseDto::new).collect(Collectors.toList());

            ResponseDto response = ResponseDto.<ParkReviewResponseDto>builder()
                    .data(responseDtos)
                    .build();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ResponseDto response = ResponseDto.builder()
                    .error(e.getMessage())
                    .build();
            return ResponseEntity.badRequest().body(response);
        }
    }
}
