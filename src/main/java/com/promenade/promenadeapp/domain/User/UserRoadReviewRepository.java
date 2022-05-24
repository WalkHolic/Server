package com.promenade.promenadeapp.domain.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRoadReviewRepository extends JpaRepository<UserRoadReview, Long> {

    List<UserRoadReview> findByUserRoadId(Long userRoadId);

    List<UserRoadReview> findByUserId(Long userId);
}
