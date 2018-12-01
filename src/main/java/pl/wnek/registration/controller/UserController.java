package pl.wnek.registration.controller;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;
import pl.wnek.registration.model.RegistrationToken;
import pl.wnek.registration.model.User;
import pl.wnek.registration.service.RegistrationClientEvent;
import pl.wnek.registration.service.TokenService;
import pl.wnek.registration.service.UserService;

@RestController
public class UserController {

    private UserService userService;
    private ApplicationEventPublisher publisher;
    private TokenService tokenService;

    public UserController(UserService userService, ApplicationEventPublisher publisher, TokenService tokenService) {
        this.userService = userService;
        this.publisher = publisher;
        this.tokenService = tokenService;
    }

    @PostMapping(value = "/user")
    public User addUser(@RequestBody User user) {
        User addedUser = userService.addUser(user);
        RegistrationToken token = tokenService.addToken(addedUser);
        publisher.publishEvent(new RegistrationClientEvent(addedUser.getEmail(), token.getTokenText()));
        return addedUser;
    }

    @GetMapping(value = "/user")
    public String getUser() {
        return "Hello World";
    }

    @GetMapping(value = "/test")
    public void dupa() {
        User user = new User("Wojciech", "pass1", "example@mail.pl");

        addUser(user);
    }

    @GetMapping(value = "/confirm-registration")
    public boolean confirmRegistration(@RequestParam("token") String tokenText) {
        RegistrationToken token = tokenService.getToken(tokenText);
        if(tokenService.isTokenExpired(token)) {
            return false;
        }
        User user = token.getUser();
        user.setEnabled(true);
        userService.updateUser(user);
        return true;
    }

    @GetMapping(value = "/home")
    public String tem2p() {
        System.out.println("home");
        return "Hello World2";
    }

    @GetMapping(value = "/resend-mail")
    public String resendMail(@RequestParam("user") String userName) {
        System.out.println("DuAP!:");

        User user = userService.getUserByName(userName);
        RegistrationToken token = tokenService.addToken(user);
        System.out.println("DuAP!:");
        publisher.publishEvent(new RegistrationClientEvent(user.getEmail(), token.getTokenText()));
        return "ok";
    }
}
