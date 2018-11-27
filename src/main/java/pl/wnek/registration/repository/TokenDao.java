package pl.wnek.registration.repository;

import org.springframework.data.repository.CrudRepository;
import pl.wnek.registration.model.Token;
import pl.wnek.registration.model.User;

import java.util.Optional;

public interface TokenDao extends CrudRepository<Token, Long> {
    Optional<Token> findByToken(String tokenText);
    Optional<Token> findByUser(User user);
}
