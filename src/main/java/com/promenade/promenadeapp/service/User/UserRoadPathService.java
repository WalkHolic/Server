package com.promenade.promenadeapp.service.User;

import com.promenade.promenadeapp.domain.User.UserRoadPath;
import com.promenade.promenadeapp.domain.User.UserRoadPathRepository;
import com.promenade.promenadeapp.dto.UserRoadPathResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserRoadPathService {

    private final UserRoadPathRepository userRoadPathRepository;

    public Long save(UserRoadPath userRoadPath) {
        return userRoadPathRepository.save(userRoadPath).getId();
    }

    public List<UserRoadPathResponse> findByUserRoadId(Long userRoadId) {
        return userRoadPathRepository.findByUserRoadId(userRoadId);
    }
}
