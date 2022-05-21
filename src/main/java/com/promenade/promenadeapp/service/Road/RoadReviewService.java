package com.promenade.promenadeapp.service.Road;

import com.promenade.promenadeapp.domain.Road.RoadReview;
import com.promenade.promenadeapp.domain.Road.RoadReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RoadReviewService {

    private final RoadReviewRepository roadReviewRepository;

    public List<RoadReview> findByRoadId(Long roadId) {
        return roadReviewRepository.findByRoadId(roadId);
    }

    public RoadReview save(RoadReview roadReview) {
        return roadReviewRepository.save(roadReview);
    }
}
