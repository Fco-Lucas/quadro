package com.maia.quadro.mapper;

import com.maia.quadro.dto.sector.SectorCreateDto;
import com.maia.quadro.dto.sector.SectorResponseDto;
import com.maia.quadro.model.Sector;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class SectorMapper {
    public static Sector toSector(SectorCreateDto createDto) {
        return new ModelMapper().map(createDto, Sector.class);
    }

    public static SectorResponseDto toDto(Sector sector) {
        return new ModelMapper().map(sector, SectorResponseDto.class);
    }

    public static List<SectorResponseDto> toListDto(List<Sector> sectors) {
        return sectors.stream().map(sector -> toDto(sector)).collect(Collectors.toList());
    }
}
