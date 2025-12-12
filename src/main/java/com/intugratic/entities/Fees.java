package com.intugratic.entities;



import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "fees")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Fees {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Actual order distance in km
    @Column(name = "order_distance_km", precision = 10, scale = 2)
    private BigDecimal orderDistanceKm;

    // Long distance enablement fee
    @Column(name = "long_distance_fee", precision = 15, scale = 2)
    private BigDecimal longDistanceFee;

    // Discount on long distance enablement fee
    @Column(name = "long_distance_fee_discount", precision = 15, scale = 2)
    private BigDecimal longDistanceFeeDiscount;

    // Discount on service fee due to 30% capping
    @Column(name = "service_fee_capping_discount", precision = 15, scale = 2)
    private BigDecimal serviceFeeCappingDiscount;

    // Payment mechanism fee
    @Column(name = "payment_mechanism_fee", precision = 15, scale = 2)
    private BigDecimal paymentMechanismFee;

    // Service fee + payment mechanism fee combined
    @Column(name = "total_service_payment_fee", precision = 15, scale = 2)
    private BigDecimal totalServicePaymentFee;

    // Taxes on service fee & payment mechanism fee (C * 18%)
    @Column(name = "service_tax_amount", precision = 15, scale = 2)
    private BigDecimal serviceTaxAmount;

    // Applicable amount for TCS
    @Column(name = "tcs_applicable_amount", precision = 15, scale = 2)
    private BigDecimal tcsApplicableAmount;

    // Applicable amount for 9(5)
    @Column(name = "section_9_5_applicable_amount", precision = 15, scale = 2)
    private BigDecimal section95ApplicableAmount;
}

