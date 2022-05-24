package com.promenade.promenadeapp.domain.Road;

import com.promenade.promenadeapp.domain.BaseTimeEntity;
import com.promenade.promenadeapp.domain.User.User;
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
public class RoadReview extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double score;

    private String content;

    @Column(name = "png_path")
    private String pngPath;

    @ManyToOne // RoadPath.road (단방향)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne // RoadPath.road (단방향)
    @JoinColumn(name = "road_id")
    private Road road;

    public RoadReview update(ReviewRequestDto reviewRequestDto, String pictureUrl) {
        this.score = reviewRequestDto.getScore();
        this.content = reviewRequestDto.getContent();
        this.pngPath = pictureUrl;

        return this;
    }

}
