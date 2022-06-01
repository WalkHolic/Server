package com.promenade.promenadeapp.controller.Road;

import com.promenade.promenadeapp.domain.Road.Road;
import com.promenade.promenadeapp.domain.Road.RoadHashtag;
import com.promenade.promenadeapp.domain.User.UserRoad;
import com.promenade.promenadeapp.domain.User.UserRoadHashtag;
import com.promenade.promenadeapp.dto.ResponseDto;
import com.promenade.promenadeapp.dto.Road.RoadNearInterface;
import com.promenade.promenadeapp.dto.Road.RoadResponseDto;
import com.promenade.promenadeapp.dto.User.UserRoadResponseDto;
import com.promenade.promenadeapp.service.Road.RoadHashtagService;
import com.promenade.promenadeapp.service.Road.RoadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/road")
public class RoadController {

    private final RoadService roadService;

    private final RoadHashtagService roadHashtagService;

    @GetMapping("/all")
    public List<Road> getRoads() {
        return roadService.getAllRoads();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {

            Road road = roadService.findById(id);
            RoadResponseDto responseDto = roadHashtagService.addHashtagRoad(road);
            ResponseDto response = ResponseDto.<RoadResponseDto>builder()
                    .data(Arrays.asList(responseDto)) // 한 개이지만 data 자체가 List Generic여서 List로 바꿔주기
                    .build();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ResponseDto response = ResponseDto.builder()
                    .error(e.getMessage())
                    .build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/nearRoads")
    public ResponseEntity<?> getNearRoads(@RequestParam double lat, @RequestParam double lng) {
        try {
            if (!roadService.isBoundaryKorea(lat, lng)) {
                ResponseDto response = ResponseDto.builder()
                        .error("위경도가 한국을 벗어났습니다.")
                        .build();
                return ResponseEntity.badRequest().body(response);
            }
            List<RoadNearInterface> nearRoads = roadService.getNearRoads(lat, lng);
            if (nearRoads.isEmpty()) {
                ResponseDto response = ResponseDto.builder()
                        .error("주변에 산책로가 없습니다.")
                        .build();
                return ResponseEntity.badRequest().body(response);
            }

            List<RoadResponseDto> responseDtos = roadHashtagService.addHashtagRoadsInterface(nearRoads);

            ResponseDto response = ResponseDto.<RoadResponseDto>builder()
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
            List<RoadHashtag> byHashtag = roadHashtagService.findByHashtag(keyword);
            if (byHashtag.isEmpty()) {
                ResponseDto response = ResponseDto.builder()
                        .error("수원 지역에 해당 해시태그에 맞는 산책로가 없습니다. keyword=" + keyword)
                        .build();
                return ResponseEntity.badRequest().body(response);
            }
            List<Road> roadsByHashtag = byHashtag.stream().map(entity -> entity.getRoad()).collect(Collectors.toList());

            List<RoadResponseDto> responseDtos = roadHashtagService.addHashtagRoads(roadsByHashtag);
            ResponseDto response = ResponseDto.<RoadResponseDto>builder()
                    .data(responseDtos)
                    .build();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ResponseDto response = ResponseDto.builder().error(e.getMessage()).build();
            return ResponseEntity.badRequest().body(response);
        }
    }
}
