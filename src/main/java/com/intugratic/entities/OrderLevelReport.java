package com.intugratic.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "order_level_report")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderLevelReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Basic order identifiers
    @Column(name = "order_id", length = 100)
    private String orderId;

    @Column(name = "order_date")
    private LocalDate orderDate;

    @Column(name = "week_no", length = 30)
    private String weekNo;

    @Column(name = "restaurant_name", length = 200)
    private String restaurantName;

    @Column(name = "restaurant_id", length = 100)
    private String restaurantId;

    @Column(name = "discount_construct", length = 200)
    private String discountConstruct;

    @Column(name = "mode_of_payment", length = 100)
    private String modeOfPayment;

    @Column(name = "order_status", length = 80)
    private String orderStatus; // Delivered / Cancelled / Rejected

    @Column(name = "cancellation_policy", length = 200)
    private String cancellationPolicy;

    @Column(name = "cancellation_reason", length = 500)
    private String cancellationReason;

    @Column(name = "cancelled_state", length = 120)
    private String cancelledState;

    @Column(name = "order_type", length = 80)
    private String orderType;

    @Column(name = "delivery_state_code", length = 50)
    private String deliveryStateCode;

    /*
      Grouped relationships to keep entity modular.
      Each related entity will be created next (CustomerPayable, CommissionValues, Fees,
      GovernmentCharges, Deductions, Additions, SettlementInfo).
     */

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_payable_id")
    private CustomerPayable customerPayable;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "commission_values_id")
    private CommissionValues commissionValues;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "fees_id")
    private Fees fees;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "government_charges_id")
    private GovernmentCharges governmentCharges;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "deductions_id")
    private Deductions deductions;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "additions_id")
    private Additions additions;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "settlement_info_id")
    private SettlementInfo settlementInfo;

    // convenience: customer identifier if needed for queries
    @Column(name = "customer_id", length = 100)
    private String customerId;
}
