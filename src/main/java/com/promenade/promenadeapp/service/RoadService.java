package com.promenade.promenadeapp.service;

import com.promenade.promenadeapp.domain.Road.Road;
import com.promenade.promenadeapp.domain.Road.RoadRepository;
import com.promenade.promenadeapp.dto.RoadNearInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RoadService {

    private final RoadRepository roadRepository;

    public List<Road> getAllRoads() {
        return roadRepository.findAll();
    }

    public Road findById(Long id) {
        return roadRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 산책길이 없습니다. id=" + id));
    }

    public List<RoadNearInterface> getNearRoads(double lat, double lng) {
        return roadRepository.findNearRoads(lat, lng);
    }
}
