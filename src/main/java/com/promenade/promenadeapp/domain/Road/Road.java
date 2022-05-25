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
    @Column(name = "road_name")
    private String roadName;

    @NotNull
    @Column(name = "road_desc", length = 600)
    private String roadDesc;

    @Column(name = "picture_path")
    private String picturePath;

    @NotNull
    private double distance;

    @NotNull
    private String time;

    @NotNull
    @Column(name = "start_name")
    private String startName;

    @Column(name = "start_road_addr")
    private String startRoadAddr;

    @Column(name = "start_lot_addr")
    private String startLotAddr;

    @NotNull
    @Column(name = "start_lat")
    private double startLat;

    @NotNull
    @Column(name = "start_lng")
    private double startLng;

    @Column(name = "road_path_str", length = 400)
    private String roadPathStr;

    @NotNull
    @Column(name = "agency_tel")
    private String agencyTel;

    @NotNull
    @Column(name = "agency_name")
    private String agencyName;

    @NotNull
    @Column(name = "base_date")
    private String baseDate;

    @NotNull
    @Column(name = "provider_code")
    private String providerCode;

    @NotNull
    @Column(name = "provider_name")
    private String providerName;

}
