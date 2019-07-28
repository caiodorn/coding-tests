package com.caiodorn.codingtests.gamesys.user.business;

import com.caiodorn.codingtests.gamesys.user.persistence.UserRepository;
import com.caiodorn.codingtests.gamesys.user.rest.User;
import com.caiodorn.codingtests.gamesys.user.util.UserObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final ExclusionService exclusionService;
    private final UserObjectMapper objectMapper;
    private final UserRepository userRepository;

    @Override
    public void register(User incomingUser) {
        validateNewUser(incomingUser);
        userRepository.save(objectMapper.mapFromResourceToEntity(incomingUser));
    }

    @Override
    public void validateNewUser(User incomingUser) {
        validateConflict(incomingUser.getUserName());
        validateBanned(incomingUser.getDob(), incomingUser.getSsn());
    }

    private void validateConflict(String incomingUserName) {
        userRepository.findByUserName(incomingUserName).ifPresent(user -> {
            throw new UserNameAlreadyInUseException(user.getUserName());
        });

    }

    private void validateBanned(String dob, String ssn) {
        if (!exclusionService.validate(dob, ssn)) {
            throw new BlackListedUserException(dob, ssn);
        }
    }

}
