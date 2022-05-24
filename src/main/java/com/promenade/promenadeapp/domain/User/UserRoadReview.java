package com.promenade.promenadeapp.domain.User;

import com.promenade.promenadeapp.domain.BaseTimeEntity;
import com.promenade.promenadeapp.dto.ReviewRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class UserRoadReview extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double score;

    private String content;

    @Column(name = "png_path")
    private String pngPath;

    @ManyToOne // 단방향
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne // 단방향
    @JoinColumn(name = "user_road_id")
    private UserRoad userRoad;

    public UserRoadReview update(ReviewRequestDto reviewRequestDto, String pictureUrl) {
        this.score = reviewRequestDto.getScore();
        this.content = reviewRequestDto.getContent();
        this.pngPath = pictureUrl;

        return this;
    }
}
