package pl.wnek.registration.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.MailMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class TempSenderListener implements ApplicationListener<ResetPasswordEvent> {

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void onApplicationEvent(ResetPasswordEvent resetPasswordEvent) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        System.out.println("OnApplicationEvent: resetpasswordEvent");
        setMailData(resetPasswordEvent, mailMessage);
        mailSender.send(mailMessage);
    }

    private void setMailData(ResetPasswordEvent resetPasswordEvent, MailMessage mailMessage) {
        mailMessage.setTo(resetPasswordEvent.getEmail());
        mailMessage.setSubject("dupa");
        mailMessage.setText("DUPA: " + resetPasswordEvent.getTokenText());
    }
}
