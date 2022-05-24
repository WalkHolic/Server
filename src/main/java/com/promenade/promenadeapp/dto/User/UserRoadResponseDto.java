package com.promenade.promenadeapp.dto.User;

import com.promenade.promenadeapp.domain.User.User;
import com.promenade.promenadeapp.domain.User.UserRoad;
import com.promenade.promenadeapp.domain.User.UserRoadHashtag;
import com.promenade.promenadeapp.domain.User.UserRoadPath;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@AllArgsConstructor
public class UserRoadResponseDto {

    private Long id;
    private Long userId;
    private String trailName;
    private String description;
    private double distance;
    private String startAddr;
    private boolean isShared;
    private String picture;
    private List<String> hashtag;

    public UserRoadResponseDto(User user, UserRoad userRoad, List<String> hashtags) {
        this.id = userRoad.getId();
        this.userId = user.getId();
        this.trailName = userRoad.getTrailName();
        this.description = userRoad.getDescription();
        this.distance = userRoad.getDistance();
        this.startAddr = userRoad.getStartAddr();
        this.isShared = userRoad.isShared();
        this.picture = userRoad.getPicture();
        this.hashtag = hashtags;
    }

}
