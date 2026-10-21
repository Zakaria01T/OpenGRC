package com.grc.opengrc.repository;

import com.grc.opengrc.entity.assessment.RiskAssessment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RiskAssessmentRepository extends JpaRepository<RiskAssessment, UUID> {
}