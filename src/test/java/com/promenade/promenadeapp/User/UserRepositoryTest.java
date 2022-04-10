package com.promenade.promenadeapp.User;


import com.promenade.promenadeapp.domain.User.User;
import com.promenade.promenadeapp.domain.User.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @AfterEach
    public void cleanup() {
        userRepository.deleteAll();
    }

    @Test
    public void 사용자_등록및불러오기() {
        //given
        User user = User.builder()
                .name("철수")
                .email("chelsu3@google.com")
                .password("156456465")
                .build();

        userRepository.save(user);

        //when
        List<User> userList = userRepository.findAll();

        //then
        User foundUser = userList.get(0);
        assertThat(foundUser.getEmail()).isEqualTo("chelsu3@google.com");
    }
}
