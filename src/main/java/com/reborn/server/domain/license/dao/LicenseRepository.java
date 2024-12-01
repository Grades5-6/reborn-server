package com.reborn.server.domain.license.dao;

import com.reborn.server.domain.license.domain.License;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface LicenseRepository extends JpaRepository<License, Long> {
    @Query("SELECT l.jmcd FROM License l")
    Set<String> findAllJmcds();
}
