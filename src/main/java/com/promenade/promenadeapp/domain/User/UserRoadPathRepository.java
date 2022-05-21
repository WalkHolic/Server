package com.promenade.promenadeapp.domain.User;

import com.promenade.promenadeapp.dto.UserRoadPathResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRoadPathRepository extends JpaRepository<UserRoadPath, Long> {

    List<UserRoadPathResponse> findByUserRoadId(Long userRoadId);


}
