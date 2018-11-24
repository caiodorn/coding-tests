package com.caiodorn.codingtests.gamesys.user.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface BlackListedUserRepository extends JpaRepository<BlackListedUserEntity, Long> {

    Optional<BlackListedUserEntity> findByDobAndSsn(LocalDate dob, String ssn);

}
