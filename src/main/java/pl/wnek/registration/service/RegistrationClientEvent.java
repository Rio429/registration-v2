package pl.wnek.registration.service;

import org.springframework.context.ApplicationEvent;

public class RegistrationClientEvent extends ApplicationEvent {

    private String email;
    private String tokenText;

    public RegistrationClientEvent(String email, String tokenText) {
        super(email);
        this.email = email;
        this.tokenText = tokenText;
        System.out.println("opublikowano");
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTokenText() {
        return tokenText;
    }

    public void setTokenText(String tokenText) {
        this.tokenText = tokenText;
    }
}
