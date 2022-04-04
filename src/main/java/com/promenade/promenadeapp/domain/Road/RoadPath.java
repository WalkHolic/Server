package com.promenade.promenadeapp.domain.Road;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class RoadPath {

    @Id
    private Long id;

    private int seq;

    private String spot_name;

    private String road_addr;

    private String lot_addr;

    private double lat;

    private double lng;

    @ManyToOne // RoadPath.road (단방향)
    @JoinColumn(name = "Road_id")
    private Road road;
}
