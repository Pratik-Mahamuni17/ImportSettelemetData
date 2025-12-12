package com.intugratic.entities;



import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "commission_values")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommissionValues {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Commissionable value of Subtotal (items total minus restaurant discounts)
    @Column(name = "commissionable_subtotal", precision = 15, scale = 2)
    private BigDecimal commissionableSubtotal;

    // Commissionable value of Packaging charge
    @Column(name = "commissionable_packaging", precision = 15, scale = 2)
    private BigDecimal commissionablePackaging;

    // Commissionable value of Total GST collected from customers
    @Column(name = "commissionable_gst", precision = 15, scale = 2)
    private BigDecimal commissionableGst;

    // Total commissionable value = subtotal + packaging + gst
    @Column(name = "total_commissionable_value", precision = 15, scale = 2)
    private BigDecimal totalCommissionableValue;

    // Base service fee % (just a percentage value)
    @Column(name = "base_service_fee_percentage", precision = 5, scale = 2)
    private BigDecimal baseServiceFeePercentage;

    // Base service fee = (Percentage * Commissionable value)
    @Column(name = "base_service_fee_amount", precision = 15, scale = 2)
    private BigDecimal baseServiceFeeAmount;
}
