package com.maia.quadro.dto.user;

import com.maia.quadro.enums.UserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UserUpdateInfoDto {

    @NotBlank
    @Size(max = 255)
    private String name;
    @NotNull
    private UserRole role;
    @NotNull
    private Long sectorId;

    UserUpdateInfoDto() {}

    public UserUpdateInfoDto(String name, UserRole role, Long sectorId) {
        this.name = name;
        this.role = role;
        this.sectorId = sectorId;
    }

    public String getName() {
        return name;
    }

    public UserRole getRole() {
        return role;
    }

    public Long getSectorId() {
        return sectorId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public void setSectorId(Long sectorId) {
        this.sectorId = sectorId;
    }

    @Override
    public String toString() {
        return "UserUpdateInfoDto{" +
                "name='" + name + '\'' +
                ", role=" + role +
                ", sectorId=" + sectorId +
                '}';
    }
}
