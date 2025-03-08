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
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;

@Tag(name = "Sectors", description = "Contém todas as operações relacionadas aos recursos dos setores do quadro de chamados")
@RestController
@RequestMapping("api/v1/sectors")
public class SectorController {

    private final SectorService sectorService;

    SectorController(SectorService sectorService) {
        this.sectorService = sectorService;
    }

    @PostMapping()
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<SectorResponseDto> create(@RequestBody @Valid SectorCreateDto dto) {
        Sector sector = sectorService.create(SectorMapper.toSector(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(SectorMapper.toDto(sector));
    }

    @GetMapping()
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public ResponseEntity<PageableDto> getAll(Pageable pageable) {
        Page<SectorProjection> sectors = sectorService.getAll(pageable);
        return ResponseEntity.ok(PagebleMapper.toDto(sectors));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<SectorResponseDto> getById(@PathVariable BigInteger id) {
        Sector sector = sectorService.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(SectorMapper.toDto(sector));
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<Void> update(@PathVariable BigInteger id, @RequestBody @Valid SectorUpdateDto dto) {
        sectorService.update(id, dto.getName());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable BigInteger id) {
        sectorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
