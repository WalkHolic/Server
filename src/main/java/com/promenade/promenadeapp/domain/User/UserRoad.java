package com.promenade.promenadeapp.domain.User;

import com.promenade.promenadeapp.config.BooleanToYNConverter;
import com.promenade.promenadeapp.domain.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class



  UserRoad extends BaseTimeEntity {

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

    private String picture;

    @Column(name = "is_shared")
    @Convert(converter = BooleanToYNConverter.class)
    private boolean isShared;

    @ManyToOne // RoadPath.road (단방향)
    @JoinColumn(name = "user_id")
    private User user;

    // 공유 기능 활성 또는 비활성화
    public UserRoad shareUserRoad(boolean isShared) {
        this.isShared = isShared;

        return this;
    }

    // 자신의 산책로 수정
    public UserRoad update(String trailName, String description, String picture) {
        this.trailName = trailName;
        this.description = description;
        this.picture = picture;

        return this;
    }

}
