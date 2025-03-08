package com.maia.quadro.mapper;

import com.maia.quadro.dto.user.UserAdminCreateDto;
import com.maia.quadro.dto.user.UserCreateDto;
import com.maia.quadro.dto.user.UserResponseDto;
import com.maia.quadro.model.AppUser;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {
    public static AppUser toUserAdmin(UserAdminCreateDto createDto) {
        return new ModelMapper().map(createDto, AppUser.class);
    }

    public static AppUser toUser(UserCreateDto createDto) {
        return new ModelMapper().map(createDto, AppUser.class);
    }

    public static UserResponseDto toDto(AppUser appUser) {
        return new ModelMapper().map(appUser, UserResponseDto.class);
    }

    public static List<UserResponseDto> toListDto(List<AppUser> appUsers) {
        return appUsers.stream().map(user -> toDto(user)).collect(Collectors.toList());
    }
}
