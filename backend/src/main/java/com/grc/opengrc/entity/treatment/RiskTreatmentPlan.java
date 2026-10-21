package com.grc.opengrc.entity.treatment;

import com.grc.opengrc.entity.risk.Risk;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "risk_treatment_plans")
@EntityListeners(AuditingEntityListener.class)
public class RiskTreatmentPlan {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "risk_id", nullable = false, updatable = false)
  private Risk risk;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 10)
  private TreatmentStrategy strategy;

  @Column(nullable = false, columnDefinition = "text")
  private String description;
  @Column(columnDefinition = "text")
  private String rationale;
  @Column(name = "owner_id", nullable = false)
  private UUID ownerId;
  @Column(name = "target_date", nullable = false)
  private LocalDate targetDate;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 15)
  private TreatmentStatus status = TreatmentStatus.PLANNED;

  @Column(name = "completed_at")
  private Instant completedAt;

  @CreatedDate
  @Column(name = "created_at", nullable = false, updatable = false)
  private Instant createdAt;
  @LastModifiedDate
  @Column(name = "updated_at", nullable = false)
  private Instant updatedAt;
  @Version
  private Long version;
}
