package com.darfik.cloudstorage.repository;

import com.darfik.cloudstorage.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    @Transactional
    boolean existsByEmail(String email);

    @Transactional
    @Query("SELECT user FROM AppUser user WHERE user.email = ?1")
    Optional<AppUser> findByEmail(String email);

}
