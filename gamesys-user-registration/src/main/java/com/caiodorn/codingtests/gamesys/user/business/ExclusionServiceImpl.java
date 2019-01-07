package com.caiodorn.codingtests.gamesys.user.business;

import com.caiodorn.codingtests.gamesys.user.persistence.BlackListedUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class ExclusionServiceImpl implements ExclusionService {

    private final BlackListedUserRepository blackListedUserRepository;

    @Override
    public boolean validate(String dob, String ssn) {
        return !this.blackListedUserRepository.findByDobAndSsn(LocalDate.parse(dob), ssn).isPresent();
    }
}