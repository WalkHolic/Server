package com.promenade.promenadeapp.dto.Park;

import com.promenade.promenadeapp.domain.Park.Park;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ParkResponseDto {

    private Long id;
    private String parkId;
    private String name;
    private String type;
    private String addrNew;
    private String addr;
    private Double lat;
    private Double lng;
    private Long area;
    private String facilitySport;
    private String facilityAmuse;
    private String facilityConv;
    private String facilityCul;
    private String facilityEtc;
    private String manageAgency;
    private String contact;
    private String providerName;

    public ParkResponseDto(Park park) {
        this.id = park.getId();
        this.parkId = park.getParkId();
        this.name = park.getName();
        this.type = park.getType();
        this.addrNew = park.getAddrNew();
        this.addr = park.getAddr();
        this.lat = park.getLat();
        this.lng = park.getLng();
        this.area = park.getArea();
        this.facilitySport = park.getFacilitySport();
        this.facilityAmuse = park.getFacilityAmuse();
        this.facilityConv = park.getFacilityConv();
        this.facilityCul = park.getFacilityCul();
        this.facilityEtc = park.getFacilityEtc();
        this.manageAgency = park.getManageAgency();
        this.contact = park.getContact();
        this.providerName = park.getProviderName();
    }

    public ParkResponseDto(ParkNearInterface park) {
        this.id = park.getId();
        this.parkId = park.getPark_id();
        this.name = park.getName();
        this.type = park.getType();
        this.addrNew = park.getAddr_new();
        this.addr = park.getAddr();
        this.lat = park.getLat();
        this.lng = park.getLng();
        this.area = park.getArea();
        this.facilitySport = park.getFacility_sport();
        this.facilityAmuse = park.getFacility_amuse();
        this.facilityConv = park.getFacility_conv();
        this.facilityCul = park.getFacility_cul();
        this.facilityEtc = park.getFacility_etc();
        this.manageAgency = park.getManage_agency();
        this.contact = park.getContact();
        this.providerName = park.getProvider_name();
    }

}
