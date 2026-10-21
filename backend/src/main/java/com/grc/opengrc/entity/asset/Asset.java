package com.grc.opengrc.entity.asset;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import com.grc.opengrc.entity.vulnerability.Vulnerability;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(
        name = "assets",
        indexes = @Index(name = "idx_assets_name", columnList = "name")
)
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank
    @Size(max = 150)
    @Column(nullable = false, length = 150)
    private String name;

    @Size(max = 1000)
    @Column(length = 1000)
    private String description;

    @NotBlank
    @Size(max = 50)
    @Column(nullable = false, length = 50)
    private String assetType;

    @Size(max = 150)
    @Column(length = 150)
    private String owner;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Criticality criticality = Criticality.MEDIUM;

    @Column(nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    @Column(nullable = false)
    private Instant updatedAt = Instant.now();

    @OneToMany(mappedBy = "asset")
    private List<Vulnerability> vulnerabilities = new ArrayList<>();

    public enum Criticality {
        LOW, MEDIUM, HIGH, CRITICAL
    }

    protected Asset() {
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = Instant.now();
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAssetType() {
        return assetType;
    }

    public void setAssetType(String assetType) {
        this.assetType = assetType;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Criticality getCriticality() {
        return criticality;
    }

    public void setCriticality(Criticality criticality) {
        this.criticality = criticality;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public List<Vulnerability> getVulnerabilities() {
        return vulnerabilities;
    }
}