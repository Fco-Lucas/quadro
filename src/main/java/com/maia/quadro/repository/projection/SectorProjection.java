package com.maia.quadro.repository.projection;

import com.maia.quadro.enums.SectorStatus;

import java.math.BigInteger;

public interface SectorProjection {
    BigInteger getId();
    String getName();
    SectorStatus getStatus();
}
