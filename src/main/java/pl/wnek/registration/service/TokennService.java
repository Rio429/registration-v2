package pl.wnek.registration.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.wnek.registration.dictionary.TokenType;
import pl.wnek.registration.model.Token;
import pl.wnek.registration.model.User;
import pl.wnek.registration.repository.TokennDao;
import pl.wnek.registration.token.TokenCreator;

import java.time.LocalDateTime;

@Service
public class TokennService {

    @Autowired
    private TokennDao tokennDao;

    @Autowired
    private TokenCreator tokenCreator;

    public Token addToken(User user, TokenType tokenType) {
        Token token = tokenCreator.createToken(user, tokenType);

        //todo zrobic tak by sprawdzalo czy podany token istnieje
        tokennDao.findByUserNameAndTokenType(user.getName(), tokenType).ifPresent(t -> token.setId(t.getId()));
        return tokennDao.save(token);
    }

    public Token getToken(Long id) {
        return tokennDao.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    public Token getToken(String tokenText) {
        return tokennDao.findByTokenText(tokenText).orElseThrow(IllegalArgumentException::new);
    }

    public Token getTokenByUserAndTokenType(String userName, TokenType tokenType) {
        return tokennDao.findByUserNameAndTokenType(userName, tokenType).orElseThrow(IllegalArgumentException::new);
    }

    public boolean isTokenExpired(Token token) {
        return token.getCreatedDate().plusDays(1).isBefore(LocalDateTime.now());
    }

}
