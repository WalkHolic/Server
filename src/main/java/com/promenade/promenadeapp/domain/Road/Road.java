package com.promenade.promenadeapp.domain.Road;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Road {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "road")
    private RoadDetail roadDetail;

    private String road_name;

    private double distance;

    private String time;

    private String start_name;

    private String start_road_addr;

    private String start_lot_addr;

    private double start_lat;

    private double start_lng;
}
