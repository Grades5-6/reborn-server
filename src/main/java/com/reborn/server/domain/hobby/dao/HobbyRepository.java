package com.reborn.server.domain.hobby.dao;

import com.reborn.server.domain.hobby.domain.Hobby;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HobbyRepository extends JpaRepository<Hobby, Long> {
    Optional<Hobby> findByClassTitle(String classTitle);
}