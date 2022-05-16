package com.promenade.promenadeapp.service.User;

import com.promenade.promenadeapp.domain.User.UserRoadPath;
import com.promenade.promenadeapp.domain.User.UserRoadPathRepository;
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

    public List<UserRoadPath> findByUserRoadId(Long userRoadId) {
        return userRoadPathRepository.findByUserRoadId(userRoadId);
    }
}
