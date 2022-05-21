package com.promenade.promenadeapp.dto.Park;

import com.promenade.promenadeapp.domain.Park.ParkReview;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ParkReviewResponseDto {

    private Long id;
    private Long userId;
    private String userName;
    private Long parkId;
    private double score;
    private String content;
    private String pngPath;

    public ParkReviewResponseDto(ParkReview parkReview) {
        this.id = parkReview.getId();
        this.userId = parkReview.getUser().getId();
        this.userName = parkReview.getUser().getName();
        this.parkId = parkReview.getPark().getId();
        this.score = parkReview.getScore();
        this.content = parkReview.getContent();
        this.pngPath = parkReview.getPngPath();
    }
}
