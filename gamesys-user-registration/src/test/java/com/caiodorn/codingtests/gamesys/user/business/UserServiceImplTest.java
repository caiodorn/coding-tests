package com.caiodorn.codingtests.gamesys.user.business;

import com.caiodorn.codingtests.gamesys.user.persistence.UserEntity;
import com.caiodorn.codingtests.gamesys.user.persistence.UserRepository;
import com.caiodorn.codingtests.gamesys.user.rest.User;
import com.caiodorn.codingtests.gamesys.user.util.UserObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    UserService userService;

    @Mock
    UserObjectMapper userObjectMapper;

    @Mock
    ExclusionService exclusionServiceMock;

    @Mock
    UserRepository userRepositoryMock;

    @BeforeEach
    public void setup() {
        this.userService = new UserServiceImpl(exclusionServiceMock, userObjectMapper, userRepositoryMock);
    }

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
