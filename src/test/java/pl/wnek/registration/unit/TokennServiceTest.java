package pl.wnek.registration.unit;

import org.junit.Before;
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
import pl.wnek.registration.service.UserService;
import pl.wnek.registration.token.TokenCreator;

import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;


@SpringBootTest
@RunWith(SpringRunner.class)
public class TokennServiceTest {

    @Autowired
    private TokennService tokennService;

    @Autowired
    private UserService userService;

    @MockBean
    private TokenCreator tokenCreator;

    @Test
    public void shouldAddToken() {
        //given
        User user = new User("user4", "pass", "mail@example.pl");
        Token token = new Token(TokenType.REGISTRATION, "tokenText#sdf", LocalDateTime.now(), user);

        when(tokenCreator.createToken(user, TokenType.REGISTRATION)).thenReturn(token);

        //when
        userService.addUser(user);
        tokennService.addToken(user, TokenType.REGISTRATION);
        Token addedToken = tokennService.getToken(token.getTokenText());

        //then
        assertThat(addedToken, is(token));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionIfTokenNotExist() {
        tokennService.getToken("uniquetokenText");
    }

    @Test
    public void shouldNotValidIfTokenIsExpired() {
        //given
        User user = new User("user3", "pass1", "example@mail.pl");
        Token token = new Token(TokenType.REGISTRATION, "text", LocalDateTime.now().minusHours(25), user);

        //when
        boolean isExpired = tokennService.isTokenExpired(token);

        //then
        assertThat(isExpired, is(true));
    }


    @Test
    public void shouldValidIfTokenIsNotExpired() {
        //given
        User user = new User("user2", "pass1", "example@mail.pl");
        Token token = new Token(TokenType.RESET_PASSWORD, "text", LocalDateTime.now().minusHours(23), user);

        //when
        boolean isExpired = tokennService.isTokenExpired(token);

        //then
        assertThat(isExpired, is(false));
    }

    @Test
    public void shouldAddDifferentTypesOfTokenToOneUser() {
        //given
        User user = new User("user15", "pass", "mail@example.pl");
        Token registrationToken = new Token(TokenType.REGISTRATION, "RegistrationTokenText",
                LocalDateTime.now(), user);
        Token resetPasswordToken = new Token(TokenType.RESET_PASSWORD, "ResetPassordTokenText",
                LocalDateTime.now(), user);

        when(tokenCreator.createToken(user, TokenType.REGISTRATION)).thenReturn(registrationToken);
        when(tokenCreator.createToken(user, TokenType.RESET_PASSWORD)).thenReturn(resetPasswordToken);

        //when
        userService.addUser(user);
        tokennService.addToken(user, TokenType.REGISTRATION);
        tokennService.addToken(user, TokenType.RESET_PASSWORD);

        Token addedRegistrationToken = tokennService.getToken(registrationToken.getTokenText());
        Token addedResetPassordToken = tokennService.getToken(resetPasswordToken.getTokenText());

        //then
        assertThat(addedRegistrationToken, is(registrationToken));
        assertThat(addedResetPassordToken, is(resetPasswordToken));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldAddOnlyOneTokenOfOneType() {
        //given
        User user = new User("user1adsa", "pass", "mail@example.pl");
        Token registrationToken1 = new Token(TokenType.REGISTRATION, "RegistrationTokenText1",
                LocalDateTime.now(), user);
        Token registrationToken2 = new Token(TokenType.REGISTRATION, "RegistrationTokenText2",
                LocalDateTime.now(), user);

        when(tokenCreator.createToken(user, TokenType.REGISTRATION))
                .thenReturn(registrationToken1).thenReturn(registrationToken2);

        //when
        userService.addUser(user);
        tokennService.addToken(user, TokenType.REGISTRATION);
        tokennService.addToken(user, TokenType.REGISTRATION);

        //then
        Token addedRegistrationToken2 = tokennService.getToken(registrationToken1.getTokenText());
    }


    //czy podmieni 1 token jeszeli sie dodda 2
    //z enabled jezeli token zostal uzyty
    //enabled jezeli ejst przedwionny ale to pewnie w integracyjyn

}
