package com.promenade.promenadeapp.service.User;

import com.promenade.promenadeapp.domain.User.UserRoad;
import com.promenade.promenadeapp.domain.User.UserRoadHashtag;
import com.promenade.promenadeapp.domain.User.UserRoadHashtagRepository;
import com.promenade.promenadeapp.dto.UserRoadNearInterface;
import com.promenade.promenadeapp.dto.UserRoadResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
                    .picture(tmpUserRoad.getPicture())
                    .hashtag(hashtagsByRoadId)
                    .build();
            responseDtos.add(userRoadResponseDto);
        }
        return responseDtos;
    }

    public List<UserRoadResponseDto> addHashtagRoadsWithD(List<UserRoadNearInterface> userRoads) {
        // UserRoad + UserRoadHashTag 포함하여 응답해주기
        List<UserRoadResponseDto> responseDtos = new ArrayList<>();
        for (UserRoadNearInterface tmpUserRoad : userRoads) {
            Long roadId = tmpUserRoad.getId();
            List<String> hashtagsByRoadId = findHashtagsByRoadId(roadId);
            UserRoadResponseDto userRoadResponseDto = UserRoadResponseDto.builder()
                    .id(tmpUserRoad.getId())
                    .userId(tmpUserRoad.getUserId())
                    .trailName(tmpUserRoad.getTrail_name())
                    .description(tmpUserRoad.getDescription())
                    .distance(tmpUserRoad.getDistance())
                    .startAddr(tmpUserRoad.getStart_addr())
                    .picture(tmpUserRoad.getPicture())
                    .hashtag(hashtagsByRoadId)
                    .build();
            responseDtos.add(userRoadResponseDto);
        }
        return responseDtos;
    }

    public List<UserRoadHashtag> findByHashtag(String hashtag) {
        return userRoadHashtagRepository.findByHashtag(hashtag);
    }
}
