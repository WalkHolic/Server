package com.promenade.promenadeapp.dto;

import com.promenade.promenadeapp.domain.Road.Road;
import com.promenade.promenadeapp.domain.User.User;
import com.promenade.promenadeapp.domain.User.UserRoad;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserRoadRequestDto {

    private String trail_name;

    private String description;

    private double distance;

    private String start_addr;

    private String trail_point;

    private User user;

    @Builder
    public UserRoadRequestDto(String trail_name, String description, double distance, String start_addr, String trail_point, User user) {
        this.trail_name = trail_name;
        this.description = description;
        this.distance = distance;
        this.start_addr = start_addr;
        this.trail_point = trail_point;
        this.user = user;
    }

    public UserRoad toEntity() {
        return UserRoad.builder()
                .trail_name(trail_name)
                .description(description)
                .distance(distance)
                .start_addr(start_addr)
                .trail_point(trail_point)
                .build();
    }
}