package pl.wnek.registration.controller;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import pl.wnek.registration.dictionary.TokenType;
import pl.wnek.registration.model.Token;
import pl.wnek.registration.model.User;
import pl.wnek.registration.service.*;

@RestController
public class UserController {

    private UserService userService;
    private ApplicationEventPublisher publisher;
    private TokennService tokenService;
    private SecureService secureService;

    public UserController(UserService userService, ApplicationEventPublisher publisher, TokennService tokenService,
                          SecureService secureService) {
        this.userService = userService;
        this.publisher = publisher;
        this.tokenService = tokenService;
        this.secureService = secureService;
    }

    @PostMapping(value = "/user")
    public User addUser(@RequestBody User user) {
        User addedUser = userService.addUser(user);
        Token token = tokenService.addToken(addedUser, TokenType.REGISTRATION);
        publisher.publishEvent(new RegistrationClientEvent(addedUser.getEmail(), token.getTokenText()));
        return addedUser;
    }

    @GetMapping(value = "/user")
    public String getUser() {
User user = new User("fsdfsd", "dfsfds", "Fdsfds@fdsfds.pl");
            User addedUser = userService.addUser(user);
            Token token = tokenService.addToken(addedUser, TokenType.REGISTRATION);
         tokenService.addToken(addedUser, TokenType.RESET_PASSWORD);
            publisher.publishEvent(new RegistrationClientEvent(addedUser.getEmail(), token.getTokenText()));
            secureService.addSecure(user);
            return "Fddfdfd";
//            return addedUser;
    }

    @GetMapping(value = "/test")
    public void dupa() {
        User user = new User("Wojciech", "pass1", "example@mail.pl");

        addUser(user);
    }

    @GetMapping(value = "/confirm-registration")
    public boolean confirmRegistration(@RequestParam("token") String tokenText) {
        Token token = tokenService.getToken(tokenText);
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
        Token token = tokenService.addToken(user, TokenType.REGISTRATION);
        System.out.println("DuAP!:");
        publisher.publishEvent(new RegistrationClientEvent(user.getEmail(), token.getTokenText()));
        return "ok";
    }

    @GetMapping(value = "/reset-password")
    public String resetPassword(@RequestParam("user") String userName) {
        User user = userService.getUserByName(userName);
        Token resetPasswordToken = tokenService.addToken(user, TokenType.RESET_PASSWORD);
        publisher.publishEvent(new ResetPasswordEvent(user.getEmail(), resetPasswordToken.getTokenText()));
        System.out.println("reset-pass");
        return "ok";
    }

    @GetMapping(value = "borszcz")
    public String safds(@RequestParam("userName") String userName) {
        User user = new User("fsdfsd", "dfsfds", "Fdsfds@fdsfds.pl");
        User addedUser = userService.addUser(user);
        secureService.addSecure(user);
        return "FDSFDSFSFSDFDS";
    }

    @GetMapping(value = "/change-password-request")
    public void changePassowordRequest(@RequestParam("tokenText") String tokenText, @RequestParam("name") String userName) {
        Token token = tokenService.getToken(tokenText);
        if(tokenService.isTokenExpired(token)) {
            ;
        }
        User user = userService.getUserByName(userName);
        secureService.addSecure(user);

//        Token resetPasswordToken = tokenService.addToken(user, TokenType.RESET_PASSWORD);
//        publisher.publishEvent(new ResetPasswordEvent(user.getEmail(), resetPasswordToken.getTokenText()));
//        System.out.println("reset-pass");
//        return "ok";
    }


    @GetMapping(value = "/dupa")
    public String dupa(@RequestParam("b") String b) {
        publisher.publishEvent(new ResetPasswordEvent("wojtekw429@interia.pl", "token"));
        return "dupa";
    }
}
