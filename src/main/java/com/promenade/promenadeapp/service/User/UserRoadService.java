package com.promenade.promenadeapp.service.User;

import com.promenade.promenadeapp.domain.User.User;
import com.promenade.promenadeapp.domain.User.UserRepository;
import com.promenade.promenadeapp.domain.User.UserRoad;
import com.promenade.promenadeapp.domain.User.UserRoadRepository;
import com.promenade.promenadeapp.dto.UserRoadRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserRoadService {

    private final UserRepository userRepository;
    private final UserRoadRepository userRoadRepository;

    public Long saveUserRoad(UserRoadRequestDto requestDto) {
        UserRoad userRoad = UserRoad.builder()
                .user(userRepository.findById(requestDto.getUser_id())
                        .orElseThrow(() -> new IllegalArgumentException("해당 user id가 없습니다.")))
                .trail_name(requestDto.getTrail_name())
                .description(requestDto.getDescription())
                .distance(requestDto.getDistance())
                .start_addr(requestDto.getStart_addr())
                .trail_point(requestDto.getTrail_point())
                .build();
        return userRoadRepository.save(userRoad).getId();
    }

    public List<UserRoad> getUserRoads(Long userId) {
        return userRoadRepository.findByUserId(userId);

    }
}
