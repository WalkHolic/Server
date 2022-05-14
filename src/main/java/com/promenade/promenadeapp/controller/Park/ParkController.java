package com.promenade.promenadeapp.controller.Park;

import com.promenade.promenadeapp.domain.Park.Park;
import com.promenade.promenadeapp.dto.ParkNearInterface;
import com.promenade.promenadeapp.dto.ResponseDto;
import com.promenade.promenadeapp.service.Park.ParkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/park")
public class ParkController {

    private final ParkService parkService;

    @GetMapping("/id/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Park park = parkService.findById(id);
        ResponseDto<Park> response = ResponseDto.<Park>builder()
                .data(Arrays.asList(park)) // 한 개인데 data 자료형이 List Generic
                .build();
        return ResponseEntity.ok(response);
    }

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
