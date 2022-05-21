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

    public List<ParkReview> findByParkId(Long parkId) {
        return parkReviewRepository.findByParkId(parkId);
    }

    public Long save(ParkReview parkReview) {
        return parkReviewRepository.save(parkReview).getId();
    }

}
