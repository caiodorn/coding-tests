package com.caiodorn.codingtests.gamesys.user.business;

import com.caiodorn.codingtests.gamesys.user.persistence.BlackListedUserRepository;
import com.caiodorn.codingtests.gamesys.user.persistence.UserEntity;
import com.caiodorn.codingtests.gamesys.user.persistence.UserRepository;
import com.caiodorn.codingtests.gamesys.user.rest.User;
import com.caiodorn.codingtests.gamesys.user.util.UserObjectMapper;
import com.caiodorn.codingtests.gamesys.user.util.UserObjectMapperImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {UserServiceImpl.class, UserObjectMapperImpl.class})
@ActiveProfiles("test")
public class UserServiceImplTest {

    @Autowired
    UserService userService;

    @Autowired
    UserObjectMapper userObjectMapper;

    @MockBean
    ExclusionService exclusionServiceMock;

    @MockBean
    UserRepository userRepositoryMock;

    @MockBean
    BlackListedUserRepository blackListedUserRepositoryMock;

    @Test
    public void givenExistingUserName_whenRegister_thenShouldThrowExpectedException() {
        User user = new User("username", "a pw", "2000-12-31", "some ssn");
        given(userRepositoryMock.findByUserName(user.getUserName())).willReturn(Optional.of(new UserEntity()));

        assertThrows(UserNameAlreadyInUseException.class, () -> userService.register(user));
    }

    @Test
    public void givenNullUser_whenRegister_thenShouldThrowExpectedException() {
        assertThrows(NullPointerException.class, () -> userService.register(null));
    }

    @Test
    public void givenBlackListedUser_whenRegister_thenShouldThrowExpectedException() {
        User user = new User("username", "a pw", "2000-12-31", "some ssn");
        given(userRepositoryMock.findByDobAndSsn(LocalDate.parse(user.getDob()), user.getSsn())).willReturn(Optional.of(userObjectMapper.mapFromResourceToEntity(user)));
        given(exclusionServiceMock.validate(user.getDob(), user.getSsn())).willReturn(false);

        assertThrows(BlackListedUserException.class, () -> userService.register(user));
    }

    @Test
    public void givenValidUserOfNonBlackListedPerson_whenRegister_thenShouldPersistIt() {
        User user = new User("username", "a pw", "2000-12-31", "some ssn");
        UserEntity userEntity = userObjectMapper.mapFromResourceToEntity(user);
        given(exclusionServiceMock.validate(user.getDob(), user.getSsn())).willReturn(true);

        userService.register(user);

        verify(userRepositoryMock).save(userEntity);
    }

}
