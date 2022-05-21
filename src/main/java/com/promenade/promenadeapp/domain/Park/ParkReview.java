package com.promenade.promenadeapp.domain.Park;

import com.promenade.promenadeapp.domain.BaseTimeEntity;
import com.promenade.promenadeapp.domain.User.User;
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
public class ParkReview extends BaseTimeEntity {

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
    @JoinColumn(name = "park_id")
    private Park park;
}
