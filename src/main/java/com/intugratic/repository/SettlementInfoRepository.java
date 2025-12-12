package com.intugratic.repository;

import com.intugratic.entities.SettlementInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SettlementInfoRepository extends JpaRepository<SettlementInfo, Long> {
}