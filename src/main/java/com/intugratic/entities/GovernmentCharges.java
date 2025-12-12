package com.intugratic.entities;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "government_charges")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GovernmentCharges {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // (19) Tax collected at source (TCS)
    @Column(name = "tax_collected_at_source", precision = 15, scale = 2)
    private BigDecimal taxCollectedAtSource;

    // (22) TCS IGST amount
    @Column(name = "tcs_igst_amount", precision = 15, scale = 2)
    private BigDecimal tcsIgstAmount;

    // (23) TDS 194O amount
    @Column(name = "tds_194o_amount", precision = 15, scale = 2)
    private BigDecimal tds194oAmount;

    // (24) GST paid by Zomato on behalf of restaurant (section 9(5))
    @Column(name = "gst_paid_under_section_9_5", precision = 15, scale = 2)
    private BigDecimal gstPaidUnder95;

    // (25) GST to be paid by Restaurant partner to Govt.
    @Column(name = "gst_payable_by_restaurant", precision = 15, scale = 2)
    private BigDecimal gstPayableByRestaurant;

    // (Total Government Charges)
    @Column(name = "total_government_charges", precision = 15, scale = 2)
    private BigDecimal totalGovernmentCharges;
}

