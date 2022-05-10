package com.promenade.promenadeapp.controller.Auth;

import com.promenade.promenadeapp.service.User.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.GeneralSecurityException;

@RequiredArgsConstructor
@RestController
public class TokenController {

    private final UserService userService;

    @PostMapping("/token")
    public String save(@RequestBody String idTokenString) throws GeneralSecurityException, IOException {
        return userService.saveUser(idTokenString);
    }
}
