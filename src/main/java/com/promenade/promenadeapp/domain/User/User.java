package com.promenade.promenadeapp.domain.User;

import com.promenade.promenadeapp.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "google_id", unique = true)
    private String googleId;

    private String name;

    private String email;

    private String picture;

    @Builder
    public User(String googleId, String name, String email, String picture) {
        this.googleId = googleId;
        this.name = name;
        this.email = email;
        this.picture = picture;
    }
}
