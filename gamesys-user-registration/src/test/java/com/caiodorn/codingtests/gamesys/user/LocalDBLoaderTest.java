package com.caiodorn.codingtests.gamesys.user;

import com.caiodorn.codingtests.gamesys.user.persistence.BlackListedUserEntity;
import com.caiodorn.codingtests.gamesys.user.persistence.BlackListedUserRepository;
import com.caiodorn.codingtests.gamesys.user.persistence.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = LocalDBLoader.class)
@ActiveProfiles("test")
public class LocalDBLoaderTest {

    @Autowired(required = false)
    LocalDBLoader localDBLoader;

    @MockBean
    UserRepository userRepositoryMock;

    @MockBean
    BlackListedUserRepository blackListedUserRepositoryMock;

    @BeforeEach
    public void prepareTest() {
        if (localDBLoader == null) {
            localDBLoader = new LocalDBLoader(userRepositoryMock, blackListedUserRepositoryMock);
        } else {
            reset(userRepositoryMock, blackListedUserRepositoryMock);
        }
    }

    @Test
    public void whenRunThenShouldPersistExpectedObjects() {
        localDBLoader.run();

        verify(userRepositoryMock, times(1)).saveAll(anyList());
        verify(blackListedUserRepositoryMock, times(1)).save(any(BlackListedUserEntity.class));
    }

}
