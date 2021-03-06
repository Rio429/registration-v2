package pl.wnek.registration.unit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import pl.wnek.registration.dictionary.TokenType;
import pl.wnek.registration.model.Token;
import pl.wnek.registration.model.User;
import pl.wnek.registration.service.TokennService;
import pl.wnek.registration.token.TokenCreator;
import pl.wnek.registration.service.UserService;

import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;


@SpringBootTest
@RunWith(SpringRunner.class)
public class ResetTokenServiceTest {

    @Autowired
    private TokennService tokenService;

    @Autowired
    private UserService userService;

    @MockBean
    private TokenCreator tokenCreator;


    @Test
    public void shouldAddToken() {
        //given
        User user = new User("user1", "pass1", "example@mail.pl");
        Token token = new Token(TokenType.RESET_PASSWORD, "token", LocalDateTime.now(), user);

        //when
        User addedUser = userService.addUser(user);
        when(tokenCreator.createToken(addedUser, TokenType.RESET_PASSWORD)).thenReturn(token);
        Token addedToken = tokenService.addToken(addedUser, TokenType.RESET_PASSWORD);

        //then
        assertThat(addedToken.getTokenText().length(), greaterThan(1));
        assertThat(addedToken.getUser().getName(), is(addedUser.getName()));
        assertThat(addedToken.getCreatedDate().getDayOfMonth(), is(LocalDateTime.now().getDayOfMonth()));

    }

    @Test
    public void shouldGetToken() {
        //given
        User user = new User("user2", "pass1", "example@mail.pl");
        Token token = new Token(TokenType.RESET_PASSWORD, "tokenText", LocalDateTime.now(), user);

        //when
        User addedUser = userService.addUser(user);

        when(tokenCreator.createToken(addedUser, TokenType.RESET_PASSWORD)).thenReturn(token);
        tokenService.addToken(addedUser, TokenType.RESET_PASSWORD);
        Token gettedToken = tokenService.getToken("tokenText");

        //then
        assertThat(gettedToken.getTokenText(), equalTo(token.getTokenText()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionIfTokenNotExist() {
        tokenService.getToken("tokenText");
    }

    @Test
    public void shouldNotValidIfTokenIsExpired() {
        //given
        User user = new User("user3", "pass1", "example@mail.pl");
        Token token = new Token(TokenType.RESET_PASSWORD, "text", LocalDateTime.now().minusHours(25), user);

        //when
        boolean isExpired = tokenService.isTokenExpired(token);

        //then
        assertThat(isExpired, is(true));
    }


    @Test
    public void shouldValidIfTokenIsNotExpired() {
        //given
        User user = new User("user3", "pass1", "example@mail.pl");
        Token token = new Token(TokenType.RESET_PASSWORD, "text", LocalDateTime.now().minusHours(23), user);

        //when
        boolean isExpired = tokenService.isTokenExpired(token);

        //then
        assertThat(isExpired, is(false));
    }


}
