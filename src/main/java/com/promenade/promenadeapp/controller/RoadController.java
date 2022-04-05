package com.promenade.promenadeapp.controller;

import com.promenade.promenadeapp.domain.Road.Road;
import com.promenade.promenadeapp.dto.RoadNearInterface;
import com.promenade.promenadeapp.service.RoadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class RoadController {

    private final RoadService roadService;

    @GetMapping("/road/all")
    public List<Road> getRoads() {
        return roadService.getAllRoads();
    }

    @GetMapping("/road/id/{id}")
    public Road findById(@PathVariable Long id) {
        return roadService.findById(id);
    }

    @GetMapping("/road/nearRoads")
    public List<RoadNearInterface> getNearRoads(@RequestParam double lat, @RequestParam double lng) {
        return roadService.getNearRoads(lat, lng);
    }

}
