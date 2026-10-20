package com.grc.opengrc.risk;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Entity 
@Table(name = "risks")
@EntityListeners(AuditingEntityListener.class)
public class Risk {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(nullable = false, unique = true, length = 20)
  private String reference; // RSK-0001, from risk_reference_seq

  @Column(nullable = false, length = 200)
  private String title;
  @Column(nullable = false, columnDefinition = "text")
  private String description;
  @Column(columnDefinition = "text")
  private String threat;
  @Column(columnDefinition = "text")
  private String vulnerability;

  @Column(name = "asset_id", nullable = false)
  private UUID assetId;
  @Column(name = "owner_id", nullable = false)
  private UUID ownerId;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 30)
  private RiskStatus status = RiskStatus.DRAFT;

  @Column(name = "next_review_date")
  private LocalDate nextReviewDate;

  @CreatedDate
  @Column(name = "created_at", nullable = false, updatable = false)
  private Instant createdAt;
  @LastModifiedDate
  @Column(name = "updated_at", nullable = false)
  private Instant updatedAt;
  @Version
  private Long version;
}
