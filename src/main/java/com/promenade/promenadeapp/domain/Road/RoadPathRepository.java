package com.promenade.promenadeapp.domain.Road;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoadPathRepository extends JpaRepository<RoadPath, Long> {

    List<RoadPath> findByRoadId(Long roadId);

}
