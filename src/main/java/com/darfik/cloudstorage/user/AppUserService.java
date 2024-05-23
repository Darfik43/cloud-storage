package com.darfik.cloudstorage.user;

import com.darfik.cloudstorage.mapper.AppUserMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AppUserService {
    private final AppUserRepository appUserRepository;
    private final AppUserMapper mapper;

    public List<AppUser> getUsers() {
        return appUserRepository.findAll();
    }

    public void addNewUser(AppUserRequest appUserRequest) {
        appUserRepository.findAppUserByUsername(appUserRequest.getUsername())
                .ifPresentOrElse((user) ->
                        {
                            throw new UserAlreadyExistsException("User already exists");
                        },
                        () -> appUserRepository.save(mapper.dtoToModel(appUserRequest))
                );
    }
}
