package com.promenade.promenadeapp.domain.Park;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParkReviewRepository extends JpaRepository<ParkReview, Long> {

    List<ParkReview> findByParkId(Long parkId);

}
