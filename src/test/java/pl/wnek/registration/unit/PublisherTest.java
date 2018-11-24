package pl.wnek.registration.unit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.junit4.SpringRunner;
import pl.wnek.registration.model.Token;
import pl.wnek.registration.model.User;
import pl.wnek.registration.service.MailSenderListener;
import pl.wnek.registration.service.RegistrationClientEvent;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@RunWith(SpringRunner.class)
public class PublisherTest {

    @Autowired
    private ApplicationEventPublisher publisher;

    @MockBean
    private MailSenderListener mailSenderListener;

    @Test
    public void publishRegistrationEventAndCheckIfMailSenderListenerIsRunning() {
        //given
        User user = new User("user1", "pass1", "mail@example.pl");
        String token = "token";

        //when
        publisher.publishEvent(new RegistrationClientEvent(user.getEmail(), token));

        //then
        verify(mailSenderListener, times(1)).onApplicationEvent(any(RegistrationClientEvent.class));
    }
}
