package com.promenade.promenadeapp.service.User;

import com.promenade.promenadeapp.domain.User.UserRoad;
import com.promenade.promenadeapp.domain.User.UserRoadRepository;
import com.promenade.promenadeapp.dto.UserRoadResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserRoadService {

    private final UserRoadRepository userRoadRepository;

    private void validate(final UserRoad userRoad) {
        if (userRoad == null) {
            log.warn("UserRoadRequestDto cannot be null.");
            throw new RuntimeException("UserRoadRequestDto cannot be null.");
        }
        if (userRoad.getUser() == null) {
            log.warn("Unknown user.");
            throw new RuntimeException("Unknown user.");
        }
    }

    public UserRoad saveUserRoad(UserRoad userRoad) {
        validate(userRoad);

        return userRoadRepository.save(userRoad);
    }

    public List<UserRoad> findByUserId(Long userId) {
        return userRoadRepository.findByUserId(userId);
    }

    public List<UserRoad> findByUserGoogleId(String userGoogleId) {
        return userRoadRepository.findByUserGoogleId(userGoogleId);
    }

    public UserRoad findById(Long id) {
        return userRoadRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 UserRoad가 없습니다. id = " + id));
    }

    public boolean existsById(Long id) {
        return userRoadRepository.existsById(id);
    }

    public List<UserRoad> deleteUserRoad(UserRoad userRoad) {
        validate(userRoad);
        try {
            userRoadRepository.delete(userRoad);
        } catch (Exception e) {
            log.error("error deleting UserRoad ", userRoad.getId(), e);
            throw new RuntimeException("error deleting UserRoad " + userRoad.getId());
        }
        return userRoadRepository.findByUserId(userRoad.getUser().getId());
    }

    public UserRoad updateShared(Long id) {
        UserRoad userRoad = userRoadRepository.findById(id)
                .map(e -> e.shareUserRoad(!e.isShared()))
                .orElseThrow(() -> new IllegalArgumentException("해당 UserRoad가 없습니다. id = " + id));

        return userRoadRepository.save(userRoad);
    }

}
