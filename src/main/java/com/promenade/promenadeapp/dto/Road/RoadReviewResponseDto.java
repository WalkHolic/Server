package com.promenade.promenadeapp.dto.Road;

import com.promenade.promenadeapp.domain.Road.RoadReview;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class RoadReviewResponseDto {

    private Long id;
    private Long userId;
    private String userName;
    private Long roadId;
    private double score;
    private String content;
    private String pngPath;

    public RoadReviewResponseDto(RoadReview roadReview) {
        this.id = roadReview.getId();
        this.userId = roadReview.getUser().getId();
        this.userName = roadReview.getUser().getName();
        this.roadId = roadReview.getRoad().getId();
        this.score = roadReview.getScore();
        this.content = roadReview.getContent();
        this.pngPath = roadReview.getPngPath();
    }
}
