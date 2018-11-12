package pl.wnek.registration.unit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import pl.wnek.registration.model.User;
import pl.wnek.registration.model.UserDetail;
import pl.wnek.registration.service.UserDetailService;
import pl.wnek.registration.service.UserService;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserDetailServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailService userDetailService;

    @Test
    public void shouldFindRegisterUserByName() {
        //given
        User user = new User("User3", "password", "mail@example.pl");
        userService.addUser(user);

        //when
        UserDetails userDetails = userDetailService.loadUserByUsername(user.getName());

        //then
        assertThat(userDetails, equalTo(new UserDetail(user)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionIfUserIsNotRegister() {
        //when
        UserDetails userDetails = userDetailService.loadUserByUsername("UserNotExist");
    }
}
