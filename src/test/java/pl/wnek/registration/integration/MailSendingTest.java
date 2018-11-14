package pl.wnek.registration.integration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.junit4.SpringRunner;
import pl.wnek.registration.controller.UserController;
import pl.wnek.registration.model.User;
import pl.wnek.registration.service.MailSenderListener;
import pl.wnek.registration.service.RegistrationClientEvent;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MailSendingTest {

    @Autowired
    private UserController userController;

    @MockBean
    private MailSenderListener mailSenderListener;

    @Test
    public void ifUserCanBeRegisterPublishRegistrationEvent() {
        //given
        User user = new User("user1", "pass1", "example@mail.pl");

        //when
        userController.addUser(user);

        //then
        verify(mailSenderListener, times(1)).onApplicationEvent(any(RegistrationClientEvent.class));
    }

    @Test
    public void ifUserCanNotBeRegisterDoNotPublishRegistrationEvent() {
        //given
        User user = new User("inc", "pass1", "example@mail.pl");

        //when
        try{
            userController.addUser(user);
        } catch (Exception e) {
        }

        //then
        verify(mailSenderListener, times(0)).onApplicationEvent(any(RegistrationClientEvent.class));
    }
}
