package pl.wnek.registration.repository;

import org.springframework.data.repository.CrudRepository;
import pl.wnek.registration.model.User;

import java.util.Optional;

public interface UserDao extends CrudRepository<User, Long> {
    Optional<User> findByName(String name);
}
