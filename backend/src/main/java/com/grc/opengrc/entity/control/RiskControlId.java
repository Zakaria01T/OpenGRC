package com.grc.opengrc.entity.control;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
public record RiskControlId(
    @Column(name = "risk_id") UUID riskId,
    @Column(name = "control_id") UUID controlId) implements Serializable {
}

