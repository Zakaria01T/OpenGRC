package com.grc.opengrc.threat;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import com.grc.opengrc.vulnerability.Vulnerability;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "threats")
public class Threat {

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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private ThreatCategory category;

    @ManyToMany(mappedBy = "threats")
    private Set<Vulnerability> vulnerabilities = new HashSet<>();

    public enum ThreatCategory {
        MALWARE,
        PHISHING,
        UNAUTHORIZED_ACCESS,
        DATA_BREACH,
        DENIAL_OF_SERVICE,
        INSIDER_THREAT,
        NATURAL_DISASTER,
        OTHER
    }

    protected Threat() {
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

    public ThreatCategory getCategory() {
        return category;
    }

    public void setCategory(ThreatCategory category) {
        this.category = category;
    }

    public Set<Vulnerability> getVulnerabilities() {
        return vulnerabilities;
    }
}