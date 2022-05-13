package com.promenade.promenadeapp.service.User;

import com.promenade.promenadeapp.domain.User.UserRepository;
import com.promenade.promenadeapp.domain.User.UserRoad;
import com.promenade.promenadeapp.domain.User.UserRoadRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

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
        if (userRoad.getUserGoogleId() == null) {
            log.warn("Unknown user.");
            throw new RuntimeException("Unknown user.");
        }
    }

    public List<UserRoad> saveUserRoad(UserRoad userRoad) {
        validate(userRoad);

        userRoadRepository.save(userRoad);
        log.info("UserRoad Id: {} is saved.", userRoad.getId());
        return userRoadRepository.findByUserGoogleId(userRoad.getUserGoogleId());

    }

    public List<UserRoad> getUserRoads(String userGoogleId) {
        return userRoadRepository.findByUserGoogleId(userGoogleId);
    }

    public UserRoad findByTrailName(String trailName) {
        return userRoadRepository.findByTrailName(trailName);
    }

    public List<UserRoad> deleteUserRoad(UserRoad userRoad) {
        validate(userRoad);
        try {
            userRoadRepository.delete(userRoad);
        } catch (Exception e) {
            log.error("error deleting UserRoad ", userRoad.getId(), e);
            throw new RuntimeException("error deleting UserRoad " + userRoad.getId());
        }
        return userRoadRepository.findByUserGoogleId(userRoad.getUserGoogleId());
    }

}
