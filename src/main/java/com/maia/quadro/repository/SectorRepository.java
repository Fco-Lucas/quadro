package com.maia.quadro.repository;

import com.maia.quadro.enums.SectorStatus;
import com.maia.quadro.model.Sector;
import com.maia.quadro.repository.projection.SectorProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SectorRepository extends JpaRepository<Sector, Long> {
    Optional<Sector> findByNameAndStatus(String name, SectorStatus status);

    @Query("SELECT s FROM Sector s WHERE s.status = :status")
    Page<SectorProjection> findAllPageable(@Param("status") SectorStatus status, Pageable pageable);
}
