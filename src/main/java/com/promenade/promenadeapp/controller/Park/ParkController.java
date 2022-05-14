package com.promenade.promenadeapp.controller.Park;

import com.promenade.promenadeapp.dto.ParkNearInterface;
import com.promenade.promenadeapp.dto.ResponseDto;
import com.promenade.promenadeapp.service.Park.ParkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/park")
public class ParkController {

    private final ParkService parkService;

    @GetMapping("/nearParks")
    public ResponseEntity<?> getNearParks(@RequestParam double lat, @RequestParam double lng) {
        List<ParkNearInterface> nearParks = parkService.getNearParks(lat, lng);
        if (nearParks.isEmpty()) {
            ResponseDto response = ResponseDto.builder()
                    .error("주변에 공원이 없습니다.")
                    .build();
            return ResponseEntity.badRequest().body(response);
        }
        ResponseDto<ParkNearInterface> response = ResponseDto.<ParkNearInterface>builder()
                .data(nearParks)
                .build();
        return ResponseEntity.ok(response);
    }

}
