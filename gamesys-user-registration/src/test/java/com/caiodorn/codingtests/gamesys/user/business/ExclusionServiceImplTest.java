package com.caiodorn.codingtests.gamesys.user.business;

import com.caiodorn.codingtests.gamesys.user.persistence.BlackListedUserEntity;
import com.caiodorn.codingtests.gamesys.user.persistence.BlackListedUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ExclusionServiceImplTest {

    private ExclusionService exclusionService;

    @Mock
    private BlackListedUserRepository blackListedUserRepositoryMock;

    private String inputDob = "2000-01-01";
    private String inputSsn = "000-00-0000";

    @BeforeEach
    public void setup() {
        this.exclusionService = new ExclusionServiceImpl(blackListedUserRepositoryMock);
    }

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
