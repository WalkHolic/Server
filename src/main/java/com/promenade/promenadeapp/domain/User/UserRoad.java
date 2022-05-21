package com.promenade.promenadeapp.domain.User;

import com.promenade.promenadeapp.config.BooleanToYNConverter;
import com.promenade.promenadeapp.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class UserRoad extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "trail_name", unique = true)
    private String trailName;

    private String description;

    private double distance;

    @Column(name = "start_addr")
    private String startAddr;

    @Column(name = "start_lat")
    private double startLat;

    @Column(name = "start_lng")
    private double startLng;

    @Column(name = "is_shared")
    @Convert(converter = BooleanToYNConverter.class)
    private boolean isShared;

    @ManyToOne // RoadPath.road (단방향)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public UserRoad(Long id, String trailName, String description, double distance, String startAddr, double startLat, double startLng, boolean isShared, User user) {
        this.id = id;
        this.trailName = trailName;
        this.description = description;
        this.distance = distance;
        this.startAddr = startAddr;
        this.startLat = startLat;
        this.startLng = startLng;
        this.isShared = isShared;
        this.user = user;
    }

    public UserRoad shareUserRoad(boolean isShared) {
        this.isShared = isShared;

        return this;
    }

}
