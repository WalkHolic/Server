package com.promenade.promenadeapp.dto.Road;

import com.promenade.promenadeapp.domain.Road.Road;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class RoadResponseDto {

    private Long id;
    private String roadName;
    private String roadDesc;
    private String picturePath;
    private Double distance;
    private String time;
    private String startName;
    private String startRoadAddr;
    private String startLotAddr;
    private Double startLat;
    private Double startLng;
    private String roadPathStr;
    private String agencyTel;
    private String agencyName;
    private String providerName;
    private List<String> hashtag;

    public RoadResponseDto(RoadNearInterface road, List<String> hashtag) {
        this.id = road.getId();
        this.roadName = road.getRoad_name();
        this.roadDesc = road.getRoad_desc();
        this.picturePath = road.getPicture_path();
        this.distance = road.getDistance();
        this.time = road.getTime();
        this.startName = road.getStart_name();
        this.startRoadAddr = road.getStart_road_addr();
        this.startLotAddr = road.getStart_lot_addr();
        this.startLat = road.getStart_lat();
        this.startLng = road.getStart_lng();
        this.roadPathStr = road.getRoad_path_str();
        this.agencyTel = road.getAgency_tel();
        this.agencyName = road.getAgency_name();
        this.providerName = road.getProvider_name();
        this.hashtag = hashtag;
    }

    public RoadResponseDto(Road road, List<String> hashtag) {
        this.id = road.getId();
        this.roadName = road.getRoadName();
        this.roadDesc = road.getRoadDesc();
        this.picturePath = road.getPicturePath();
        this.distance = road.getDistance();
        this.time = road.getTime();
        this.startName = road.getStartName();
        this.startRoadAddr = road.getStartRoadAddr();
        this.startLotAddr = road.getStartLotAddr();
        this.startLat = road.getStartLat();
        this.startLng = road.getStartLng();
        this.roadPathStr = road.getRoadPathStr();
        this.agencyTel = road.getAgencyTel();
        this.agencyName = road.getAgencyName();
        this.providerName = road.getProviderName();
        this.hashtag = hashtag;
    }

}
