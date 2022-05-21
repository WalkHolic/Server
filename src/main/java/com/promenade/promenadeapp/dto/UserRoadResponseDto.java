package com.promenade.promenadeapp.dto;

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

}
