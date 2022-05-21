package com.promenade.promenadeapp.controller;

import com.promenade.promenadeapp.domain.User.User;
import com.promenade.promenadeapp.domain.User.UserRoad;
import com.promenade.promenadeapp.domain.User.UserRoadHashtag;
import com.promenade.promenadeapp.domain.User.UserRoadPath;
import com.promenade.promenadeapp.dto.*;
import com.promenade.promenadeapp.security.TokenProvider;
import com.promenade.promenadeapp.service.User.UserRoadHashtagService;
import com.promenade.promenadeapp.service.User.UserRoadPathService;
import com.promenade.promenadeapp.service.User.UserRoadService;
import com.promenade.promenadeapp.service.User.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/test")
public class TestController {

    private final UserService userService;

    private final UserRoadService userRoadService;

    private final UserRoadPathService userRoadPathService;

    private final UserRoadHashtagService userRoadHashtagService;

    private final TokenProvider tokenProvider;

    @PostMapping("/user")
    public ResponseEntity<?> saveUserRoad(@RequestBody TestRequestDto requestDto) {
        try {
            User foundUser = userService.findById(requestDto.getUserId());

            // 1. request 정보에 따라 UserRoad 엔티티에 저장하기
            UserRoad userRoad = UserRoad.builder()
                    .id(null) // saveUserRoad()에서 자동 추가
                    .trailName(requestDto.getTrailName())
                    .description(requestDto.getDescription())
                    .distance(requestDto.getDistance())
                    .startAddr(requestDto.getStartAddr())
                    .user(foundUser) // user 추가
                    .build();
            UserRoad savedUserRoad = userRoadService.saveUserRoad(userRoad);

            // 2. request 정보에 따라 UserRoadPath 엔티티에 저장하기 (trailPoints o)
            List<List<Double>> points = requestDto.getTrailPoints();
            if (points == null) {
                userRoadService.deleteUserRoad(savedUserRoad);
                ResponseDto response = ResponseDto.builder().error("산책로 경로 정보(points)가 없습니다.").build();
                return ResponseEntity.badRequest().body(response);
            }
            for (List<Double> point : points) {
                UserRoadPath tmpUserRoadPath = UserRoadPath.builder()
                        .lat(point.get(0))
                        .lng(point.get(1))
                        .userRoad(savedUserRoad)
                        .build();
                Long pathId = userRoadPathService.save(tmpUserRoadPath);// id 자동 추가
                log.info("UserRoadPath is saved. id = {}", pathId);
            }

            // 3. request 정보에 따라 UserRoadHashTag 엔티티에 저장하기
            List<String> hashtags = requestDto.getHashtag();
            if (hashtags != null && !hashtags.isEmpty()) {
                for (String hashtag : hashtags) {
                    UserRoadHashtag userRoadHashtag = UserRoadHashtag.builder()
                            .userRoad(userRoad)
                            .hashtag(hashtag)
                            .build();
                    Long hashtagId = userRoadHashtagService.save(userRoadHashtag);
                    log.debug("Hashtag is saved. id = " + hashtagId + ". hashtag = " + hashtag);
                }
            }

            // 사용자의 모든 산책로 응답 (userRoadPath 제외. path는 새로 요청 했을 시에만)
            List<UserRoad> userRoads = userRoadService.findByUserId(foundUser.getId());

            // UserRoad에 Hashtag 추가해서 응답해주기
            List<UserRoadResponseDto> responseDtos = userRoadHashtagService.addHashtagRoads(userRoads);

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
