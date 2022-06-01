package com.promenade.promenadeapp.service.Road;

import com.promenade.promenadeapp.domain.Road.*;
import com.promenade.promenadeapp.dto.Road.RoadNearInterface;
import com.promenade.promenadeapp.dto.Road.RoadResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class RoadHashtagService {

    private final RoadHashtagRepository roadHashtagRepository;

    private final RoadReviewRepository roadReviewRepository;

    public List<String> findHashtagsByRoadId(Long roadId) {
        return roadHashtagRepository.findDistinctHashtagByRoadId(roadId);
    }

    public RoadResponseDto addHashtagRoad(Road road) {
        Long roadId = road.getId();
        List<String> hashtagsByRoadId = findHashtagsByRoadId(roadId);
        RoadResponseDto roadResponseDto = new RoadResponseDto(road, hashtagsByRoadId);
        return roadResponseDto;
    }

    public List<RoadResponseDto> addHashtagRoads(List<Road> roads) {
        // Road + RoadHashTag 포함하여 응답해주기
        List<RoadResponseDto> responseDtos = new ArrayList<>();
        for (Road road : roads) {
            Long roadId = road.getId();
            List<String> hashtagsByRoadId = findHashtagsByRoadId(roadId);
            RoadResponseDto roadResponseDto = new RoadResponseDto(road, hashtagsByRoadId);
            responseDtos.add(roadResponseDto);
        }
        return responseDtos;
    }

    public List<RoadResponseDto> addHashtagRoadsInterface(List<RoadNearInterface> roads) {
        // Road + RoadHashTag 포함하여 응답해주기
        List<RoadResponseDto> responseDtos = new ArrayList<>();
        for (RoadNearInterface road : roads) {
            Long roadId = road.getId();
            List<String> hashtagsByRoadId = findHashtagsByRoadId(roadId);
            RoadResponseDto roadResponseDto = new RoadResponseDto(road, hashtagsByRoadId);
            responseDtos.add(roadResponseDto);
        }
        return responseDtos;
    }

    public List<RoadHashtag> findByHashtag(String hashtag) {
        return roadHashtagRepository.findByHashtag(hashtag);
    }
}
