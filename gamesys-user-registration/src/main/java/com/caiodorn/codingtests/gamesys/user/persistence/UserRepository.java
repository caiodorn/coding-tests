package com.caiodorn.codingtests.gamesys.user.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByDobAndSsn(LocalDate dob, String ssn);
    Optional<UserEntity> findByUserName(String userName);

}
