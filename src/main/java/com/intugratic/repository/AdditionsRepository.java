package com.intugratic.repository;

import com.intugratic.entities.Additions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdditionsRepository extends JpaRepository<Additions, Long> {
}