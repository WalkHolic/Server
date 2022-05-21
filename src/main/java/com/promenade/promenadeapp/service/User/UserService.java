package com.promenade.promenadeapp.service.User;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.gson.JsonObject;
import com.promenade.promenadeapp.domain.User.User;
import com.promenade.promenadeapp.domain.User.UserRepository;
import com.promenade.promenadeapp.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    @Value("${google.client-id}")
    private String googleClientId;

    private final UserRepository userRepository;

    @Transactional
    public UserDto verifyGoogleIdToken(String idTokenString) throws GeneralSecurityException, IOException {
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
                // Specify the CLIENT_ID of the app that accesses the backend:
                .setAudience(Collections.singletonList(googleClientId))
                // Or, if multiple clients access the backend:
                //.setAudience(Arrays.asList(CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3))
                .build();

        GoogleIdToken idToken = verifier.verify(idTokenString);

        if (idToken != null) {
            GoogleIdToken.Payload payload = idToken.getPayload();

            String googleId = payload.getSubject();
            String email = payload.getEmail();
            String name = (String) payload.get("name");
            String pictureUrl = (String) payload.get("picture");

            UserDto userDto = UserDto.builder()
                    .googleId(googleId)
                    .email(email)
                    .name(name)
                    .picture(pictureUrl)
                    .build();

            return userDto;
        } else {
            return null;
        }
    }

    @Transactional
    public User save(UserDto userDto) {
        if (userDto == null || userDto.getGoogleId() == null) {
            throw new RuntimeException("Invalid arguments");
        }
        User user = User.builder()
                .googleId(userDto.getGoogleId())
                .email(userDto.getEmail())
                .name(userDto.getName())
                .picture(userDto.getPicture())
                .build();

        return userRepository.save(user);
    }

    public Long getUserIdByGoogleId(String googleId) {
        User foundUser = userRepository.findByGoogleId(googleId);
        return foundUser.getId();
    }

    /* Test Code */
    public User saveTest(User user) {
        if (user == null || user.getEmail() == null) {
            throw new RuntimeException("Invalid arguments");
        }
        String email = user.getEmail();
        if (userRepository.existsByEmail(email)) {
            log.warn("Email already exists {}", email);
            throw new RuntimeException("Email already exists");
        }
        return userRepository.save(user);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    /* end */

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("유저가 존재하지 않습니다. id = " + id));
    }

    public User findByGoogleId(String googleId) {
        return userRepository.findByGoogleId(googleId);
    }

    public User getByCredentials(String googleId, String email) {
        return userRepository.findByGoogleIdAndEmail(googleId, email);
    }

    public Boolean existsByGoogleId(String googleId) {
        return userRepository.existsByGoogleId(googleId);
    }
}
