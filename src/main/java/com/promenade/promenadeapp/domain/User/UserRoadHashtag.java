package com.promenade.promenadeapp.domain.User;

import com.promenade.promenadeapp.domain.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class UserRoadHashtag extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String hashtag;

    @ManyToOne // RoadPath.road (단방향)
    @JoinColumn(name = "user_road_id")
    private UserRoad userRoad;

}
