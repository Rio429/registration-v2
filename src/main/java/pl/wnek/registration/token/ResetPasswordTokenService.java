package pl.wnek.registration.token;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.wnek.registration.model.RegistrationToken;
import pl.wnek.registration.model.ResetPasswordToken;
import pl.wnek.registration.model.User;
import pl.wnek.registration.repository.ResetPasswordTokenDao;

import java.time.LocalDateTime;

@Service
public class ResetPasswordTokenService {

    @Autowired
    private ResetPasswordTokenDao ResetPasswordTokenDao;

    @Autowired
    private TokenCreator tokenCreator;

    public ResetPasswordToken addToken(User user) {
        ResetPasswordToken token = tokenCreator.createResetToken(user);
        ResetPasswordTokenDao.findByUser(user).ifPresent(t -> token.setId(t.getId()));
        return ResetPasswordTokenDao.save(token);
    }


    //TODO refactor this dude!!
    public ResetPasswordToken getToken(String tokenText) {

        return ResetPasswordTokenDao.findByTokenText(tokenText).orElseThrow(IllegalArgumentException::new);
    }

    public ResetPasswordToken getToken(Long id) {
        return ResetPasswordTokenDao.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    public ResetPasswordToken getToken(User user) {
        return ResetPasswordTokenDao.findByUser(user).orElseThrow(IllegalArgumentException::new);
    }

    public boolean isTokenExpired(ResetPasswordToken token) {
        return token.getCreatedDate().plusDays(1).isBefore(LocalDateTime.now());
    }

    private boolean isAnotherTokenForUserInDatabase(User user) {
        return ResetPasswordTokenDao.findByUser(user).isPresent();
    }
}
