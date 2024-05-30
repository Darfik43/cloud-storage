package com.darfik.cloudstorage.web.mapper;

import com.darfik.cloudstorage.domain.user.RegistrationDto;
import com.darfik.cloudstorage.domain.user.AppUser;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AppUserMapper extends Mappable<AppUser, RegistrationDto> {
}
