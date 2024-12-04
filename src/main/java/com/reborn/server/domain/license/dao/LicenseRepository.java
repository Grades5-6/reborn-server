package com.reborn.server.domain.license.dao;

import com.reborn.server.domain.license.domain.License;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.Set;

public interface LicenseRepository extends JpaRepository<License, Long> {
    @Query("SELECT l.jmfldnm FROM License l")
    Set<String> findAllJmfldnm();
    Optional<License> findByJmfldnm(String jmfldnm);
}
