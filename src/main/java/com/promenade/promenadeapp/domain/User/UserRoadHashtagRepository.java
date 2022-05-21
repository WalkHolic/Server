package com.promenade.promenadeapp.domain.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRoadHashtagRepository extends JpaRepository<UserRoadHashtag, Long> {

    List<UserRoadHashtag> findByUserRoadId(Long roadId);
}
