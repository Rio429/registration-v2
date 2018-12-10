package pl.wnek.registration.repository;

import org.springframework.data.repository.CrudRepository;
import pl.wnek.registration.dictionary.TokenType;
import pl.wnek.registration.model.Token;
import pl.wnek.registration.model.User;

import java.util.Optional;

public interface TokennDao extends CrudRepository<Token, Long> {
    Optional<Token> findByTokenText(String tokenText);
    Optional<Token> findByUserNameAndTokenType(String name, TokenType tokenType);

}
