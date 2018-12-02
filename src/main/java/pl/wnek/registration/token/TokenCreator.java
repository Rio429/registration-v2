package pl.wnek.registration.token;

import org.springframework.stereotype.Component;
import pl.wnek.registration.model.RegistrationToken;
import pl.wnek.registration.model.ResetPasswordToken;
import pl.wnek.registration.model.User;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class TokenCreator {

    public RegistrationToken createRegistrationToken(User user) {
        String tokenText = UUID.randomUUID().toString();
        return new RegistrationToken(tokenText, LocalDateTime.now(), user);
    }

    public ResetPasswordToken createResetToken(User user) {
        String tokenText = UUID.randomUUID().toString();
        return new ResetPasswordToken(tokenText, LocalDateTime.now(), user);
    }

}
