package com.grc.opengrc.repository;

import com.grc.opengrc.entity.Asset;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AssetRepository extends JpaRepository<Asset, UUID> {

    List<Asset> findByNameContainingIgnoreCase(String name);

    List<Asset> findByCriticality(Asset.Criticality criticality);
}