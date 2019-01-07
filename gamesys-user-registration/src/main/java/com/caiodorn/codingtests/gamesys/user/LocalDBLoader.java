package com.caiodorn.codingtests.gamesys.user;

import com.caiodorn.codingtests.gamesys.user.persistence.BlackListedUserEntity;
import com.caiodorn.codingtests.gamesys.user.persistence.BlackListedUserRepository;
import com.caiodorn.codingtests.gamesys.user.persistence.UserEntity;
import com.caiodorn.codingtests.gamesys.user.persistence.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;

/**
 * This class' solely purpose is to facilitate local development by pushing some data into the in-memory DB. This is not
 * intended to be used by unit/integration tests.
 */
@Profile("local")
@Component
@AllArgsConstructor
public class LocalDBLoader implements CommandLineRunner {

    private UserRepository userRepository;
    private BlackListedUserRepository blackListedUserRepository;

    @Override
    public void run(String... args) {
        UserEntity bannedUser = new UserEntity(null, "bannedUser", "abC1", "000-00-0000", LocalDate.now());
        UserEntity harmlessUser = new UserEntity(null, "harmlessUser", "abC2", "111-11-1111", LocalDate.now());
        BlackListedUserEntity blackList = new BlackListedUserEntity(null, bannedUser.getSsn(), bannedUser.getDob(), "used 'infinite money' cheat", null);

        userRepository.saveAll(Arrays.asList(bannedUser, harmlessUser));
        blackListedUserRepository.save(blackList);
    }

}
