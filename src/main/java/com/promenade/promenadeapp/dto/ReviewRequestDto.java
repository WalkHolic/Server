package com.promenade.promenadeapp.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewRequestDto {

    private double score;
    private String content;

}
