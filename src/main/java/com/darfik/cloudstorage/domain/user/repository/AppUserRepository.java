package com.darfik.cloudstorage.domain.user.repository;

import com.darfik.cloudstorage.domain.user.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    @Transactional
    boolean existsByEmail(String email);

    @Transactional
    Optional<AppUser> findByEmail(String email);


}
