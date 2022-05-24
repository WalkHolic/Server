package com.promenade.promenadeapp.controller.User;

import com.promenade.promenadeapp.domain.Road.Road;
import com.promenade.promenadeapp.domain.Road.RoadReview;
import com.promenade.promenadeapp.domain.User.User;
import com.promenade.promenadeapp.domain.User.UserRoad;
import com.promenade.promenadeapp.domain.User.UserRoadReview;
import com.promenade.promenadeapp.dto.ResponseDto;
import com.promenade.promenadeapp.dto.ReviewRequestDto;
import com.promenade.promenadeapp.dto.Road.RoadReviewResponseDto;
import com.promenade.promenadeapp.dto.User.UserRoadReviewResponseDto;
import com.promenade.promenadeapp.service.Road.StorageService;
import com.promenade.promenadeapp.service.User.UserRoadReviewService;
import com.promenade.promenadeapp.service.User.UserRoadService;
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
@RequestMapping("/userRoad")
public class UserRoadReviewController {

    private final UserRoadReviewService userRoadReviewService;

    private final UserRoadService userRoadService;

    private final UserService userService;

    private final StorageService storageService;

    @GetMapping("/{id}/review")
    public ResponseEntity<?> findByUserRoadId(@PathVariable Long id) {
        try {
            UserRoad userRoad = userRoadService.findById(id);
            // 공유되지 않은 산책로 접근 시, 에러 반환
            if (!userRoad.isShared()) {
                ResponseDto response = ResponseDto.builder()
                        .error("공유되지 않은 산책로에 접근하였습니다. id=" + id)
                        .build();
                return ResponseEntity.badRequest().body(response);
            }

            List<UserRoadReview> userRoadReviews = userRoadReviewService.findByUserRoadId(id);

            if (userRoadReviews.isEmpty()) {
                ResponseDto response = ResponseDto.builder()
                        .error("해당 공유 산책로의 리뷰가 없습니다.")
                        .build();
                return ResponseEntity.badRequest().body(response);
            }
            List<UserRoadReviewResponseDto> responseDtos = userRoadReviews.stream().map(UserRoadReviewResponseDto::new).collect(Collectors.toList());
            ResponseDto response = ResponseDto.<UserRoadReviewResponseDto>builder()
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

            UserRoad userRoad = userRoadService.findById(id);
            // 공유되지 않은 산책로 접근 시, 에러 반환
            if (!userRoad.isShared()) {
                ResponseDto response = ResponseDto.builder()
                        .error("공유되지 않은 산책로에 접근하였습니다. id=" + id)
                        .build();
                return ResponseEntity.badRequest().body(response);
            }
            User user = userService.findByGoogleId(googleId);

            // 사진 파일 있을때만 s3 접근해서 업로드 처리
            String pictureUrl = null;
            if (!(thumbnail == null || thumbnail.isEmpty())) {
                pictureUrl = storageService.uploadFile(thumbnail);
            }

            UserRoadReview userRoadReview = UserRoadReview.builder()
                    .id(null) // save하면서 자동 저장
                    .score(reviewRequestDto.getScore())
                    .content(reviewRequestDto.getContent())
                    .pngPath(pictureUrl)
                    .user(user)
                    .userRoad(userRoad)
                    .build();
            UserRoadReview savedReview = userRoadReviewService.save(userRoadReview);
            log.info("Shared Road's review is saved. id=" + savedReview.getId());

            List<UserRoadReview> userRoadReviews = userRoadReviewService.findByUserRoadId(id);
            List<UserRoadReviewResponseDto> responseDtos = userRoadReviews.stream().map(UserRoadReviewResponseDto::new).collect(Collectors.toList());

            ResponseDto response = ResponseDto.<UserRoadReviewResponseDto>builder()
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
