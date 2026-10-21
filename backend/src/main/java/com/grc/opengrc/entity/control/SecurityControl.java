package com.grc.opengrc.entity.control;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "security_controls")
@EntityListeners(AuditingEntityListener.class)
public class SecurityControl {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(nullable = false, unique = true, length = 10)
  private String code; // "5.1", "8.24"
  @Column(nullable = false)
  private String title;
  @Column(columnDefinition = "text")
  private String description;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 20)
  private ControlTheme theme;

  @Column(name = "standard_version", nullable = false, length = 30)
  private String standardVersion = "ISO/IEC 27001:2022";

  @Column(nullable = false)
  private boolean applicable = true;
  @Column(name = "applicability_justification", columnDefinition = "text")
  private String applicabilityJustification;

  @Enumerated(EnumType.STRING)
  @Column(name = "implementation_status", nullable = false, length = 25)
  private ImplementationStatus implementationStatus = ImplementationStatus.NOT_IMPLEMENTED;

  @Column(name = "owner_id")
  private UUID ownerId;
  @Column(name = "implementation_notes", columnDefinition = "text")
  private String implementationNotes;

  @CreatedDate
  @Column(name = "created_at", nullable = false, updatable = false)
  private Instant createdAt;
  @LastModifiedDate
  @Column(name = "updated_at", nullable = false)
  private Instant updatedAt;
  @Version
  private Long version;
}
