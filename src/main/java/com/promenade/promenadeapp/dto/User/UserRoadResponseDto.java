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

    // UserRoadHashtagService의 addHashtagRoads 함수에서 hashtag 추가해서 생성해서, 따로 생성자가 존재하지 않음.
    public UserRoadResponseDto(UserRoad userRoad, List<String> hashtags) {
        this.id = userRoad.getId();
        this.userId = userRoad.getUser().getId();
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

    // UserRoadHashtagService의 addHashtagRoadsWithD 함수에서 hashtag 추가해서 생성해서, 따로 생성자가 존재하지 않음.
    public UserRoadResponseDto(UserRoadNearInterface userRoad, List<String> hashtags) {
        this.id = userRoad.getId();
        this.userId = userRoad.getUserId();
        this.trailName = userRoad.getTrail_name();
        this.description = userRoad.getDescription();
        this.distance = userRoad.getDistance();
        this.startAddr = userRoad.getStart_addr();
        this.isShared = (userRoad.getIs_shared().equalsIgnoreCase("Y") ? true : false);
        this.picture = userRoad.getPicture();
        this.hashtag = hashtags;
        this.createdDate = userRoad.getCreated_date();
        this.modifiedDate = userRoad.getModified_date();
    }
}
