package com.promenade.promenadeapp.domain.Road;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class RoadHashtag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String hashtag;

    @ManyToOne // 단방향
    @JoinColumn(name = "road_id")
    private Road road;

}
