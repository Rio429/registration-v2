package pl.wnek.registration.unit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.wnek.registration.model.User;
import pl.wnek.registration.service.UserService;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void shouldAddUserToDatabase() {
        //given
        User user = new User("User1", "Pass1", "email@example.pl");

        //when
        User addedUser = userService.addUser(user);

        //then
        assertThat(addedUser, equalTo(user));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotAddUserWithInvalidData() {
        //given
        User user = new User("", "", "");

        //when
        userService.addUser(user);
    }
}
