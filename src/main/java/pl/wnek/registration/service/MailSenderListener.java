package pl.wnek.registration.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.MailMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class MailSenderListener implements ApplicationListener<RegistrationClientEvent> {

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void onApplicationEvent(RegistrationClientEvent registrationClientEvent) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        setMailData(registrationClientEvent, mailMessage);
        mailSender.send(mailMessage);
    }

    private void setMailData(RegistrationClientEvent registrationClientEvent, MailMessage mailMessage) {
        mailMessage.setTo(registrationClientEvent.getEmail());
        mailMessage.setSubject("temp");
        mailMessage.setText("localhost:8080/confirm-resgistration?token=" + registrationClientEvent.getTokenText());
    }
}
