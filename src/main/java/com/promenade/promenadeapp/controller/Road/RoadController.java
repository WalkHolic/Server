package com.promenade.promenadeapp.controller.Road;

import com.promenade.promenadeapp.domain.Road.Road;
import com.promenade.promenadeapp.dto.RoadNearInterface;
import com.promenade.promenadeapp.service.Road.RoadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/id/{id}")
    public Road findById(@PathVariable Long id) {
        return roadService.findById(id);
    }

    @GetMapping("/nearRoads")
    public List<RoadNearInterface> getNearRoads(@RequestParam double lat, @RequestParam double lng) {
        return roadService.getNearRoads(lat, lng);
    }

}
