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
import pl.wnek.registration.model.User;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@RunWith(SpringRunner.class)
public class UserControllerTest {

    @Autowired
    private UserController userController;

    @Autowired
    private TestRestTemplate testRestTemplate;

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

       //then
        assertThat(response.getBody(), is(user));
    }
}
