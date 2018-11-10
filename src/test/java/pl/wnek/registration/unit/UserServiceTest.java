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
    public void T1_shouldAddUserToDatabase() {
        //given
        User user = new User("User1", "Pass1", "email@example.pl");

        //when
        User addedUser = userService.addUser(user);
        User userFromDb = userService.getUserByName(user.getName());

        //then
        assertThat(addedUser, equalTo(user));
        assertThat(userFromDb, equalTo(user));
    }

    @Test(expected = IllegalArgumentException.class)
    public void T2_shouldNotAddUserWithInvalidData() {
        //given
        User user = new User("", "", "");

        //when
        userService.addUser(user);
    }

    @Test
    public void T3_shouldGetUserFromDatabaseByName() {
        //given
        User user = new User("User2", "Pass2", "email2@example.pl");
        userService.addUser(user);

        //when
        User responseUser = userService.getUserByName(user.getName());

        //then
        assertThat(responseUser, equalTo(user));
    }


    @Test(expected = IllegalArgumentException.class)
    public void T4_shouldThrowExceptionIfGettingUserIsNotInDatabase() {
        User responseUser = userService.getUserByName("UserNotExist");
    }

    @Test(expected = NullPointerException.class)
    public void T5_shouldThrowExceptionIfUserNameIsInDatabase() {
        //given
        User user1 = new User("User3", "Pass1", "email@example.pl");
        User user2 = new User("User3", "Pass1", "email@example.pl");

        //when
        userService.addUser(user1);
        userService.addUser(user2);
    }
}
