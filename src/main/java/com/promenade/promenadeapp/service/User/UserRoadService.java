package com.promenade.promenadeapp.service.User;

import com.promenade.promenadeapp.domain.Road.Road;
import com.promenade.promenadeapp.domain.User.UserRoad;
import com.promenade.promenadeapp.domain.User.UserRoadRepository;
import com.promenade.promenadeapp.dto.UserRoadRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserRoadService {

    private final UserRoadRepository userRoadRepository;

    @Transactional
    public Long save(UserRoad userRoad) {
        return userRoadRepository.save(userRoad).getId();
    }

    public List<UserRoad> getUserRoads() {
        return userRoadRepository.findAll();
    }
}
