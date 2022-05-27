package com.promenade.promenadeapp.service.User;

import com.promenade.promenadeapp.domain.User.UserRoad;
import com.promenade.promenadeapp.domain.User.UserRoadHashtag;
import com.promenade.promenadeapp.domain.User.UserRoadHashtagRepository;
import com.promenade.promenadeapp.dto.User.UserRoadNearInterface;
import com.promenade.promenadeapp.dto.User.UserRoadResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserRoadHashtagService {

    private final UserRoadHashtagRepository userRoadHashtagRepository;

    private final List<String> hashtagType = Arrays.asList(new String[]{"나들이", "물놀이", "아이와함께",
            "걷기좋은", "드라이브코스", "데이트코스", "분위기좋은", "런닝", "벚꽃명소", "힐링"});

    public Long save(UserRoadHashtag userRoadHashtag) {
        if (!hashtagType.contains(userRoadHashtag.getHashtag())) {
            throw new RuntimeException("해시태그 타입에 맞지 않는 키워드를 입력하셨습니다.");
        }
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
            UserRoadResponseDto userRoadResponseDto = new UserRoadResponseDto(tmpUserRoad, hashtagsByRoadId);
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
            UserRoadResponseDto userRoadResponseDto = new UserRoadResponseDto(tmpUserRoad, hashtagsByRoadId);
            responseDtos.add(userRoadResponseDto);
        }
        return responseDtos;
    }

    public List<UserRoadHashtag> findByHashtag(String hashtag) {
        return userRoadHashtagRepository.findByHashtag(hashtag);
    }

    public void delete(UserRoadHashtag userRoadHashtag) {
        userRoadHashtagRepository.delete(userRoadHashtag);
    }

    public void update(UserRoad userRoad, List<String> hashtags) {
        List<UserRoadHashtag> foundHashtags = userRoadHashtagRepository.findByUserRoadId(userRoad.getId());
        // 기존 해시태그 삭제 (개수가 다를 수 있기 때문)
        for (UserRoadHashtag foundHashtag : foundHashtags) {
            userRoadHashtagRepository.delete(foundHashtag);
            log.info("해시태그 삭제. id=" + foundHashtag.getId());
        }
        // 새로운 해시태그 추가
        if (!(hashtags == null || hashtags.isEmpty())) {
            for (String hashtag : hashtags) {
                if (!hashtagType.contains(hashtag)) {
                    throw new RuntimeException("해시태그 타입에 맞지 않는 키워드를 입력하셨습니다.");
                }
                UserRoadHashtag userRoadHashtag = UserRoadHashtag.builder()
                        .id(null)
                        .userRoad(userRoad)
                        .hashtag(hashtag)
                        .build();
                Long id = userRoadHashtagRepository.save(userRoadHashtag).getId();
                log.info("해시태그 추가. id=" + id);
            }
        }
    }

}
