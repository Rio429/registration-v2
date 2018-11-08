package pl.wnek.registration.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistrationController {

    @GetMapping(value = "/registration")
    public String temp() {
        return "Hello World";
    }

    @GetMapping(value = "/home")
    public String tem2p() {
        return "Hello World2";
    }
}
