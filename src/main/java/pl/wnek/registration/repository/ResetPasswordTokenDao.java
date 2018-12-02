package pl.wnek.registration.repository;

import org.springframework.data.repository.CrudRepository;
import pl.wnek.registration.model.RegistrationToken;
import pl.wnek.registration.model.ResetPasswordToken;
import pl.wnek.registration.model.User;

import java.util.Optional;

public interface ResetPasswordTokenDao extends CrudRepository<ResetPasswordToken, Long> {
    Optional<ResetPasswordToken> findByTokenText(String tokenText);
    Optional<ResetPasswordToken> findByUser(User user);
}

