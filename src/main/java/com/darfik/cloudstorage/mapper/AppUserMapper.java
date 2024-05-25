package com.darfik.cloudstorage.mapper;

import com.darfik.cloudstorage.dto.RegistrationDto;
import com.darfik.cloudstorage.model.AppUser;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AppUserMapper {
    AppUser dtoToModel(RegistrationDto registrationDto);
    RegistrationDto modelToDto(AppUser appUser);
}
