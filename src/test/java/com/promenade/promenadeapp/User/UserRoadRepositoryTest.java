package com.promenade.promenadeapp.User;


import com.promenade.promenadeapp.domain.User.User;
import com.promenade.promenadeapp.domain.User.UserRepository;
import com.promenade.promenadeapp.domain.User.UserRoad;
import com.promenade.promenadeapp.domain.User.UserRoadRepository;
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
public class UserRoadRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserRoadRepository userRoadRepository;

    @AfterEach
    public void cleanup() {
        userRoadRepository.deleteAll();
        userRepository.deleteAll();
    }


    @Test
    public void 사용자등록_커스텀산책로등록_불러오기() {
        //given
        User user = User.builder()
                .name("철수")
                .email("chelsu3@google.com")
                .password("156456465")
                .build();

        userRepository.save(user);

        String trail_name = "나만의 산책로";
        String description = "내 집앞 풍산역 근처 산책로입니다";
        double distance = 3.235;
        String start_addr = "경기도 수원시 팔달구 우만동 04-5";
        String trail_point = "[{" +
                "        lat: 37.2781968," +
                "        lon: 127.0429034" +
                "    }, {" +
                "        lat: 37.279260473122769," +
                "        lon: 127.04385995864686" +
                "    }]";
        UserRoad userRoad = UserRoad.builder()
                .trail_name(trail_name)
                .description(description)
                .distance(distance)
                .start_addr(start_addr)
                .trail_point(trail_point)
                .user(user)
                .build();

        userRoadRepository.save(userRoad);

        //when
        List<UserRoad> userRoads = userRoadRepository.findAll();

        //then
        UserRoad foundUserRoad = userRoads.get(0);
        assertThat(foundUserRoad.getTrail_name()).isEqualTo(trail_name);
        assertThat(foundUserRoad.getDescription()).isEqualTo(description);
        assertThat(foundUserRoad.getDistance()).isEqualTo(distance);
        assertThat(foundUserRoad.getStart_addr()).isEqualTo(start_addr);
        assertThat(foundUserRoad.getTrail_point()).isEqualTo(trail_point);
        assertThat(foundUserRoad.getId()).isNotNull();
        assertThat(foundUserRoad.getUser().getId()).isNotNull();

    }
}
