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

    private String spotName;

    private String roadAddr;

    private String lotAddr;

    private Double lat;

    private Double lng;

    private String pngPath;

    @ManyToOne // RoadPath.road (단방향)
    @JoinColumn(name = "road_id")
    private Road road;

}
