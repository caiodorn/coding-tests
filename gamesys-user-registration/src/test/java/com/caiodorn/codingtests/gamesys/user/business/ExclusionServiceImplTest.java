package com.caiodorn.codingtests.gamesys.user.business;

import com.caiodorn.codingtests.gamesys.user.persistence.BlackListedUserEntity;
import com.caiodorn.codingtests.gamesys.user.persistence.BlackListedUserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ExclusionServiceImpl.class)
@ActiveProfiles("test")
public class ExclusionServiceImplTest {

    @Autowired
    ExclusionService exclusionService;

    @MockBean
    BlackListedUserRepository blackListedUserRepositoryMock;

    private String inputDob = "2000-01-01";

    private String inputSsn = "000-00-0000";

    @Test
    public void whenNotBlackListedThenShouldReturnTrue() {
        when(blackListedUserRepositoryMock.findByDobAndSsn(LocalDate.parse(inputDob), inputSsn)).thenReturn(Optional.empty());

        assertTrue(exclusionService.validate(inputDob, inputSsn));
    }

    @Test
    public void whenNotBlackListedThenShouldReturnFalse() {
        when(blackListedUserRepositoryMock.findByDobAndSsn(LocalDate.parse(inputDob), inputSsn)).thenReturn(Optional.of(new BlackListedUserEntity()));

        assertFalse(exclusionService.validate(inputDob, inputSsn));
    }

}
