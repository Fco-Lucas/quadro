package com.maia.quadro.dto.user;

import com.maia.quadro.enums.UserRole;
import com.maia.quadro.enums.UserStatus;

import java.util.UUID;

public class UserResponseDto {

    private UUID id;
    private String name;
    private String cpf;
    private UserRole role;
    private Long sectorId;
    private UserStatus status;

    public UserResponseDto() {}

    public UserResponseDto(UUID id, String name, String cpf, UserRole role, Long sectorId, UserStatus status) {
        this.id = id;
        this.name = name;
        this.cpf = cpf;
        this.role = role;
        this.sectorId = sectorId;
        this.status = status;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCpf() {
        return cpf;
    }

    public UserRole getRole() {
        return role;
    }

    public Long getSectorId() {
        return sectorId;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public void setSectorId(Long sectorId) {
        this.sectorId = sectorId;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "UserResponseDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cpf='" + cpf + '\'' +
                ", role='" + role + '\'' +
                ", sectorId=" + sectorId +
                ", status='" + status + '\'' +
                '}';
    }
}
