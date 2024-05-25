package com.darfik.cloudstorage.mapper;

import com.darfik.cloudstorage.model.AppUser;
import com.darfik.cloudstorage.dto.AppUserRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AppUserMapper {
    AppUser dtoToModel(AppUserRequest appUserRequest);
    AppUserRequest modelToDto(AppUser appUser);
}
