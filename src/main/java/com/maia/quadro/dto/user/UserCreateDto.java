package com.maia.quadro.dto.user;

import com.maia.quadro.enums.UserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UserCreateDto {

    @NotBlank
    @Size(max = 255)
    private String name;
    @NotBlank
    @Size(min = 11, max = 11)
    private String cpf;
    @NotBlank
    @Size(min = 6)
    private String password;
    @NotNull
    private UserRole userRole;
    @NotNull
    private Long sectorId;

    public UserCreateDto() {
    }

    public UserCreateDto(String name, String cpf, String password, UserRole userRole, Long sectorId) {
        this.name = name;
        this.cpf = cpf;
        this.password = password;
        this.userRole = userRole;
        this.sectorId = sectorId;
    }

    public String getName() {
        return name;
    }

    public String getCpf() {
        return cpf;
    }

    public String getPassword() {
        return password;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public Long getSectorId() {
        return sectorId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public void setSectorId(Long sectorId) {
        this.sectorId = sectorId;
    }

    @Override
    public String toString() {
        return "UserCreateDto{" +
                "name='" + name + '\'' +
                ", cpf='" + cpf + '\'' +
                ", password='" + password + '\'' +
                ", userRole='" + userRole + '\'' +
                ", sectorId=" + sectorId +
                '}';
    }
}
