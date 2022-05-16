package com.promenade.promenadeapp.dto;

import com.promenade.promenadeapp.domain.User.UserRoad;
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
    private String userGoogleId;
    private String trailName;
    private String description;
    private double distance;
    private String startAddr;

    public UserRoadResponseDto(UserRoad userRoad) {
        this.id = userRoad.getId();
        this.userId = userRoad.getUser().getId();
        this.userGoogleId = userRoad.getUserGoogleId();
        this.trailName = userRoad.getTrailName();
        this.description = userRoad.getDescription();
        this.distance = userRoad.getDistance();
        this.startAddr = userRoad.getStartAddr();
    }
}
