package com.promenade.promenadeapp.domain.Park;

import lombok.Getter;

import javax.persistence.*;

@Getter
@Table(name = "park_info")
@Entity
public class Park {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    private String updated;

    private String manageAgency;

    private String contact;

    private String dataBaseDate;

    private String providerCode;

    private String providerName;

    private String pngPath;

    private String hashtag;

}
