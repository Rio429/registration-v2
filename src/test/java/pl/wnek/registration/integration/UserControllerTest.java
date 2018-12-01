package pl.wnek.registration.integration;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import pl.wnek.registration.controller.UserController;
import pl.wnek.registration.model.RegistrationToken;
import pl.wnek.registration.model.User;
import pl.wnek.registration.repository.TokenDao;
import pl.wnek.registration.service.TokenService;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@RunWith(SpringRunner.class)
public class UserControllerTest {

    @Autowired
    private UserController userController;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private TokenDao tokenDao;

    private String url;

    @Before
    public void initUrl() {
        url = "/user";
    }

    @Test
    public void shouldAddUser() {
        //given
       User user = new User("User", "password", "mail@example.pl");

       //when
       ResponseEntity<User> response = testRestTemplate.postForEntity(url, user, User.class);

       //TODO był problem z hasłem. Po dodaniu kodowania hasła do bazy, assercja sprwadzala czy user wyzej jest taki sam jak ten niżej. Nie byli tacy sami bo wyzej jest haslo password1 a na baize juz bylo zahaszowane
       //then
        assertThat(response.getBody().getName(), is(user.getName()));
    }

    @Test
    public void shouldAddTokenToUser() {
        //given
        User user = new User("User1", "pass1", "wojtekw429@interia.pl");

        //when
        testRestTemplate.postForEntity(url, user, User.class);

        RegistrationToken token = tokenService.getToken(2L);

        //then
        assertThat(token.getUser().getName(), is(user.getName()));
    }

    @Test
    public void shouldConfirmRegistrationToUser() {
        //given //TODO poprawic 2L
        User user = new User("User1", "pass1", "example@mail.pl");
        testRestTemplate.postForEntity(url, user, User.class);
        RegistrationToken token = tokenService.getToken(2L);
        String confirmUrl = "/confirm-registration?token=" + token.getTokenText();

        //when
        ResponseEntity<Boolean> response = testRestTemplate.getForEntity(confirmUrl, Boolean.class);

        //then
        assertThat(response.getBody(), is(true));
    }

    @Test
    public void shouldConfirmRegistrationBySecondToken() {
        //given
        User user = new User("User2", "pass1", "example@mail.pl");
        testRestTemplate.postForEntity(url, user, User.class);
        testRestTemplate.getForEntity("/resend-mail", String.class);
        RegistrationToken token = tokenService.getToken(2L);
        String confirmUrl = "/confirm-registration?token=" + token.getTokenText();

        //when
        ResponseEntity<Boolean> response = testRestTemplate.getForEntity(confirmUrl, Boolean.class);

        //then
        assertThat(response.getBody(), is(true));
    }


    @Test()
    public void shouldNotConfirmRegistrationByFirstTokenIfWasSendingSecond() {
        //given
        User user = new User("User2", "pass1", "example@mail.pl");
        testRestTemplate.postForEntity(url, user, User.class);
        RegistrationToken token = tokenService.getToken(2L);
        String confirmUrl = "/confirm-registration?token=" + token.getTokenText();

        //when
        testRestTemplate.getForEntity("/resend-mail?user=" + user.getName(), String.class);
        testRestTemplate.getForEntity(confirmUrl, Boolean.class);
    }
}
