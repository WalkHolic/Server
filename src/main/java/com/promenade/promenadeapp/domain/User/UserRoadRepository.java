package com.promenade.promenadeapp.domain.User;

import com.promenade.promenadeapp.domain.Road.Road;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoadRepository extends JpaRepository<UserRoad, Long> {
    UserRoad deleteByUserId(Long userId);
}
