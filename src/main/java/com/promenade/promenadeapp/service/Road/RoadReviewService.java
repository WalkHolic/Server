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

    public RoadReview findById(Long id) {
        return roadReviewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 리뷰가 없습니다. id=" + id));
    }

    public List<RoadReview> findByRoadId(Long roadId) {
        return roadReviewRepository.findByRoadId(roadId);
    }

    public RoadReview save(RoadReview roadReview) {
        return roadReviewRepository.save(roadReview);
    }

    public void delete(Long id) {
        roadReviewRepository.delete(findById(id));
    }

    public List<RoadReview> findByUserId(Long userId) {
        return roadReviewRepository.findByUserId(userId);
    }
}
