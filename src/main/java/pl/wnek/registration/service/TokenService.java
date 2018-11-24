package pl.wnek.registration.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.wnek.registration.model.Token;
import pl.wnek.registration.model.User;
import pl.wnek.registration.repository.TokenDao;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class TokenService {

    @Autowired
    private TokenDao tokenDao;

    @Autowired
    private TokenCreator tokenCreator;

    public Token addToken(User user) {
        Token token = tokenCreator.createToken(user);
        return tokenDao.save(token);
    }

    public Token getToken(String tokenText) {
        return Optional.ofNullable(tokenDao.findByToken(tokenText))
                .get()
                .orElseThrow(IllegalArgumentException::new);
    }

    public Token getToken(Long id) {
        return Optional.ofNullable(tokenDao.findById(id))
                .get()
                .orElseThrow(IllegalArgumentException::new);
    }

    public boolean isTokenExpired(Token token) {
        return token.getCreatedDate().plusDays(1).isBefore(LocalDateTime.now());
    }
}
