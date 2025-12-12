package com.intugratic.entities;


import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "settlement_info")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SettlementInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // (Order Level Payout) A - F + G
    @Column(name = "order_level_payout", precision = 15, scale = 2)
    private BigDecimal orderLevelPayout;

    // Settlement status
    @Column(name = "settlement_status", length = 100)
    private String settlementStatus;

    // Settlement date
    @Column(name = "settlement_date")
    private LocalDate settlementDate;

    // Bank UTR
    @Column(name = "bank_utr", length = 150)
    private String bankUtr;

    // Unsettled amount
    @Column(name = "unsettled_amount", precision = 15, scale = 2)
    private BigDecimal unsettledAmount;
}

