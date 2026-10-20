package com.grc.opengrc.repository;

import com.grc.opengrc.treatment.RiskTreatmentPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RiskTreatmentPlanRepository extends JpaRepository<RiskTreatmentPlan, UUID> {
}