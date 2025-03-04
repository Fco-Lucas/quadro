package com.maia.quadro.controller;

import com.maia.quadro.dto.PageableDto;
import com.maia.quadro.dto.sector.SectorCreateDto;
import com.maia.quadro.dto.sector.SectorResponseDto;
import com.maia.quadro.dto.sector.SectorUpdateDto;
import com.maia.quadro.mapper.PagebleMapper;
import com.maia.quadro.mapper.SectorMapper;
import com.maia.quadro.model.Sector;
import com.maia.quadro.repository.projection.SectorProjection;
import com.maia.quadro.service.SectorService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

@RestController
@RequestMapping("api/v1/sectors")
public class SectorController {

    private final SectorService sectorService;

    SectorController(SectorService sectorService) {
        this.sectorService = sectorService;
    }

    @PostMapping()
    public ResponseEntity<SectorResponseDto> create(@RequestBody @Valid SectorCreateDto dto) {
        Sector sector = sectorService.create(SectorMapper.toSector(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(SectorMapper.toDto(sector));
    }

    @GetMapping()
    public ResponseEntity<PageableDto> getAll(Pageable pageable) {
        Page<SectorProjection> sectors = sectorService.getAll(pageable);
        return ResponseEntity.ok(PagebleMapper.toDto(sectors));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SectorResponseDto> getById(@PathVariable BigInteger id) {
        Sector sector = sectorService.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(SectorMapper.toDto(sector));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable BigInteger id, @RequestBody @Valid SectorUpdateDto dto) {
        sectorService.update(id, dto.getName());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable BigInteger id) {
        sectorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
