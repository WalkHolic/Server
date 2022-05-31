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

    List<RoadHashtag> findByHashtag(String hashtag);

}
