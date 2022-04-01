package com.promenade.promenadeapp.domain.Road;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class RoadPathID implements Serializable {

    private Road road;
    private int seq;
}
