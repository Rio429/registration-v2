package pl.wnek.registration.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import pl.wnek.registration.model.User;
import pl.wnek.registration.model.UserDetail;

@Service
public class UserDetailService implements UserDetailsService {

    private UserService userService;

    public UserDetailService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetail loadUserByUsername(String name) {
        User user = userService.getUserByName(name);
        return new UserDetail(user);
    }
}
