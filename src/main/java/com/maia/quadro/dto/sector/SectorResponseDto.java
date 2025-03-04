package com.maia.quadro.dto.sector;

import com.maia.quadro.enums.SectorStatus;

import java.math.BigInteger;

public class SectorResponseDto {

    private BigInteger id;
    private String name;
    private SectorStatus status;

    public SectorResponseDto() {
    }

    public SectorResponseDto(BigInteger id, String name, SectorStatus status) {
        this.id = id;
        this.name = name;
        this.status = status;
    }

    public BigInteger getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public SectorStatus getStatus() {
        return status;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(SectorStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "SectorResponseDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", status=" + status +
                '}';
    }
}
