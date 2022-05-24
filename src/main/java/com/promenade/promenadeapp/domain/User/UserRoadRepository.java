package com.promenade.promenadeapp.domain.User;

import com.promenade.promenadeapp.dto.User.UserRoadNearInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRoadRepository extends JpaRepository<UserRoad, Long> {

    List<UserRoad> findByUserGoogleId(String googleId);

    List<UserRoad> findByUserId(Long userId);

    // 반경 5km 이내 시작 주소가 있는 user_road 반환
    @Query(value = "SELECT *, ( 6371 * acos ( cos ( radians(start_lat) ) * cos( radians(:lat) ) * cos( radians(start_lng) - radians(:lng) ) + sin ( radians(start_lat) ) * sin( radians(:lat) ) )\n" +
            "        ) AS d\n" +
            "FROM user_road\n" +
            "WHERE is_shared = 'Y'\n" +
            "HAVING d < 5\n" +
            "ORDER BY d\n" +
            "LIMIT 0 , 20;", nativeQuery = true)
    List<UserRoadNearInterface> findNearUserRoads(@Param("lat") double lat, @Param("lng") double lng);

}
