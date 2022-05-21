package com.promenade.promenadeapp.domain.Park;

import com.promenade.promenadeapp.dto.Park.ParkNearInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ParkRepository extends JpaRepository<Park, Long> {

    // 반경 5km 이내 공원 조회
    @Query(value = "SELECT *, ( 6371 * acos ( cos ( radians(lat) ) * cos( radians(:lat) ) * cos( radians(lng) - radians(:lng) ) + sin ( radians(lat) ) * sin( radians(:lat) ) )\n" +
            "        ) AS d\n" +
            "FROM park_info\n" +
            "HAVING d < 5\n" +
            "ORDER BY d\n" +
            "LIMIT 0 , 20;", nativeQuery = true)
    List<ParkNearInterface> findNearParks(@Param("lat") double lat, @Param("lng") double lng);

    // 필터링 기능 + 반경 5km 이내 공원 조회
    @Query(value = "SELECT *, ( 6371 * acos ( cos ( radians(lat) ) * cos( radians(:lat) ) * cos( radians(lng) - radians(:lng) ) + sin ( radians(lat) ) * sin( radians(:lat) ) )\n" +
            "        ) AS d\n" +
            "FROM park_info\n" +
            "WHERE facility_sport like %:filter% or facility_conv like %:filter% or facility_cul like %:filter% or facility_etc like %:filter%\n" +
            "HAVING d < 5\n" +
            "ORDER BY d\n" +
            "LIMIT 0 , 20;", nativeQuery = true)
    List<ParkNearInterface> searchByFilters(@Param("lat") double lat, @Param("lng") double lng, @Param("filter") String filter);

}
