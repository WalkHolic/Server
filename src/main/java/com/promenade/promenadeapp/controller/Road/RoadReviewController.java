package com.promenade.promenadeapp.controller.Road;

import com.promenade.promenadeapp.domain.Park.ParkReview;
import com.promenade.promenadeapp.domain.Road.Road;
import com.promenade.promenadeapp.domain.Road.RoadReview;
import com.promenade.promenadeapp.domain.User.User;
import com.promenade.promenadeapp.dto.Park.ParkReviewResponseDto;
import com.promenade.promenadeapp.dto.ResponseDto;
import com.promenade.promenadeapp.dto.ReviewRequestDto;
import com.promenade.promenadeapp.dto.Road.RoadReviewResponseDto;
import com.promenade.promenadeapp.service.Road.RoadReviewService;
import com.promenade.promenadeapp.service.Road.RoadService;
import com.promenade.promenadeapp.service.Road.StorageService;
import com.promenade.promenadeapp.service.User.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/road")
public class RoadReviewController {

    private final RoadReviewService roadReviewService;

    private final UserService userService;

    private final RoadService roadService;

    private final StorageService storageService;

    @GetMapping("/{id}/review")
    public ResponseEntity<?> findByRoadId(@PathVariable Long id) {
        try {

            List<RoadReview> roadReviews = roadReviewService.findByRoadId(id);
            if (roadReviews.isEmpty()) {
                ResponseDto response = ResponseDto.builder()
                        .error("해당 산책로의 리뷰가 없습니다.")
                        .build();
                return ResponseEntity.badRequest().body(response);
            }
            List<RoadReviewResponseDto> responseDtos = roadReviews.stream().map(RoadReviewResponseDto::new).collect(Collectors.toList());
            ResponseDto response = ResponseDto.<RoadReviewResponseDto>builder()
                    .data(responseDtos)
                    .build();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ResponseDto response = ResponseDto.builder().error(e.getMessage()).build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/{id}/review")
    public ResponseEntity postReview(@AuthenticationPrincipal String googleId,
                                     @PathVariable Long id,
                                     @RequestPart(required = false) MultipartFile thumbnail,
                                     @RequestPart ReviewRequestDto reviewRequestDto) {
        try {

            User userByGoogleId = userService.findByGoogleId(googleId);
            Road roadById = roadService.findById(id);

            // 사진 파일 있을때만 s3 접근해서 업로드 처리
            String pictureUrl = null;
            if (!(thumbnail == null || thumbnail.isEmpty())) {
                pictureUrl = storageService.uploadFile(thumbnail);
            }

            RoadReview roadReview = RoadReview.builder()
                    .id(null) // save하면서 자동 저장
                    .score(reviewRequestDto.getScore())
                    .content(reviewRequestDto.getContent())
                    .pngPath(pictureUrl)
                    .user(userByGoogleId)
                    .road(roadById)
                    .build();
            RoadReview savedReview = roadReviewService.save(roadReview);
            log.info("review saved. id=" + savedReview.getId());

            List<RoadReview> roadReviews = roadReviewService.findByRoadId(id);
            List<RoadReviewResponseDto> responseDtos = roadReviews.stream().map(RoadReviewResponseDto::new).collect(Collectors.toList());

            ResponseDto response = ResponseDto.<RoadReviewResponseDto>builder()
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
            RoadReview reviewById = roadReviewService.findById(id);

            if (userByGoogleId.getId() != reviewById.getUser().getId()) {
                ResponseDto response = ResponseDto.builder()
                        .error("요청한 산책로리뷰 id가 당신의 리뷰가 아닙니다. id = " + id)
                        .build();
                return ResponseEntity.badRequest().body(response);
            }
            roadReviewService.delete(id);
            log.info("요청된 산책로 리뷰가 삭제되었습니다. id=" + id);

            return findByRoadId(reviewById.getRoad().getId());
        } catch (Exception e) {
            ResponseDto response = ResponseDto.builder()
                    .error(e.getMessage())
                    .build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/user/review")
    public ResponseEntity<?> getMyReviews(@AuthenticationPrincipal String googleId) {
        try {
            User user = userService.findByGoogleId(googleId);
            List<RoadReview> roadReviews = roadReviewService.findByUserId(user.getId());
            List<RoadReviewResponseDto> responseDtos = roadReviews.stream().map(RoadReviewResponseDto::new).collect(Collectors.toList());

            ResponseDto response = ResponseDto.<RoadReviewResponseDto>builder()
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

    @PutMapping("/review/{id}")
    public ResponseEntity<?> updateReview(@AuthenticationPrincipal String googleId,
                                          @PathVariable Long id,
                                          @RequestPart(required = false) MultipartFile thumbnail,
                                          @RequestPart ReviewRequestDto reviewRequestDto) {
        try {

            User foundUser = userService.findByGoogleId(googleId);
            RoadReview foundRoadReview = roadReviewService.findById(id);

            if (foundUser.getId() != foundRoadReview.getUser().getId()) {
                ResponseDto response = ResponseDto.builder()
                        .error("접근 가능한 산책로 리뷰가 아닙니다.")
                        .build();
                return ResponseEntity.badRequest().body(response);
            }

            // 사진 파일 있을때만 s3 접근해서 업로드 처리
            String pictureUrl = null;
            if (!(thumbnail == null || thumbnail.isEmpty())) {
                pictureUrl = storageService.uploadFile(thumbnail);
            } else if (foundRoadReview.getPngPath() != null) {
                pictureUrl = foundRoadReview.getPngPath();
            }

            RoadReview roadReview = roadReviewService.update(id, reviewRequestDto, pictureUrl);
            RoadReview savedId = roadReviewService.save(roadReview); // 업데이트 후 저장해야만 DB에 반영이 됨.
            log.info("산책로 리뷰 업데이트 완료. id=" + savedId);

            List<RoadReview> roadReviews = roadReviewService.findByUserId(foundUser.getId());
            List<RoadReviewResponseDto> responseDtos = roadReviews.stream().map(RoadReviewResponseDto::new).collect(Collectors.toList());
            ResponseDto response = ResponseDto.<RoadReviewResponseDto>builder()
                    .data(responseDtos)
                    .build();
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            ResponseDto response = ResponseDto.builder().error(e.getMessage()).build();
            return ResponseEntity.badRequest().body(response);
        }
    }
}
