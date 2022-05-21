package com.promenade.promenadeapp.domain.Road;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoadReviewRepository extends JpaRepository<RoadReview, Long> {

    List<RoadReview> findByRoadId(Long roadId);
}
