package pl.wnek.registration.integration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.wnek.registration.dictionary.TokenType;
import pl.wnek.registration.model.Token;
import pl.wnek.registration.model.User;
import pl.wnek.registration.service.TokennService;
import pl.wnek.registration.service.UserService;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;


@RunWith(SpringRunner.class)
@SpringBootTest
public class TokenIntegrationTest {

    @Autowired
    private UserService userService;

    @Autowired
    private TokennService tokenService;

    @Test(expected = IllegalArgumentException.class)
    public void whenAddTwoTokenToOneUser_firstShouldBeDeleted() {
        //given
        User user = new User("user1", "pass1", "example@mail.pl");

        //when
        User addedUser = userService.addUser(user);
        Token fistToken = tokenService.addToken(addedUser, TokenType.REGISTRATION);
        Token secondToken = tokenService.addToken(addedUser, TokenType.REGISTRATION);

        //then
        tokenService.getToken(fistToken.getTokenText());
    }

    @Test
    public void shouldGetTokenByUser() {
        //given
        User user = new User("user4", "pass1", "example@mail.pl");

        //when
        userService.addUser(user);
        Token addedToken = tokenService.addToken(user, TokenType.REGISTRATION);
        Token gettedToken = tokenService.getTokenByUserAndTokenType(user.getName(), TokenType.REGISTRATION);

        //then
        assertThat(addedToken.getTokenText(), is(gettedToken.getTokenText()));
    }


}
