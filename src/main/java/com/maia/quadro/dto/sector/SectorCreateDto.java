package com.maia.quadro.dto.sector;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class SectorCreateDto {

    @NotBlank
    @Size(max = 255)
    private String name;

    public SectorCreateDto() {
    }

    public SectorCreateDto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "SectorCreateDto{" +
                "name='" + name + '\'' +
                '}';
    }
}
