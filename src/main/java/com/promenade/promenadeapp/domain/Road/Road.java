package com.promenade.promenadeapp.domain.Road;

import com.sun.istack.NotNull;
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

    @NotNull
    private String road_name;

    @NotNull
    @Column(length = 600)
    private String road_desc;

    private String picture_path;

    @NotNull
    private double distance;

    @NotNull
    private String time;

    @NotNull
    private String start_name;

    private String start_road_addr;

    private String start_lot_addr;

    @NotNull
    private double start_lat;

    @NotNull
    private double start_lng;

    @NotNull
    private String agency_tel;

    @NotNull
    private String agency_name;

    @NotNull
    private String base_date;

    @NotNull
    private String provider_code;

    @NotNull
    private String provider_name;

}
