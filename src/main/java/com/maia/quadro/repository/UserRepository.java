package com.maia.quadro.repository;

import com.maia.quadro.enums.UserRole;
import com.maia.quadro.enums.UserStatus;
import com.maia.quadro.model.AppUser;
import com.maia.quadro.repository.projection.UserProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<AppUser, UUID> {
    Optional<AppUser> findByCpfAndStatus(String cpf, UserStatus status);

    Optional<AppUser> findByRoleAndStatus(UserRole role, UserStatus status);

    List<AppUser> findBySectorIdAndStatus(Long sectorId, UserStatus status);

    @Query("SELECT u FROM AppUser u WHERE u.status = :status")
    Page<UserProjection> findAllPageable(@Param("status") UserStatus status, Pageable pageable);

    @Query("SELECT U.role FROM AppUser U WHERE U.cpf = :cpf AND U.status = :status")
    UserRole findRoleByCpf(@Param("cpf") String cpf, @Param("status") UserStatus status);
}
