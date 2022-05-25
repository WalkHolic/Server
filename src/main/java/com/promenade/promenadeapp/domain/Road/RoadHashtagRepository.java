package com.promenade.promenadeapp.domain.Road;

import com.promenade.promenadeapp.domain.User.UserRoadHashtag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoadHashtagRepository extends JpaRepository<RoadHashtag, Long> {

    List<RoadHashtag> findByRoadId(Long roadId);

    List<RoadHashtag> findByHashtag(String hashtag);

}
