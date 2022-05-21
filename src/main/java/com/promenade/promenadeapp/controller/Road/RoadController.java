package com.promenade.promenadeapp.controller.Road;

import com.promenade.promenadeapp.domain.Road.Road;
import com.promenade.promenadeapp.dto.ResponseDto;
import com.promenade.promenadeapp.dto.Road.RoadNearInterface;
import com.promenade.promenadeapp.service.Road.RoadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/road")
public class RoadController {

    private final RoadService roadService;

    @GetMapping("/all")
    public List<Road> getRoads() {
        return roadService.getAllRoads();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<Road>> findById(@PathVariable Long id) {
        Road road = roadService.findById(id);
        ResponseDto<Road> response = ResponseDto.<Road>builder()
                .data(Arrays.asList(road)) // 한 개이지만 data 자체가 List Generic여서 List로 바꿔주기
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/nearRoads")
    public ResponseEntity<ResponseDto<RoadNearInterface>> getNearRoads(@RequestParam double lat, @RequestParam double lng) {
        List<RoadNearInterface> nearRoads = roadService.getNearRoads(lat, lng);
        if (nearRoads.isEmpty()) {
            ResponseDto response = ResponseDto.builder()
                    .error("주변에 산책로가 없습니다.")
                    .build();
            return ResponseEntity.badRequest().body(response);
        }
        ResponseDto<RoadNearInterface> response = ResponseDto.<RoadNearInterface>builder()
                .data(nearRoads)
                .build();
        return ResponseEntity.ok(response);
    }

}
