package com.grc.opengrc.repository;

import com.grc.opengrc.control.SecurityControl;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SecurityControlRepository extends JpaRepository<SecurityControl, UUID> {
}