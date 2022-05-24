package com.promenade.promenadeapp.dto.User;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class UserRoadUpdateRequestDto {

    private String trailName;
    private String description;
    private List<String> hashtag;

}
