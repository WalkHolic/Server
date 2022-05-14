package com.promenade.promenadeapp.domain.Park;

import com.promenade.promenadeapp.dto.ParkNearInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ParkRepository extends JpaRepository<Park, Long> {

    // 반경 5km 이내 시작 주소가 있는 road 반환 (d, id, road_name)
    @Query(value = "SELECT ( 6371 * acos ( cos ( radians(lat) ) * cos( radians(:lat) ) * cos( radians(lng) - radians(:lng) ) + sin ( radians(lat) ) * sin( radians(:lat) ) )\n" +
            "        ) AS d, id, park_id, name, type, addr_new, addr, lat, lng, area, facility_sport, facility_amuse, facility_conv, facility_cul, facility_etc, updated, manage_agency, contact, data_base_date, provider_code, provider_name\n" +
            "FROM park_info\n" +
            "HAVING d < 5\n" +
            "ORDER BY d\n" +
            "LIMIT 0 , 20;", nativeQuery = true)
    List<ParkNearInterface> findNearParks(@Param("lat") double lat, @Param("lng") double lng);

}
