package com.grc.opengrc.repository;

import com.grc.opengrc.threat.Threat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ThreatRepository extends JpaRepository<Threat, UUID> {

    List<Threat> findByCategory(Threat.ThreatCategory category);

    boolean existsByNameIgnoreCase(String name);
}