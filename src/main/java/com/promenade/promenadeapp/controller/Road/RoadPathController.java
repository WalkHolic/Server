package com.promenade.promenadeapp.controller.Road;

import com.promenade.promenadeapp.domain.Road.RoadPath;
import com.promenade.promenadeapp.dto.ResponseDto;
import com.promenade.promenadeapp.dto.RoadPathResponseDto;
import com.promenade.promenadeapp.service.Road.RoadPathService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/roadPath")
public class RoadPathController {

    private final RoadPathService roadPathService;

    @GetMapping("/roadId/{roadId}")
    public ResponseEntity<?> findByRoadId(@PathVariable Long roadId) {
        List<RoadPath> foundRoadPaths = roadPathService.findByUserId(roadId);
        if (foundRoadPaths.isEmpty()) {
            ResponseDto response = ResponseDto.builder()
                    .error("해당 산책로의 경로 정보가 없습니다. roadId = " + roadId)
                    .build();
            return ResponseEntity.badRequest().body(response);
        }
        List<RoadPathResponseDto> responseDto = foundRoadPaths.stream().map(RoadPathResponseDto::new).collect(Collectors.toList());
        ResponseDto<RoadPathResponseDto> response = ResponseDto.<RoadPathResponseDto>builder()
                .data(responseDto)
                .build();
        return ResponseEntity.ok(response);
    }
}
