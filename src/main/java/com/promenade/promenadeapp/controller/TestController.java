package com.promenade.promenadeapp.controller;

import com.promenade.promenadeapp.domain.User.User;
import com.promenade.promenadeapp.domain.User.UserRoad;
import com.promenade.promenadeapp.dto.*;
import com.promenade.promenadeapp.security.TokenProvider;
import com.promenade.promenadeapp.service.User.UserRoadService;
import com.promenade.promenadeapp.service.User.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/test")
public class TestController {

    private final UserService userService;

    private final UserRoadService userRoadService;

    private final TokenProvider tokenProvider;

    @PostMapping("/user")
    public ResponseEntity<?> saveUserRoad(@RequestBody TestRequestDto requestDto) {
        try {
            User foundUserByGoogleId = userService.findByGoogleId(requestDto.getGoogleId());

            UserRoad userRoad = UserRoad.builder()
                    .id(null) // saveUserRoad()에서 자동 추가
                    .userGoogleId(requestDto.getGoogleId()) // googleId 추가
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

    @PostMapping("/token")
    public ResponseEntity<?> saveOrLogin(@RequestBody String idTokenString) throws GeneralSecurityException, IOException {
        try {
            UserDto userDto = userService.verifyGoogleIdToken(idTokenString);
            if (userDto == null) { // id token이 유효하지 않을 때 예외 처리
                ResponseDto<Object> responseDto = ResponseDto.builder()
                        .error("google id token이 유효하지 않습니다.").build();
                return ResponseEntity.badRequest().body(responseDto);
            }

            // Login (이미 있는 사용자임)
            if (userService.existsByGoogleId(userDto.getGoogleId())) {
                User user = userService.getByCredentials(userDto.getGoogleId(), userDto.getEmail());
                if (user != null) {
                    String token = tokenProvider.createToken(user);
                    UserDto responseUserDto = UserDto.builder()
                            .id(user.getId())
                            .googleId(user.getGoogleId())
                            .email(user.getEmail())
                            .name(user.getName())
                            .picture(user.getPicture())
                            .token(token)
                            .build();
                    System.out.println("사용자가 로그인 되었습니다. id=" + user.getId());
                    return ResponseEntity.ok().body(responseUserDto);
                } else {
                    ResponseDto responseDto = ResponseDto.builder()
                            .error("Login failed")
                            .build();
                    return ResponseEntity.badRequest().body(responseDto);
                }
            }
            // Save (회원가입 DB에 저장)
            else {
                User user = userService.save(userDto);
                String token = tokenProvider.createToken(user);
                UserDto responseUserDto = UserDto.builder()
                        .id(user.getId())
                        .googleId(user.getGoogleId())
                        .email(user.getEmail())
                        .name(user.getName())
                        .picture(user.getPicture())
                        .token(token)
                        .build();
                System.out.println("새로운 사용자가 db에 저장되었습니다. id=" + user.getId());
                return ResponseEntity.ok().body(responseUserDto); // save한 후에도 JWT token 전달해주기
            }
        } catch (Exception e) {
            ResponseDto responseDto = ResponseDto.builder().error(e.getMessage()).build();
            return ResponseEntity.badRequest().body(responseDto);
        }
    }
}
