package pl.wnek.registration.service;

import org.springframework.stereotype.Service;
import pl.wnek.registration.model.User;
import pl.wnek.registration.repository.UserDao;
import pl.wnek.registration.validator.RegistrationValidator;

@Service
public class UserService {

    private UserDao userDao;
    private RegistrationValidator registrationValidator;

    public UserService(UserDao userDao, RegistrationValidator registrationValidator) {
        this.userDao = userDao;
        this.registrationValidator = registrationValidator;
    }

    public User addUser(User user) {
        if (!registrationValidator.isValid(user)) throw new IllegalArgumentException();
        return userDao.save(user);
    }
}
