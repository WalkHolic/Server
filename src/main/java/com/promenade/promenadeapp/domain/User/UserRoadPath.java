package com.promenade.promenadeapp.domain.User;

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
public class UserRoadPath {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer seq;

    private double lat;

    private double lng;

    @ManyToOne // RoadPath.road (단방향)
    @JoinColumn(name = "user_road_id")
    private UserRoad userRoad;

}
