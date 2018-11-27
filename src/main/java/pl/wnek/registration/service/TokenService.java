package pl.wnek.registration.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.wnek.registration.model.Token;
import pl.wnek.registration.model.User;
import pl.wnek.registration.repository.TokenDao;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TokenService {

    @Autowired
    private TokenDao tokenDao;

    @Autowired
    private TokenCreator tokenCreator;

    public Token addToken(User user) {
        Token token = tokenCreator.createToken(user);
        tokenDao.findByUser(user).ifPresent(t -> token.setId(t.getId()));
        return tokenDao.save(token);
    }


    //TODO refactor this dude!!
    public Token getToken(String tokenText) {

        return tokenDao.findByToken(tokenText).orElseThrow(IllegalArgumentException::new);
    }

    public Token getToken(Long id) {
        return tokenDao.findById(id).orElseThrow(IllegalArgumentException::new);
   }

    public Token getToken(User user) {
        return tokenDao.findByUser(user).orElseThrow(IllegalArgumentException::new);
    }

    public boolean isTokenExpired(Token token) {
        return token.getCreatedDate().plusDays(1).isBefore(LocalDateTime.now());
    }

    private boolean isAnotherTokenForUserInDatabase(User user) {
        return tokenDao.findByUser(user).isPresent();
    }
}
