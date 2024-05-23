package com.darfik.cloudstorage.mapper;

import com.darfik.cloudstorage.user.AppUser;
import com.darfik.cloudstorage.user.AppUserRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AppUserMapper {
    AppUser dtoToModel(AppUserRequest appUserRequest);
    AppUserRequest modelToDto(AppUser appUser);
}
