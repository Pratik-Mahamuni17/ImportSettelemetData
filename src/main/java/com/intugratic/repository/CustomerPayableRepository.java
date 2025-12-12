package com.intugratic.repository;

import com.intugratic.entities.CustomerPayable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerPayableRepository extends JpaRepository<CustomerPayable, Long> {
}
