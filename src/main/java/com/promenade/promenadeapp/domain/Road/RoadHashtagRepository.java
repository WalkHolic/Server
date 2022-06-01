package com.promenade.promenadeapp.domain.Road;

import com.promenade.promenadeapp.domain.User.UserRoadHashtag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoadHashtagRepository extends JpaRepository<RoadHashtag, Long> {

    List<RoadHashtag> findByRoadId(Long roadId);

    @Query(value = "SELECT DISTINCT hashtag FROM road_hashtag WHERE road_id=:roadId", nativeQuery = true)
    List<String> findDistinctHashtagByRoadId(@Param("roadId") Long roadId);

    @Query("select h from RoadHashtag h where h.hashtag=:hashtag and (h.road.startLotAddr like '%수원%' or h.road.startRoadAddr like '%수원%'" +
            "or h.road.startLotAddr like '%서울%' or h.road.startRoadAddr like '%서울%')")
    List<RoadHashtag> findByHashtag(String hashtag); // 우선 서울과 수원 지역 해시태그 조회 기능만 제공

}
