package pl.wnek.registration.token;

import org.springframework.stereotype.Component;
import pl.wnek.registration.dictionary.TokenType;
import pl.wnek.registration.model.Token;
import pl.wnek.registration.model.User;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class TokenCreator {

    public Token createToken(User user, TokenType tokenType) {
        String tokenText = UUID.randomUUID().toString();
        return new Token(tokenType, tokenText, LocalDateTime.now(), user);
    }

}
