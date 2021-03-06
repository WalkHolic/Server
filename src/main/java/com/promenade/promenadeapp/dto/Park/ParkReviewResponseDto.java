package com.promenade.promenadeapp.dto.Park;

import com.promenade.promenadeapp.domain.Park.ParkReview;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class ParkReviewResponseDto {

    private Long id;
    private Long userId;
    private String userName;
    private Long parkId;
    private String name;
    private double score;
    private String content;
    private String pngPath;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public ParkReviewResponseDto(ParkReview parkReview) {
        this.id = parkReview.getId();
        this.userId = parkReview.getUser().getId();
        this.userName = parkReview.getUser().getName();
        this.parkId = parkReview.getPark().getId();
        this.name = parkReview.getPark().getName();
        this.score = parkReview.getScore();
        this.content = parkReview.getContent();
        this.pngPath = parkReview.getPngPath();
        this.createdDate = parkReview.getCreatedDate();
        this.modifiedDate = parkReview.getModifiedDate();
    }
}
