package pl.wnek.registration.service;

import org.springframework.context.ApplicationEvent;
import pl.wnek.registration.model.User;

public class RegistrationClientEvent extends ApplicationEvent {

    private String email;

    public RegistrationClientEvent(String email) {
        super(email);
        this.email = email;
        System.out.println("opublikowano");
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
