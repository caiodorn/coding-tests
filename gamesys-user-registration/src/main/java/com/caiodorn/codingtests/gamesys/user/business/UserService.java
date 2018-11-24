package com.caiodorn.codingtests.gamesys.user.business;

import com.caiodorn.codingtests.gamesys.user.rest.User;

public interface UserService {

    void register(User user);
    void validateNewUser(User user);

}
