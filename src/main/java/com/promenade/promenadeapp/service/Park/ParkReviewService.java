package com.promenade.promenadeapp.service.Park;

import com.promenade.promenadeapp.domain.Park.ParkReview;
import com.promenade.promenadeapp.domain.Park.ParkReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ParkReviewService {

    private final ParkReviewRepository parkReviewRepository;

    public ParkReview findById(Long id) {
        return parkReviewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 리뷰가 없습니다. id=" + id));
    }

    public List<ParkReview> findByParkId(Long parkId) {
        return parkReviewRepository.findByParkId(parkId);
    }

    public Long save(ParkReview parkReview) {
        return parkReviewRepository.save(parkReview).getId();
    }

    public void delete(Long id) {
        parkReviewRepository.delete(findById(id));
    }

    public List<ParkReview> findByUserId(Long userId) {
        return parkReviewRepository.findByUserId(userId);
    }

}
