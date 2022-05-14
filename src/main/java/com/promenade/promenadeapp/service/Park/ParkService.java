package com.promenade.promenadeapp.service.Park;

import com.promenade.promenadeapp.domain.Park.ParkRepository;
import com.promenade.promenadeapp.dto.ParkNearInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ParkService {

    private final ParkRepository parkRepository;

    public List<ParkNearInterface> getNearParks(double lat, double lng) {
        return parkRepository.findNearParks(lat, lng);
    }
}
