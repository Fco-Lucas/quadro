package com.maia.quadro.repository;

import com.maia.quadro.enums.UserStatus;
import com.maia.quadro.model.User;
import com.maia.quadro.repository.projection.UserProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByCpfAndStatus(String cpf, UserStatus status);

    @Query("SELECT u FROM User u WHERE u.status = :status")
    Page<UserProjection> findAllPageable(@Param("status") UserStatus status, Pageable pageable);
}
