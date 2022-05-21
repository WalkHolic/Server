package com.promenade.promenadeapp.controller.Park;

import com.promenade.promenadeapp.domain.Park.Park;
import com.promenade.promenadeapp.domain.Park.ParkReview;
import com.promenade.promenadeapp.domain.User.User;
import com.promenade.promenadeapp.dto.Park.ParkReviewResponseDto;
import com.promenade.promenadeapp.dto.ResponseDto;
import com.promenade.promenadeapp.dto.Road.RoadReviewRequestDto;
import com.promenade.promenadeapp.service.Park.ParkReviewService;
import com.promenade.promenadeapp.service.Park.ParkService;
import com.promenade.promenadeapp.service.User.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/park/review")
public class ParkReviewController {

    private final ParkReviewService parkReviewService;

    private final UserService userService;

    private final ParkService parkService;

    @GetMapping("/{parkId}")
    public ResponseEntity<?> findByParkId(@PathVariable Long parkId) {
        List<ParkReview> parkReviews = parkReviewService.findByParkId(parkId);
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

    @PostMapping("/{parkId}")
    public ResponseEntity postReview(@AuthenticationPrincipal String googleId,
                                     @PathVariable Long parkId, @RequestBody RoadReviewRequestDto requestDto) {
        User userByGoogleId = userService.findByGoogleId(googleId);
        Park parkById = parkService.findById(parkId);

        ParkReview parkReview = ParkReview.builder()
                .id(null) // save하면서 자동 저장
                .score(requestDto.getScore())
                .content(requestDto.getContent())
                .pngPath(requestDto.getPng_path())
                .user(userByGoogleId)
                .park(parkById)
                .build();
        Long reviewId = parkReviewService.save(parkReview);
        log.info("review saved. id="+reviewId);

        return findByParkId(parkId);
    }
}
