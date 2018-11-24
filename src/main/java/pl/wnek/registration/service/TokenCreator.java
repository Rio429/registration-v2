package pl.wnek.registration.service;

import org.springframework.stereotype.Component;
import pl.wnek.registration.model.Token;
import pl.wnek.registration.model.User;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class TokenCreator {

    public Token createToken(User user) {
        String tokenText = UUID.randomUUID().toString();
        return new Token(tokenText, LocalDateTime.now(), user);
    }

}
