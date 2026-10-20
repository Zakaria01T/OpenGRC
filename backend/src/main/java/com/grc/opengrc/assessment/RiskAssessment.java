package com.grc.opengrc.assessment;

import com.grc.opengrc.risk.Risk;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "risk_assessments")
public class RiskAssessment {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "risk_id", nullable = false, updatable = false)
  private Risk risk;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 10, updatable = false)
  private AssessmentType type;

  @Column(nullable = false, updatable = false)
  private short likelihood;
  @Column(nullable = false, updatable = false)
  private short impact;
  @Column(nullable = false, updatable = false)
  private short score; // set by the service

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 10, updatable = false)
  private RiskLevel level; // set by the service

  @Column(columnDefinition = "text", updatable = false)
  private String justification;
  @Column(name = "assessed_by", nullable = false, updatable = false)
  private UUID assessedBy;

  @Column(name = "assessed_at", nullable = false, updatable = false, insertable = false)
  private Instant assessedAt; // database default now()

  @Column(name = "is_current", nullable = false)
  private boolean current = true;
}
