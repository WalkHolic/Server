package com.promenade.promenadeapp.service.Road;

import com.promenade.promenadeapp.domain.Road.Road;
import com.promenade.promenadeapp.domain.Road.RoadRepository;
import com.promenade.promenadeapp.dto.RoadNearInterface;
import com.promenade.promenadeapp.dto.UserRoadRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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