package com.promenade.promenadeapp.domain.User;

import com.promenade.promenadeapp.domain.Road.Road;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRoadRepository extends JpaRepository<UserRoad, Long> {

    List<UserRoad> findByUserId(Long UserId);
}
