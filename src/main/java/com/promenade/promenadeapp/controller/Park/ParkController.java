package com.promenade.promenadeapp.controller.Park;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.promenade.promenadeapp.domain.Park.Park;
import com.promenade.promenadeapp.dto.Park.ParkFilterDto;
import com.promenade.promenadeapp.dto.Park.ParkNearInterface;
import com.promenade.promenadeapp.dto.Park.ParkResponseDto;
import com.promenade.promenadeapp.dto.ResponseDto;
import com.promenade.promenadeapp.dto.Road.RoadResponseDto;
import com.promenade.promenadeapp.service.Park.ParkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/park")
public class ParkController {

    private final ParkService parkService;

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {

            Park park = parkService.findById(id);
            ParkResponseDto responseDto = new ParkResponseDto(park);
            ResponseDto response = ResponseDto.<ParkResponseDto>builder()
                    .data(Arrays.asList(responseDto)) // 한 개인데 data 자료형이 List Generic
                    .build();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ResponseDto response = ResponseDto.builder()
                    .error(e.getMessage())
                    .build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/nearParks")
    public ResponseEntity<?> getNearParks(@RequestParam double lat, @RequestParam double lng) {
        try {

            List<ParkNearInterface> nearParks = parkService.getNearParks(lat, lng);
            if (nearParks.isEmpty()) {
                ResponseDto response = ResponseDto.builder()
                        .data(null)
                        .build();
                return ResponseEntity.ok(response);
            }
            List<ParkResponseDto> responseDtos = nearParks.stream().map(ParkResponseDto::new).collect(Collectors.toList());

            ResponseDto response = ResponseDto.<ParkResponseDto>builder()
                    .data(responseDtos)
                    .build();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ResponseDto response = ResponseDto.builder()
                    .error(e.getMessage())
                    .build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/filter")
    public ResponseEntity<?> filtering(@RequestParam double lat, @RequestParam double lng,
                                       @RequestBody ParkFilterDto filterDto) throws JsonProcessingException {
        try {

            List<String> filters = parkService.filtering(filterDto);
            // 하나도 필터링 체크 안하면 필터링 없이 주변에 있는 공원 조회
            if (filters.isEmpty()) {
                List<ParkNearInterface> nearParks = parkService.getNearParks(lat, lng);
                List<ParkResponseDto> responseDtos = nearParks.stream().map(ParkResponseDto::new).collect(Collectors.toList());
                ResponseDto response = ResponseDto.<ParkResponseDto>builder()
                        .data(responseDtos)
                        .build();
                return ResponseEntity.ok(response);
            }

            List<ParkNearInterface> parks = new ArrayList<>();
            List<Long> parkIds = new ArrayList<>();
            List<Long> duplicatedParkIds = new ArrayList<>();
            for (String filter : filters) {
                List<ParkNearInterface> filterParks = parkService.searchByFilters(lat, lng, filter);
                if (parks.isEmpty()) {
                    parks.addAll(filterParks);
                    parkIds.addAll(parks.stream().map(p -> p.getId()).collect(Collectors.toList()));
                    System.out.println(filter + "옵션의 parkIds = " + parkIds);
                    continue;
                }
                for (ParkNearInterface iterPark : filterParks) {
                    if (parkIds.contains(iterPark.getId())) {
                        duplicatedParkIds.add(iterPark.getId());
                    }
                }
                parkIds.clear();
                parkIds.addAll(duplicatedParkIds);
                duplicatedParkIds.clear();
                System.out.println(filter + "옵션의 parkIds = " + parkIds);
            }
            List<ParkNearInterface> resultParks = parks.stream().filter(park -> parkIds.contains(park.getId())).collect(Collectors.toList());

            if (resultParks.isEmpty()) {
                ResponseDto response = ResponseDto.builder()
                        .error("필터링에 맞는 공원이 없습니다.")
                        .build();
                return ResponseEntity.badRequest().body(response);
            }

            List<ParkResponseDto> responseDtos = resultParks.stream().map(ParkResponseDto::new).collect(Collectors.toList());

            ResponseDto response = ResponseDto.<ParkResponseDto>builder()
                    .data(responseDtos)
                    .build();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ResponseDto response = ResponseDto.builder().error(e.getMessage()).build();
            return ResponseEntity.badRequest().body(response);
        }
    }
}
