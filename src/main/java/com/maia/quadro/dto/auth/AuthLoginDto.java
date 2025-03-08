package com.maia.quadro.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AuthLoginDto {

    @NotBlank
    @Size(min = 11, max = 11)
    private String cpf;
    @NotBlank
    @Size(max = 255)
    private String password;

    public AuthLoginDto() {
    }

    public AuthLoginDto(String cpf, String password) {
        this.cpf = cpf;
        this.password = password;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "AuthLoginDto{" +
                "cpf='" + cpf + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
