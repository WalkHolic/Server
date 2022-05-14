package com.promenade.promenadeapp.dto;

import com.promenade.promenadeapp.domain.Road.RoadPath;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class RoadPathResponseDto {

    private Long id;
    private Long roadId;
    private String spotName;
    private String roadAddr;
    private String lotAddr;
    private Double lat;
    private Double lng;

    public RoadPathResponseDto(RoadPath roadPath) {
        this.id = roadPath.getId();
        this.roadId = roadPath.getRoad().getId();
        this.spotName = roadPath.getSpotName();
        this.roadAddr = roadPath.getRoadAddr();
        this.lotAddr = roadPath.getLotAddr();
        this.lat = roadPath.getLat();
        this.lng = roadPath.getLng();
    }

}
