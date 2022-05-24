package com.promenade.promenadeapp.controller.User;

import com.promenade.promenadeapp.domain.Road.RoadReview;
import com.promenade.promenadeapp.domain.User.UserRoad;
import com.promenade.promenadeapp.domain.User.UserRoadReview;
import com.promenade.promenadeapp.dto.ResponseDto;
import com.promenade.promenadeapp.dto.Road.RoadReviewResponseDto;
import com.promenade.promenadeapp.dto.User.UserRoadReviewResponseDto;
import com.promenade.promenadeapp.service.User.UserRoadReviewService;
import com.promenade.promenadeapp.service.User.UserRoadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/userRoad")
public class UserRoadReviewController {

    private final UserRoadReviewService userRoadReviewService;

    private final UserRoadService userRoadService;

    @GetMapping("/{id}/review")
    public ResponseEntity<?> findByUserRoadId(@AuthenticationPrincipal String googleId, @PathVariable Long id) {
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
}
