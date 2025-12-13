package com.intugratic.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "order_level_report")
public class OrderLevelReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String orderId;
    private LocalDate orderDate;

    @Column(columnDefinition = "LONGTEXT")
    private String orderLevelJson;
}

