package com.promenade.promenadeapp.domain.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRoadRepository extends JpaRepository<UserRoad, Long> {

    List<UserRoad> findByUserGoogleId(String googleId);

    List<UserRoad> findByUserId(Long userId);

    @Query(value = "update user_road set is_shared = :isShared where id = :id", nativeQuery = true)
    void updateShared(@Param("id") Long id, @Param("isShared") boolean isShared);

}
