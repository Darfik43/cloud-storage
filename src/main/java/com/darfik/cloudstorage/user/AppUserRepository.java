package com.darfik.cloudstorage.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    @Transactional
    @Query("SELECT user FROM AppUser user WHERE user.username = ?1")
    Optional<AppUser> findAppUserByUsername(String username);
}
