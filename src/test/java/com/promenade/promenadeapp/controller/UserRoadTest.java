//package com.promenade.promenadeapp.controller;
//
//
//import com.promenade.promenadeapp.domain.User.User;
//import com.promenade.promenadeapp.domain.User.UserRoad;
//import com.promenade.promenadeapp.domain.User.UserRoadRepository;
//import com.promenade.promenadeapp.dto.UserRoadRequestDto;
//import com.promenade.promenadeapp.service.User.UserRoadService;
//import com.promenade.promenadeapp.service.User.UserService;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.boot.web.server.LocalServerPort;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@ExtendWith(SpringExtension.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//public class UserRoadTest {
//
//    @LocalServerPort
//    private int port;
//
//    @Autowired
//    private UserRoadService userRoadService;
//
//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private UserRoadRepository userRoadRepository;
//
//    @Autowired
//    private TestRestTemplate testRestTemplate;
//
//    @AfterEach
//    public void cleanup() {
//        userRoadRepository.deleteAll();
//    }
//
//    @Test
//    public void UserRoad_등록하기() throws Exception {
//        //given
//        Long user_id = 28L; // 데이터베이스 상에 user_id가 28인 유저가 있어야 함
//        String trail_name = "나만의 산책로";
//        String description = "내 집앞 풍산역 근처 산책로입니다";
//        double distance = 3.235;
//        String start_addr = "경기도 수원시 팔달구 우만동 04-5";
//        String trail_point = "[{" +
//                "        lat: 37.2781968," +
//                "        lon: 127.0429034" +
//                "    }, {" +
//                "        lat: 37.279260473122769," +
//                "        lon: 127.04385995864686" +
//                "    }]";
//        UserRoadRequestDto requestDto = UserRoadRequestDto.builder()
//                .user_id(user_id)
//                .trail_name(trail_name)
//                .description(description)
//                .distance(distance)
//                .start_addr(start_addr)
//                .trail_point(trail_point)
//                .build();
//
////        userRoadService.saveUserRoad(requestDto);
//
//        String url = "http://localhost:" + port + "/user/customRoads";
//
//        //when
//        ResponseEntity<String> responseEntity = testRestTemplate.postForEntity(url, requestDto, String.class);
//
//        //then
//        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
////        assertThat(responseEntity.getBody()).isGreaterThan(0L);
//        List<UserRoad> all = userRoadService.getUserRoads();
//        assertThat(all.get(0).getUser().getId()).isNotNull();
//        assertThat(all.get(0).getTrail_name()).isEqualTo(trail_name);
//        assertThat(all.get(0).getDescription()).isEqualTo(description);
//        assertThat(all.get(0).getDistance()).isEqualTo(distance);
//        assertThat(all.get(0).getStart_addr()).isEqualTo(start_addr);
//        assertThat(all.get(0).getTrail_point()).isEqualTo(trail_point);
//    }
//}
