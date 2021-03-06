package com.promenade.promenadeapp.service.User;

import com.promenade.promenadeapp.domain.User.UserRoad;
import com.promenade.promenadeapp.domain.User.UserRoadRepository;
import com.promenade.promenadeapp.dto.User.UserRoadNearInterface;
import com.promenade.promenadeapp.dto.User.UserRoadUpdateRequestDto;
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

    public UserRoad update(Long id, UserRoadUpdateRequestDto requestDto, String thumbnail) {
        UserRoad userRoad = userRoadRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 산책로가 없습니다. id=" + id));
        if (requestDto.getTrailName() == null || requestDto.getTrailName().isBlank()) {
            throw new IllegalArgumentException("산책로 이름은 필수입니다.");
        }

        return userRoad.update(requestDto.getTrailName(), requestDto.getDescription(), thumbnail);
    }

    public List<UserRoadNearInterface> findNearUserRoads(double lat, double lng) {
        return userRoadRepository.findNearUserRoads(lat, lng);
    }

    public Boolean isBoundaryKorea(double lat, double lng) {
        if (lat > 38.9 || lat < 33.0 || lng > 132.0 || lng < 124.5) {
            return false;
        } else return true;
    }
}
