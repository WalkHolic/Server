package com.promenade.promenadeapp.domain.Road;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class RoadDetail {

    @Id
    private Long id;

    @MapsId // RoadDetail.id 매핑
    @OneToOne
    @JoinColumn(name = "id")
    private Road road;

    @Column(length = 600)
    private String road_desc;

    private String picture_path;

    private String agency_tel;

    private String base_date;

    private String provider_code;

    private String provider_name;

}
