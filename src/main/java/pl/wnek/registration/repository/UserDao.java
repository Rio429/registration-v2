package pl.wnek.registration.repository;

import org.springframework.data.repository.CrudRepository;
import pl.wnek.registration.model.User;

public interface UserDao extends CrudRepository<User, Long> {
}
