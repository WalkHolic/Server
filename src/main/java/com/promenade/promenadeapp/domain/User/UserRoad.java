package com.promenade.promenadeapp.domain.User;

import com.promenade.promenadeapp.domain.BaseTimeEntity;
import com.promenade.promenadeapp.domain.Road.Road;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
public class UserRoad extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String trail_name;

    private String description;

    private double distance;

    private String start_addr;

    private String trail_point;

    @ManyToOne // RoadPath.road (단방향)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public UserRoad(Long id, String trail_name, String description, double distance, String start_addr, String trail_point, User user) {
        this.id = id;
        this.trail_name = trail_name;
        this.description = description;
        this.distance = distance;
        this.start_addr = start_addr;
        this.trail_point = trail_point;
        this.user = user;
    }
}
