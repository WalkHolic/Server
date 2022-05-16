package com.promenade.promenadeapp.domain.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRoadPathRepository extends JpaRepository<UserRoadPath, Long> {

    List<UserRoadPath> findByUserRoadId(Long userRoadId);
}
