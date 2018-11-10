package pl.wnek.registration.validator;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Component;
import pl.wnek.registration.model.User;


@Component
public class RegistrationValidator {

    public boolean isValid(User user) {
        return user!= null &&
                isPasswordValid(user.getPassword()) &&
                isNameValid(user.getName()) &&
                isEmailValid(user.getEmail());
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 3;
    }

    private boolean isNameValid(String name) {
        return name.length() > 3;
    }

    private boolean isEmailValid(String email) {
        return email.length() > 5;
    }

}
