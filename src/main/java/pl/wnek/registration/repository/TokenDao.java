package pl.wnek.registration.repository;

import org.springframework.data.repository.CrudRepository;
import pl.wnek.registration.model.Token;

import java.util.Optional;

public interface TokenDao extends CrudRepository<Token, Long> {
    Optional<Token> findByToken(String tokenText);
}
