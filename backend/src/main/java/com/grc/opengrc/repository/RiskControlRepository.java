package com.grc.opengrc.repository;

import com.grc.opengrc.control.RiskControl;
import com.grc.opengrc.control.RiskControlId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RiskControlRepository extends JpaRepository<RiskControl, RiskControlId> {
}