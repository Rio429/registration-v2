package pl.wnek.registration.controller;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;
import pl.wnek.registration.model.RegistrationToken;
import pl.wnek.registration.model.ResetPasswordToken;
import pl.wnek.registration.model.User;
import pl.wnek.registration.service.RegistrationClientEvent;
import pl.wnek.registration.service.ResetPasswordEvent;
import pl.wnek.registration.token.ResetPasswordTokenService;
import pl.wnek.registration.token.TokenService;
import pl.wnek.registration.service.UserService;

@RestController
public class UserController {

    private UserService userService;
    private ApplicationEventPublisher publisher;
    private TokenService tokenService;
    private ResetPasswordTokenService resetPasswordTokenService;

    public UserController(UserService userService, ApplicationEventPublisher publisher, TokenService tokenService,
                          ResetPasswordTokenService resetPasswordTokenService) {
        this.userService = userService;
        this.publisher = publisher;
        this.tokenService = tokenService;
        this.resetPasswordTokenService = resetPasswordTokenService;
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

    @GetMapping(value = "/reset-password")
    public String resetPassword(@RequestParam("user") String userName) {
        User user = userService.getUserByName(userName);
        ResetPasswordToken resetPasswordToken = resetPasswordTokenService.addToken(user);
        publisher.publishEvent(new ResetPasswordEvent(user.getEmail(), resetPasswordToken.getTokenText()));
        System.out.println("reset-pass");
        return "ok";
    }

    @GetMapping(value = "/dupa")
    public String dupa(@RequestParam("b") String b) {
        publisher.publishEvent(new ResetPasswordEvent("wojtekw429@interia.pl", "token"));
        return "dupa";
    }
}
