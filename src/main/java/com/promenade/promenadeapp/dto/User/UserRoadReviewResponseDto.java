package com.promenade.promenadeapp.dto.User;

import com.promenade.promenadeapp.domain.User.UserRoadReview;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class UserRoadReviewResponseDto {

    private Long id;
    private Long userId;
    private String userName;
    private Long userRoadId;
    private double score;
    private String content;
    private String pngPath;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public UserRoadReviewResponseDto(UserRoadReview userRoadReview) {
        this.id = userRoadReview.getId();
        this.userId = userRoadReview.getUser().getId();
        this.userName = userRoadReview.getUser().getName();
        this.userRoadId = userRoadReview.getUserRoad().getId();
        this.score = userRoadReview.getScore();
        this.content = userRoadReview.getContent();
        this.pngPath = userRoadReview.getPngPath();
        this.createdDate = userRoadReview.getCreatedDate();
        this.modifiedDate = userRoadReview.getModifiedDate();
    }

}
