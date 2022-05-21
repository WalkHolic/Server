package com.promenade.promenadeapp.dto.User;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long id;
    private String token;
    private String googleId;
    private String email;
    private String name;
    private String picture;

}
