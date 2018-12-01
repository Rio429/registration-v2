package pl.wnek.registration.service;

import org.springframework.stereotype.Component;
import pl.wnek.registration.model.RegistrationToken;
import pl.wnek.registration.model.User;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class TokenCreator {

    public RegistrationToken createToken(User user) {
        String tokenText = UUID.randomUUID().toString();
        return new RegistrationToken(tokenText, LocalDateTime.now(), user);
    }

}
