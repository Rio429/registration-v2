package pl.wnek.registration.unit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.wnek.registration.model.User;
import pl.wnek.registration.validator.RegistrationValidator;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ValidatorTest {

    @Autowired
    private RegistrationValidator validator;

    @Test
    public void shouldNotValidIfPasswordIsNotCorrect() {
        //given
        User user = new User("CorrectName", "inc", "correct@mail.pl");

        //when
        boolean isValid = validator.isValid(user);

        //then
        assertFalse(isValid);
    }

    @Test
    public void shouldNotValidIfNameIsNotCorrect() {
        //given
        User user = new User("inc", "correctPass", "correct@mail.pl");

        //when
        boolean isValid = validator.isValid(user);

        //then
        assertFalse(isValid);
    }

    @Test
    public void shouldNotValidIfEmailIsNotCorrect() {
        //given
        User user = new User("correctName", "correctPass", "inc");

        //when
        boolean isValid = validator.isValid(user);

        //then
        assertFalse(isValid);
    }

    @Test
    public void shouldNotValidIfUserIsNull() {
        //given
        User user = null;

        //when
        boolean isValid = validator.isValid(user);

        //then
        assertFalse(isValid);
    }

    @Test
    public void shouldValidCorrectUser() {
        //given
        User user = new User("correctName", "correctPassword", "correct@mail.pl");

        //when
        boolean isValid = validator.isValid(user);

        //then
        assertTrue(isValid);
    }
}
