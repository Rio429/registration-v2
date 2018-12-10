package pl.wnek.registration.integration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import pl.wnek.registration.controller.UserController;
import pl.wnek.registration.model.Token;
import pl.wnek.registration.model.User;
import pl.wnek.registration.service.TokennService;


import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class AccessTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private UserController userController;

    @Autowired
    private TokennService tokenService;

    @Test
    public void T01_shouldReturnStatusForbiddenIfUserIsNotLoginOnHomePage() {
        //given
        String url = "/home";

        //when
        ResponseEntity<String> response = testRestTemplate.getForEntity(url, String.class);

        //then
        assertThat(response.getStatusCode(), is(HttpStatus.UNAUTHORIZED));
    }

    @Test
    public void T02_shouldReturnStatusUnauthorizedIfUserTryLoginWithoutRegisterOnHomePage() {
        //given
        String url = "/home";
        String user = "user1";
        String password = "pass1";

        //when
        ResponseEntity<String> response = testRestTemplate.withBasicAuth(user, password)
        .getForEntity(url, String.class);

        //then
        assertThat(response.getStatusCode(), is(HttpStatus.UNAUTHORIZED));
    }

    @Test
    public void T03_shouldReturnStatusOkIfUserIsNotLoginOnRegistrationPage() {
        //given
        String url = "/user";

        //when
        ResponseEntity<String> response = testRestTemplate.getForEntity(url, String.class);

        //then
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void T04_shouldReturnStatusUnauthorizedForUserWithoutConfirmByMail() {
        //given
        User user = new User("notConfrimBymail", "register", "register@mail.pl");
        testRestTemplate.postForEntity("/user", user, User.class);

        //when
        ResponseEntity<String> response = testRestTemplate
                .withBasicAuth(user.getName(), user.getPassword())
                .getForEntity("/home", String.class);

        //then
        assertThat(response.getStatusCode(), is(HttpStatus.UNAUTHORIZED));
    }


    @Test
    public void T05_shouldReturnStatusOkForUserWithConfirmation() {
        //given
        User user = new User("register", "register", "register@mail.pl");
        testRestTemplate.postForEntity("/user", user, User.class);
        Token token = tokenService.getToken(2L);
        String confirmUrl = "/confirm-registration?token=" + token.getTokenText();


        testRestTemplate.getForEntity(confirmUrl, Boolean.class);

        //when
        ResponseEntity<String> response = testRestTemplate
                .withBasicAuth(user.getName(), user.getPassword())
                .getForEntity("/home", String.class);

        //then
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }
}
