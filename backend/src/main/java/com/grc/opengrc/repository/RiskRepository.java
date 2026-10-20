package com.grc.opengrc.repository;

import com.grc.opengrc.risk.Risk;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RiskRepository extends JpaRepository<Risk, UUID> {
}