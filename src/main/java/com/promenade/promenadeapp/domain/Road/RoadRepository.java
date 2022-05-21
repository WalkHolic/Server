package com.promenade.promenadeapp.domain.Road;

import com.promenade.promenadeapp.dto.Road.RoadNearInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoadRepository extends JpaRepository<Road, Long> {

    // 반경 5km 이내 시작 주소가 있는 road 반환 (d, id, road_name)
    @Query(value = "SELECT *, ( 6371 * acos ( cos ( radians(start_lat) ) * cos( radians(:lat) ) * cos( radians(start_lng) - radians(:lng) ) + sin ( radians(start_lat) ) * sin( radians(:lat) ) )\n" +
            "        ) AS d\n" +
            "FROM road\n" +
            "HAVING d < 5\n" +
            "ORDER BY d\n" +
            "LIMIT 0 , 20;", nativeQuery = true)
    List<RoadNearInterface> findNearRoads(@Param("lat") double lat, @Param("lng") double lng);

}
