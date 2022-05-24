package com.promenade.promenadeapp.dto.User;

import com.promenade.promenadeapp.domain.User.User;
import com.promenade.promenadeapp.domain.User.UserRoad;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

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
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

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
        this.createdDate = userRoad.getCreatedDate();
        this.modifiedDate = userRoad.getModifiedDate();
    }

}
