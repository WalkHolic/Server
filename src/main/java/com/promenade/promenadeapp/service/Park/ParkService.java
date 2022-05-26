package com.promenade.promenadeapp.service.Park;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.promenade.promenadeapp.domain.Park.Park;
import com.promenade.promenadeapp.domain.Park.ParkRepository;
import com.promenade.promenadeapp.dto.Park.ParkFilterDto;
import com.promenade.promenadeapp.dto.Park.ParkNearInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
@Service
public class ParkService {

    private final ParkRepository parkRepository;

    public List<ParkNearInterface> getNearParks(double lat, double lng) {
        return parkRepository.findNearParks(lat, lng);
    }

    public Park findById(Long id) {
        return parkRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 공원이 없습니다. id = " + id));
    }

    public List<String> filtering(ParkFilterDto filterDto) throws JsonProcessingException {
        Gson gson = new Gson();
        String json = gson.toJson(filterDto);
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<String> filters = new ArrayList<>();
        Map<String, Boolean> result = mapper.readValue(json, Map.class);
        for (String key : result.keySet()) {
            if (result.get(key)) {
                filters.add(key);
            }
        }
        return filters;
    }

    public List<ParkNearInterface> searchByFilters(double lat, double lng, String filters) {
        return parkRepository.searchByFilters(lat, lng, filters);
    }

    public Boolean isBoundaryKorea(double lat, double lng) {
        if (lat > 38.9 || lat < 33.0 || lng > 132.0 || lng < 124.5) {
            return false;
        } else return true;
    }
}
