package com.intugratic.repository;

import com.intugratic.entity.OrderLevelReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderLevelReportRepository
        extends JpaRepository<OrderLevelReportEntity, Long> {
}

