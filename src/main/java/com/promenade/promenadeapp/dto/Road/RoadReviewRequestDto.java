package com.promenade.promenadeapp.dto.Road;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RoadReviewRequestDto {

    private double score;
    private String content;
    private String png_path;

}
