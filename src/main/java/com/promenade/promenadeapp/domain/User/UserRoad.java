package com.promenade.promenadeapp.domain.User;

import com.promenade.promenadeapp.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class UserRoad extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_google_id")
    private String userGoogleId;

    @Column(name = "trail_name", unique = true)
    private String trailName;

    private String description;

    private double distance;

    @Column(name = "start_addr")
    private String startAddr;

    @Column(name = "trail_point")
    private String trailPoint;

    @ManyToOne // RoadPath.road (단방향)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public UserRoad(Long id, String userGoogleId, String trailName, String description, double distance, String startAddr, String trailPoint, User user) {
        this.id = id;
        this.userGoogleId = userGoogleId;
        this.trailName = trailName;
        this.description = description;
        this.distance = distance;
        this.startAddr = startAddr;
        this.trailPoint = trailPoint;
        this.user = user;
    }
}
