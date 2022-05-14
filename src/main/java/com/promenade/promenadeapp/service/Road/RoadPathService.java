package com.promenade.promenadeapp.service.Road;

import com.promenade.promenadeapp.domain.Road.RoadPath;
import com.promenade.promenadeapp.domain.Road.RoadPathRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RoadPathService {

    private final RoadPathRepository roadPathRepository;

    public List<RoadPath> findByUserId(Long userId) {
        return roadPathRepository.findByRoadId(userId);
    }
}
