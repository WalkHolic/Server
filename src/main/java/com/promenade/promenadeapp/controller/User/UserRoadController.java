package com.promenade.promenadeapp.controller.User;

import com.promenade.promenadeapp.domain.User.UserRoad;
import com.promenade.promenadeapp.dto.UserRoadRequestDto;
import com.promenade.promenadeapp.service.User.UserRoadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class UserRoadController {

    private final UserRoadService userRoadService;

    @PostMapping("/user/customRoads")
    public Long save(@RequestBody UserRoadRequestDto requestDto) {
        return userRoadService.saveUserRoad(requestDto);
    }

    @GetMapping("/user/customRoads")
    public List<UserRoad> getUserRoads() {
        return userRoadService.getUserRoads();
    }

}
