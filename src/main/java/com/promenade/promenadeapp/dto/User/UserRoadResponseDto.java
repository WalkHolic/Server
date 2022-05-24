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

}
