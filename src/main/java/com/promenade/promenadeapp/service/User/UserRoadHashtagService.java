package com.promenade.promenadeapp.service.User;

import com.promenade.promenadeapp.domain.User.UserRoad;
import com.promenade.promenadeapp.domain.User.UserRoadHashtag;
import com.promenade.promenadeapp.domain.User.UserRoadHashtagRepository;
import com.promenade.promenadeapp.dto.UserRoadResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserRoadHashtagService {

    private final UserRoadHashtagRepository userRoadHashtagRepository;

    public Long save(UserRoadHashtag userRoadHashtag) {
        return userRoadHashtagRepository.save(userRoadHashtag).getId();
    }

    public List<String> findHashtagsByRoadId(Long roadId) {
        List<UserRoadHashtag> hashTags = userRoadHashtagRepository.findByUserRoadId(roadId);
        List<String> result = new ArrayList<>();
        for (UserRoadHashtag hashTag : hashTags) {
            String hashtag = hashTag.getHashtag();
            result.add(hashtag);
        }
        return result;
    }

    public UserRoadResponseDto addHashtagRoad(UserRoad userRoad) {
        // UserRoad + UserRoadHashTag 포함하여 응답해주기
        Long roadId = userRoad.getId();
        List<String> hashtagsByRoadId = findHashtagsByRoadId(roadId);
        UserRoadResponseDto userRoadResponseDto = UserRoadResponseDto.builder()
                .id(userRoad.getId())
                .userId(userRoad.getUser().getId())
                .trailName(userRoad.getTrailName())
                .description(userRoad.getDescription())
                .distance(userRoad.getDistance())
                .startAddr(userRoad.getStartAddr())
                .hashtag(hashtagsByRoadId)
                .build();

        return userRoadResponseDto;
    }

    public List<UserRoadResponseDto> addHashtagRoads(List<UserRoad> userRoads) {
        // UserRoad + UserRoadHashTag 포함하여 응답해주기
        List<UserRoadResponseDto> responseDtos = new ArrayList<>();
        for (UserRoad tmpUserRoad : userRoads) {
            Long roadId = tmpUserRoad.getId();
            List<String> hashtagsByRoadId = findHashtagsByRoadId(roadId);
            UserRoadResponseDto userRoadResponseDto = UserRoadResponseDto.builder()
                    .id(tmpUserRoad.getId())
                    .userId(tmpUserRoad.getUser().getId())
                    .trailName(tmpUserRoad.getTrailName())
                    .description(tmpUserRoad.getDescription())
                    .distance(tmpUserRoad.getDistance())
                    .startAddr(tmpUserRoad.getStartAddr())
                    .hashtag(hashtagsByRoadId)
                    .build();
            responseDtos.add(userRoadResponseDto);
        }
        return responseDtos;
    }
}
