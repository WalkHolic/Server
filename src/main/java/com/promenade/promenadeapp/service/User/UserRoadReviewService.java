package com.promenade.promenadeapp.service.User;

import com.promenade.promenadeapp.domain.User.UserRoadReview;
import com.promenade.promenadeapp.domain.User.UserRoadReviewRepository;
import com.promenade.promenadeapp.dto.ReviewRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserRoadReviewService {

    private final UserRoadReviewRepository userRoadReviewRepository;

    public UserRoadReview save(UserRoadReview userRoadReview) {
        return userRoadReviewRepository.save(userRoadReview);
    }

    public UserRoadReview findById(Long id) {
        return userRoadReviewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 공유 산책로 리뷰가 없습니다. id=" + id));
    }

    public void delete(UserRoadReview userRoadReview) {
        userRoadReviewRepository.delete(userRoadReview);
    }

    public List<UserRoadReview> findByUserId(Long userId) {
        return userRoadReviewRepository.findByUserId(userId);
    }

    public List<UserRoadReview> findByUserRoadId(Long userRoadId) {
        return userRoadReviewRepository.findByUserRoadId(userRoadId);
    }

    public UserRoadReview update(Long id, ReviewRequestDto reviewRequestDto, String pictureUrl) {
        UserRoadReview roadReview = userRoadReviewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 리뷰가 없습니다. id=" + id));
        return roadReview.update(reviewRequestDto, pictureUrl);
    }
}
