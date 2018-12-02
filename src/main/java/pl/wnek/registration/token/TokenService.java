package pl.wnek.registration.token;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.wnek.registration.model.RegistrationToken;
import pl.wnek.registration.model.User;
import pl.wnek.registration.repository.TokenDao;

import java.time.LocalDateTime;

@Service
public class TokenService {

    @Autowired
    private TokenDao tokenDao;

    @Autowired
    private TokenCreator tokenCreator;

    public RegistrationToken addToken(User user) {
        RegistrationToken token = tokenCreator.createRegistrationToken(user);
        tokenDao.findByUser(user).ifPresent(t -> token.setId(t.getId()));
        return tokenDao.save(token);
    }


    //TODO refactor this dude!!
    public RegistrationToken getToken(String tokenText) {

        return tokenDao.findByTokenText(tokenText).orElseThrow(IllegalArgumentException::new);
    }

    public RegistrationToken getToken(Long id) {
        return tokenDao.findById(id).orElseThrow(IllegalArgumentException::new);
   }

    public RegistrationToken getToken(User user) {
        return tokenDao.findByUser(user).orElseThrow(IllegalArgumentException::new);
    }

    public boolean isTokenExpired(RegistrationToken token) {
        return token.getCreatedDate().plusDays(1).isBefore(LocalDateTime.now());
    }

    private boolean isAnotherTokenForUserInDatabase(User user) {
        return tokenDao.findByUser(user).isPresent();
    }
}
