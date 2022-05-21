package com.promenade.promenadeapp.controller.User;

import com.promenade.promenadeapp.domain.User.User;
import com.promenade.promenadeapp.dto.ResponseDto;
import com.promenade.promenadeapp.dto.User.UserDto;
import com.promenade.promenadeapp.security.TokenProvider;
import com.promenade.promenadeapp.service.User.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class UserController {

    private final UserService userService;

    private final TokenProvider tokenProvider;

    @PostMapping("/google")
    public ResponseEntity<?> saveOrLogin(@RequestBody String idTokenString) throws GeneralSecurityException, IOException {
        try {
            UserDto userDto = userService.verifyGoogleIdToken(idTokenString);
            if (userDto == null) { // id token이 유효하지 않을 때 예외 처리
                ResponseDto<Object> responseDto = ResponseDto.builder()
                        .error("google id token이 유효하지 않습니다.").build();
                return ResponseEntity.badRequest().body(responseDto);
            }

            // Login (이미 있는 사용자임)
            if (userService.existsByGoogleId(userDto.getGoogleId())) {
                User user = userService.getByCredentials(userDto.getGoogleId(), userDto.getEmail());
                if (user != null) {
                    String token = tokenProvider.createToken(user);
                    UserDto responseUserDto = UserDto.builder()
                            .id(user.getId())
                            .googleId(user.getGoogleId())
                            .email(user.getEmail())
                            .name(user.getName())
                            .picture(user.getPicture())
                            .token(token)
                            .build();
                    System.out.println("사용자가 로그인 되었습니다. id=" + user.getId());
                    ResponseDto<UserDto> response = ResponseDto.<UserDto>builder()
                            .data(Arrays.asList(responseUserDto))
                            .build();
                    return ResponseEntity.ok(response);
                } else {
                    ResponseDto responseDto = ResponseDto.builder()
                            .error("Login failed")
                            .build();
                    return ResponseEntity.badRequest().body(responseDto);
                }
            }
            // Save (회원가입 DB에 저장)
            else {
                User user = userService.save(userDto);
                String token = tokenProvider.createToken(user);
                UserDto responseUserDto = UserDto.builder()
                        .id(user.getId())
                        .googleId(user.getGoogleId())
                        .email(user.getEmail())
                        .name(user.getName())
                        .picture(user.getPicture())
                        .token(token)
                        .build();
                System.out.println("새로운 사용자가 db에 저장되었습니다. id=" + user.getId());
                ResponseDto<UserDto> response = ResponseDto.<UserDto>builder()
                        .data(Arrays.asList(responseUserDto))
                        .build();
                return ResponseEntity.ok(response); // save한 후에도 JWT token 전달해주기
            }
        } catch (Exception e) {
            ResponseDto responseDto = ResponseDto.builder().error(e.getMessage()).build();
            return ResponseEntity.badRequest().body(responseDto);
        }
    }

    /* Test code */
    @PostMapping("/test/signup")
    public ResponseEntity<?> registerUserTest(@RequestBody UserDto userDto) {
        try {
            User user = User.builder()
                    .googleId(userDto.getGoogleId())
                    .email(userDto.getEmail())
                    .name(userDto.getName())
                    .picture(userDto.getPicture())
                    .build();
            User registeredUser = userService.saveTest(user);
            UserDto responseUserDto = UserDto.builder()
                    .id(registeredUser.getId())
                    .googleId(registeredUser.getGoogleId())
                    .email(registeredUser.getEmail())
                    .name(registeredUser.getName())
                    .picture(registeredUser.getPicture())
                    .build();
            return ResponseEntity.ok().body(responseUserDto);
        } catch (Exception e) {
            ResponseDto responseDto = ResponseDto.builder().error(e.getMessage()).build();
            return ResponseEntity.badRequest().body(responseDto);
        }
    }

    @PostMapping("/test/signin")
    public ResponseEntity<?> authenticateTest(@RequestBody UserDto userDto) {
        User user = userService.findByEmail(userDto.getEmail());
        if (user != null) {
            String token = tokenProvider.createToken(user);
            UserDto responseUserDto = UserDto.builder()
                    .id(user.getId())
                    .googleId(user.getGoogleId())
                    .email(user.getEmail())
                    .name(user.getName())
                    .picture(user.getPicture())
                    .token(token)
                    .build();
            ResponseDto response = ResponseDto.<UserDto>builder()
                    .data(Arrays.asList(responseUserDto))
                    .build();
            return ResponseEntity.ok().body(response);
        } else {
            ResponseDto responseDto = ResponseDto.builder()
                    .error("Login failed")
                    .build();
            return ResponseEntity.badRequest().body(responseDto);
        }
    }
    /* end */
}
