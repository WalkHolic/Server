package com.promenade.promenadeapp.service.Road;

import com.promenade.promenadeapp.domain.Road.Road;
import com.promenade.promenadeapp.domain.Road.RoadRepository;
import com.promenade.promenadeapp.dto.Road.RoadNearInterface;
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

    public Boolean isBoundaryKorea(double lat, double lng) {
        if (lat > 38.9 || lat < 33.0 || lng > 132.0 || lng < 124.5) {
            return false;
        } else return true;
    }
}
