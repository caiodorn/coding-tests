package com.caiodorn.codingtests.gamesys.user.util;

import com.caiodorn.codingtests.gamesys.user.persistence.UserEntity;
import com.caiodorn.codingtests.gamesys.user.rest.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface UserObjectMapper {

    @Mappings({
            @Mapping(target = "userId", ignore = true)
    })
    UserEntity mapFromResourceToEntity(User user);

}
