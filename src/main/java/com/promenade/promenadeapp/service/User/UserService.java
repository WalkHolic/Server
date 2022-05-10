package com.promenade.promenadeapp.service.User;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.gson.JsonObject;
import com.promenade.promenadeapp.domain.User.User;
import com.promenade.promenadeapp.domain.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

@RequiredArgsConstructor
@Service
public class UserService {

    @Value("${google_client-id}")
    private String googleClientId;

    @Value("${google.client-secret}")
    private String googleClientSecret;

    private final UserRepository userRepository;

    @Transactional
    public String saveUser(String idTokenString) throws GeneralSecurityException, IOException {
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

            JsonObject obj = new JsonObject();
            obj.addProperty("googleId", googleId);
            obj.addProperty("email", email);
            obj.addProperty("name", name);
            obj.addProperty("picture", pictureUrl);

            return obj.toString();

            //TODO: googleID도 데이터베이스에 저장하기
            // Store profile Information
//            User user = User.builder()
//                    .name(name)
//                    .email(email)
//                    .picture(pictureUrl)
//                    .build();
//
//            userService.save(user);

        } else {
            return "Invalid ID Token";
        }
    }

    @Transactional
    public Long save(User user) {
        return userRepository.save(user).getId();
    }
}
