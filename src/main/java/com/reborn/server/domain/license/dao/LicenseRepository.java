package com.reborn.server.domain.license.dao;

import com.reborn.server.domain.license.domain.License;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LicenseRepository extends JpaRepository<License, Long> {
}
