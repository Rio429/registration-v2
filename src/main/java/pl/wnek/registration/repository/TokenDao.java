package pl.wnek.registration.repository;

import org.springframework.data.repository.CrudRepository;
import pl.wnek.registration.model.RegistrationToken;
import pl.wnek.registration.model.User;

import java.util.Optional;

public interface TokenDao extends CrudRepository<RegistrationToken, Long> {
    Optional<RegistrationToken> findByTokenText(String tokenText);
    Optional<RegistrationToken> findByUser(User user);
}
