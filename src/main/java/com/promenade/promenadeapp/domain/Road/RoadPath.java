package com.promenade.promenadeapp.domain.Road;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@IdClass(RoadPathID.class)
@Entity
public class RoadPath {

    @Id
    @ManyToOne
    @JoinColumn(name = "id")
    private Road road;

    @Id
    private int seq;

    private String spot_name;

    private String road_addr;

    private String lot_addr;

    private double lat;

    private double lng;

}
