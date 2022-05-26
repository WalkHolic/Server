package com.promenade.promenadeapp.controller.User;

import com.promenade.promenadeapp.domain.User.User;
import com.promenade.promenadeapp.domain.User.UserRoad;
import com.promenade.promenadeapp.domain.User.UserRoadHashtag;
import com.promenade.promenadeapp.domain.User.UserRoadPath;
import com.promenade.promenadeapp.dto.*;
import com.promenade.promenadeapp.dto.User.*;
import com.promenade.promenadeapp.service.Road.StorageService;
import com.promenade.promenadeapp.service.User.UserRoadHashtagService;
import com.promenade.promenadeapp.service.User.UserRoadPathService;
import com.promenade.promenadeapp.service.User.UserRoadService;
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
@RequestMapping("/user/road")
public class UserRoadController {

    private final UserService userService;

    private final UserRoadService userRoadService;

    private final UserRoadPathService userRoadPathService;

    private final UserRoadHashtagService userRoadHashtagService;

    private final StorageService storageService;

    @GetMapping
    public ResponseEntity<?> getUserRoads(@AuthenticationPrincipal String googleId) {
        try {

            List<UserRoad> userRoads = userRoadService.findByUserGoogleId(googleId);
            if (userRoads.isEmpty()) {
                ResponseDto response = ResponseDto.builder()
                        .error("사용자의 커스텀 산책로가 없습니다. userGoogleId = " + googleId)
                        .build();
                return ResponseEntity.badRequest().body(response);
            }
            // UserRoad에 Hashtag 추가해서 응답해주기
            List<UserRoadResponseDto> responseDtos = userRoadHashtagService.addHashtagRoads(userRoads);
            ResponseDto<UserRoadResponseDto> response = ResponseDto.<UserRoadResponseDto>builder()
                    .data(responseDtos)
                    .build();

            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            ResponseDto response = ResponseDto.builder().error(e.getMessage()).build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping
    public ResponseEntity<?> saveUserRoad(@AuthenticationPrincipal String googleId,
                                          @RequestBody UserRoadRequestDto requestDto) {
        try {
            User foundUserByGoogleId = userService.findByGoogleId(googleId);

            // 1. request 정보에 따라 UserRoad 엔티티에 저장하기 (trailPoints x)
            UserRoad userRoad = UserRoad.builder()
                    .id(null) // saveUserRoad()에서 자동 추가
                    .trailName(requestDto.getTrailName())
                    .description(requestDto.getDescription())
                    .distance(requestDto.getDistance())
                    .startAddr(requestDto.getStartAddr())
                    .startLat(requestDto.getTrailPoints().get(0).get(0))
                    .startLng(requestDto.getTrailPoints().get(0).get(1))
                    .user(foundUserByGoogleId) // user 추가
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
                log.debug("lat, lng = " + point.toString());
                UserRoadPath tmpUserRoadPath = UserRoadPath.builder()
                        .lat(point.get(0))
                        .lng(point.get(1))
                        .userRoad(savedUserRoad)
                        .build();
                Long pathId = userRoadPathService.save(tmpUserRoadPath);// id 자동 추가
                log.debug("UserRoadPath is saved. id = {}", pathId);
            }

            // 3. request 정보에 따라 UserRoadHashTag 엔티티에 저장하기
            List<String> hashtags = requestDto.getHashtag();
            System.out.println(hashtags);
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
            List<UserRoad> userRoads = userRoadService.findByUserGoogleId(googleId); // 인증된 사용자의 산책로 리스트

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

    @DeleteMapping("/{id}")
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

            List<UserRoadResponseDto> responseDtos = userRoadHashtagService.addHashtagRoads(userRoads);

            ResponseDto<UserRoadResponseDto> response = ResponseDto.<UserRoadResponseDto>builder()
                    .data(responseDtos)
                    .build();

            return ResponseEntity.ok().body(response);

        } catch (Exception e) {
            ResponseDto response = ResponseDto.builder().error(e.getMessage()).build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/{id}/paths")
    public ResponseEntity<?> findByUserRoadId(@AuthenticationPrincipal String googleId,
                                                       @PathVariable Long id) {
        try {

            List<UserRoad> foundUserRoads = userRoadService.findByUserGoogleId(googleId);
            List<Long> roadIds = foundUserRoads.stream().map(road -> road.getId()).collect(Collectors.toList());
            UserRoad foundUserRoad = userRoadService.findById(id);

            // 공유된 산책로가 아니거나, 로그인한 사용자 산책로가 아니면 잘못된 접근 처리
            if ((foundUserRoad.isShared() == false) && !roadIds.contains(id)) {
                ResponseDto response = ResponseDto.builder().error("접근 가능한 산책로가 아닙니다. id=" + id).build();
                return ResponseEntity.badRequest().body(response);
            }

            ResponseDto<UserRoadPathResponse> response = ResponseDto.<UserRoadPathResponse>builder()
                    .data(userRoadPathService.findByUserRoadId(id))
                    .build();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ResponseDto response = ResponseDto.builder().error(e.getMessage()).build();
            return ResponseEntity.badRequest().body(response);
        }

    }

    @GetMapping("/{id}/share")
    public ResponseEntity<?> shareUserRoad(@AuthenticationPrincipal String googleId,
                                           @PathVariable Long id) {
        try {

            List<UserRoad> foundUserRoads = userRoadService.findByUserGoogleId(googleId);
            List<Long> roadIds = foundUserRoads.stream().map(road -> road.getId()).collect(Collectors.toList());

            if (!roadIds.contains(id)) {
                ResponseDto response = ResponseDto.builder().error("접근 가능한 산책로가 아닙니다. id=" + id).build();
                return ResponseEntity.badRequest().body(response);
            }

            UserRoad userRoad = userRoadService.updateShared(id);

            ResponseDto response = ResponseDto.builder()
                    .data(Arrays.asList("shared changed. " + "shared: " + userRoad.isShared()))
                    .build();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ResponseDto response = ResponseDto.builder().error(e.getMessage()).build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/nearRoads")
    public ResponseEntity<?> getNearRoads(@RequestParam double lat, @RequestParam double lng) {
        try {

            List<UserRoadNearInterface> nearRoads = userRoadService.findNearUserRoads(lat, lng);
            if (nearRoads.isEmpty()) {
                ResponseDto response = ResponseDto.builder()
                        .error("주변에 공유된 사용자 산책로가 없습니다.")
                        .build();
                return ResponseEntity.badRequest().body(response);
            }

            List<UserRoadResponseDto> responseDtos = userRoadHashtagService.addHashtagRoadsWithD(nearRoads);
            ResponseDto response = ResponseDto.<UserRoadResponseDto>builder()
                    .data(responseDtos)
                    .build();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ResponseDto response = ResponseDto.builder().error(e.getMessage()).build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/hashtag")
    public ResponseEntity<?> findByHashtag(@RequestParam String keyword) {
        try {

            List<UserRoadHashtag> byHashtag = userRoadHashtagService.findByHashtag(keyword);
            if (byHashtag.isEmpty()) {
                ResponseDto response = ResponseDto.builder()
                        .error("해시태그에 맞는 산책로가 하나도 없습니다. 해당 해시태그 = " + keyword)
                        .build();
                return ResponseEntity.badRequest().body(response);
            }
            List<UserRoad> userRoadsByHashtag = byHashtag.stream().map(entity -> entity.getUserRoad()).collect(Collectors.toList());

            // shared인 사용자 산책로만 필터링
            List<UserRoad> userRoads = userRoadsByHashtag.stream().filter(road -> road.isShared() == true).collect(Collectors.toList());
            if (userRoads.isEmpty()) {
                ResponseDto response = ResponseDto.builder()
                        .error("공유된 산책로가 없습니다. 해당 해시태그 = " + keyword)
                        .build();
                return ResponseEntity.badRequest().body(response);
            }

            List<UserRoadResponseDto> responseDtos = userRoadHashtagService.addHashtagRoads(userRoads);
            ResponseDto response = ResponseDto.<UserRoadResponseDto>builder()
                    .data(responseDtos)
                    .build();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ResponseDto response = ResponseDto.builder().error(e.getMessage()).build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateUserRoad(@AuthenticationPrincipal String googleId,
                                            @PathVariable Long id,
                                            @RequestPart(required = false) MultipartFile thumbnail,
                                            @RequestPart UserRoadUpdateRequestDto userRoadUpdateRequestDto) {
        try {
            List<UserRoad> foundUserRoads = userRoadService.findByUserGoogleId(googleId);
            List<Long> roadIds = foundUserRoads.stream().map(road -> road.getId()).collect(Collectors.toList());

            if (!roadIds.contains(id)) {
                ResponseDto response = ResponseDto.builder().error("접근 가능한 산책로가 아닙니다. id=" + id).build();
                return ResponseEntity.badRequest().body(response);
            }

            // 사진 파일 있을때만 s3 접근해서 업로드 처리
            String pictureUrl = null;
            if (!(thumbnail == null || thumbnail.isEmpty())) {
                pictureUrl = storageService.uploadFile(thumbnail);
            }
            UserRoad updatedUserRoad = userRoadService.update(id, userRoadUpdateRequestDto, pictureUrl);
            log.info("사용자 산책로 업데이트. id=" + updatedUserRoad.getId());

            userRoadHashtagService.update(updatedUserRoad, userRoadUpdateRequestDto.getHashtag());

            List<UserRoadResponseDto> userRoadResponseDtos = userRoadHashtagService.addHashtagRoads(foundUserRoads);
            ResponseDto response = ResponseDto.<UserRoadResponseDto>builder()
                    .data(userRoadResponseDtos)
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
