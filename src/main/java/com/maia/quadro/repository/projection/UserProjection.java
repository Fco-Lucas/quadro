package com.maia.quadro.repository.projection;

import com.maia.quadro.enums.UserRole;
import com.maia.quadro.enums.UserStatus;

import java.util.UUID;

public interface UserProjection {
    UUID getId();
    String getName();
    String getCpf();
    UserRole getRole();
    Long getSectorId();
    UserStatus getStatus();
}
