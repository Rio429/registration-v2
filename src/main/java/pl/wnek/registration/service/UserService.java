package pl.wnek.registration.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.wnek.registration.model.User;
import pl.wnek.registration.repository.UserDao;
import pl.wnek.registration.validator.RegistrationValidator;

import java.util.Optional;

@Service
public class UserService {

    private UserDao userDao;
    private RegistrationValidator registrationValidator;
    private PasswordEncoder passwordEncoder;

    public UserService(UserDao userDao, RegistrationValidator registrationValidator) {
        this.userDao = userDao;
        this.registrationValidator = registrationValidator;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public User addUser(User user) {
        if (!registrationValidator.isValid(user)) throw new IllegalArgumentException();
        if (isUserWithNameInDatabase(user.getName())) throw new NullPointerException();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userDao.save(user);
    }

    public User getUserByName(String name) {
        return Optional.ofNullable(userDao.findByName(name))
                .get()
                .orElseThrow(IllegalArgumentException::new);
    }

    public User updateUser(User user) {
        return userDao.save(user);
    }

    private boolean isUserWithNameInDatabase(String name) {
        return userDao.findByName(name).isPresent();
    }
}
