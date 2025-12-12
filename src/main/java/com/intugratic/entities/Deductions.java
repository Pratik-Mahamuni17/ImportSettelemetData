package com.intugratic.entities;


import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "deductions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Deductions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Customer compensation / recoupment
    @Column(name = "customer_compensation", precision = 15, scale = 2)
    private BigDecimal customerCompensation;

    // Delivery charges recovery
    @Column(name = "delivery_charges_recovery", precision = 15, scale = 2)
    private BigDecimal deliveryChargesRecovery;

    // Amount received in cash (only for self-delivery orders)
    @Column(name = "cash_received", precision = 15, scale = 2)
    private BigDecimal cashReceived;

    // Credit note / Debit note adjustment
    @Column(name = "credit_debit_adjustment", precision = 15, scale = 2)
    private BigDecimal creditDebitAdjustment;

    // Promo recovery adjustment
    @Column(name = "promo_recovery", precision = 15, scale = 2)
    private BigDecimal promoRecovery;

    // Extra inventory ads (order-level deduction)
    @Column(name = "extra_inventory_ads", precision = 15, scale = 2)
    private BigDecimal extraInventoryAds;

    // Brand loyalty points redemption
    @Column(name = "brand_loyalty_redemption", precision = 15, scale = 2)
    private BigDecimal brandLoyaltyRedemption;

    // Express order fee
    @Column(name = "express_order_fee", precision = 15, scale = 2)
    private BigDecimal expressOrderFee;

    // Other order-level deductions (sum of many small adjustments)
    @Column(name = "other_order_level_deductions", precision = 15, scale = 2)
    private BigDecimal otherOrderLevelDeductions;

    // Net Deductions [(C) + (D) + (E)]
    @Column(name = "net_deductions", precision = 15, scale = 2)
    private BigDecimal netDeductions;
}

