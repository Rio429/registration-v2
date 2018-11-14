package pl.wnek.registration.controller;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.wnek.registration.model.User;
import pl.wnek.registration.service.RegistrationClientEvent;
import pl.wnek.registration.service.UserService;

@RestController
public class UserController {

    private UserService userService;
    private ApplicationEventPublisher publisher;

    public UserController(UserService userService, ApplicationEventPublisher publisher) {
        this.userService = userService;
        this.publisher = publisher;
    }

    @PostMapping(value = "/user")
    public User addUser(@RequestBody User user) {
        User addedUser = userService.addUser(user);
        publisher.publishEvent(new RegistrationClientEvent(addedUser.getEmail()));
        return addedUser;
    }

    @GetMapping(value = "/user")
    public String getUser() {
        return "Hello World";
    }

    @GetMapping(value = "/home")
    public String tem2p() {
        return "Hello World2";
    }
}
