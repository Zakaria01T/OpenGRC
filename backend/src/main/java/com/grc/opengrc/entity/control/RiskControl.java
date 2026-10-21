package com.grc.opengrc.entity.control;

import com.grc.opengrc.entity.risk.Risk;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "risk_controls")
public class RiskControl {
  @EmbeddedId
  private RiskControlId id;

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("riskId")
  @JoinColumn(name = "risk_id")
  private Risk risk;

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("controlId")
  @JoinColumn(name = "control_id")
  private SecurityControl control;

  @Column(columnDefinition = "text")
  private String notes;
  @Column(name = "linked_at", nullable = false, updatable = false, insertable = false)
  private Instant linkedAt;
  @Column(name = "linked_by", nullable = false)
  private UUID linkedBy;
}