package com.promenade.promenadeapp.controller.User;

import com.promenade.promenadeapp.domain.User.User;
import com.promenade.promenadeapp.domain.User.UserRoad;
import com.promenade.promenadeapp.dto.ResponseDto;
import com.promenade.promenadeapp.dto.UserRoadRequestDto;
import com.promenade.promenadeapp.dto.UserRoadResponseDto;
import com.promenade.promenadeapp.service.User.UserRoadService;
import com.promenade.promenadeapp.service.User.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user/road")
public class UserRoadController {

    private final UserService userService;

    private final UserRoadService userRoadService;

    @GetMapping
    public ResponseEntity<?> getUserRoads(@AuthenticationPrincipal String googleId) {
        List<UserRoad> userRoads = userRoadService.getUserRoads(googleId);
        if (userRoads.isEmpty()) {
            ResponseDto response = ResponseDto.builder()
                    .error("사용자의 커스텀 산책로가 없습니다. userGoogleId = " + googleId)
                    .build();
            return ResponseEntity.badRequest().body(response);
        }
        List<UserRoadResponseDto> responseDtos = userRoads.stream().map(UserRoadResponseDto::new).collect(Collectors.toList());
        ResponseDto<UserRoadResponseDto> response = ResponseDto.<UserRoadResponseDto>builder()
                .data(responseDtos)
                .build();

        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    public ResponseEntity<?> saveUserRoad(@AuthenticationPrincipal String googleId,
                                          @RequestBody UserRoadRequestDto requestDto) {
        try {
            User foundUserByGoogleId = userService.findByGoogleId(googleId);

            UserRoad userRoad = UserRoad.builder()
                    .id(null) // saveUserRoad()에서 자동 추가
                    .userGoogleId(googleId) // googleId 추가
                    .trailName(requestDto.getTrailName())
                    .description(requestDto.getDescription())
                    .distance(requestDto.getDistance())
                    .startAddr(requestDto.getStartAddr())
                    .trailPoint(requestDto.getTrailPoint())
                    .user(foundUserByGoogleId) // user 추가
                    .build();
            List<UserRoad> userRoads = userRoadService.saveUserRoad(userRoad);

            List<UserRoadResponseDto> responseDtos = userRoads.stream().map(UserRoadResponseDto::new).collect(Collectors.toList());
            ResponseDto<UserRoadResponseDto> response = ResponseDto.<UserRoadResponseDto>builder()
                    .data(responseDtos)
                    .build();

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ResponseDto response = ResponseDto.builder().error(e.getMessage()).build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> deleteUserRoad(@AuthenticationPrincipal String googleId,
                                            @PathVariable Long id) {
        try {
            Long userId = userService.getUserIdByGoogleId(googleId);
            UserRoad userRoad = userRoadService.findById(id);
            if (userId != userRoad.getUser().getId()) {
                ResponseDto response = ResponseDto.builder()
                        .error("요청한 산책로 id가 로그인한 당신의 산책로가 아닙니다. roadId = " + id)
                        .build();
                return ResponseEntity.badRequest().body(response);
            }

            List<UserRoad> userRoads = userRoadService.deleteUserRoad(userRoad);

            List<UserRoadResponseDto> responseDtos = userRoads.stream().map(UserRoadResponseDto::new).collect(Collectors.toList());
            ResponseDto<UserRoadResponseDto> response = ResponseDto.<UserRoadResponseDto>builder()
                    .data(responseDtos)
                    .build();

            return ResponseEntity.ok().body(response);

        } catch (Exception e) {
            ResponseDto response = ResponseDto.builder().error(e.getMessage()).build();
            return ResponseEntity.badRequest().body(response);
        }
    }

}
