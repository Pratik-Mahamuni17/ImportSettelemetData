package com.intugratic.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "order_level_report")
public class OrderLevelReportEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ================= BASIC ORDER DETAILS =================
    private Integer sno;

    @Column(name = "order_id")
    private String orderId;

    @Column(name = "order_date")
    private LocalDateTime orderDate;

    @Column(name = "week_no")
    private String weekNo;

    @Column(name = "restaurant_name")
    private String restaurantName;

    @Column(name = "restaurant_id")
    private String restaurantId;

    @Column(name = "discount_construct")
    private String discountConstruct;

    @Column(name = "mode_of_payment")
    private String modeOfPayment;

    @Column(name = "order_status")
    private String orderStatus;

    @Column(name = "cancellation_policy")
    private String cancellationPolicy;

    @Column(name = "cancellation_reason")
    private String cancellationReason;

    @Column(name = "cancelled_rejected_state")
    private String cancelledRejectedState;

    @Column(name = "order_type")
    private String orderType;

    @Column(name = "delivery_state_code")
    private String deliveryStateCode;

    // ================= CUSTOMER PAYABLE =================
    private BigDecimal subtotal;

    @Column(name = "packaging_charge")
    private BigDecimal packagingCharge;

    @Column(name = "delivery_charge_self_logistics")
    private BigDecimal deliveryChargeSelfLogistics;

    @Column(name = "restaurant_discount_promo")
    private BigDecimal restaurantDiscountPromo;

    @Column(name = "restaurant_discount_other")
    private BigDecimal restaurantDiscountOther;

    @Column(name = "brand_pack_subscription_fee")
    private BigDecimal brandPackSubscriptionFee;

    @Column(name = "delivery_charge_discount")
    private BigDecimal deliveryChargeDiscount;

    @Column(name = "total_gst_collected")
    private BigDecimal totalGstCollected;

    @Column(name = "net_order_value")
    private BigDecimal netOrderValue;

    // ================= COMMISSIONABLE VALUE =================
    @Column(name = "commissionable_subtotal")
    private BigDecimal commissionableSubtotal;

    @Column(name = "commissionable_packaging_charge")
    private BigDecimal commissionablePackagingCharge;

    @Column(name = "commissionable_gst")
    private BigDecimal commissionableGst;

    @Column(name = "total_commissionable_value")
    private BigDecimal totalCommissionableValue;

    // ================= SERVICE FEE & DISTANCE =================
    @Column(name = "base_service_fee_percent")
    private BigDecimal baseServiceFeePercent;

    @Column(name = "base_service_fee")
    private BigDecimal baseServiceFee;

    @Column(name = "actual_order_distance_km")
    private BigDecimal actualOrderDistanceKm;

    @Column(name = "long_distance_enablement_fee")
    private BigDecimal longDistanceEnablementFee;

    @Column(name = "discount_on_long_distance_fee")
    private BigDecimal discountOnLongDistanceFee;

    @Column(name = "discount_on_service_fee_capping")
    private BigDecimal discountOnServiceFeeCapping;

    @Column(name = "payment_mechanism_fee")
    private BigDecimal paymentMechanismFee;

    // ================= TAXES & GOVERNMENT =================
    @Column(name = "service_fee_and_payment_mechanism_fee")
    private BigDecimal serviceFeeAndPaymentMechanismFee;

    @Column(name = "taxes_on_service_fee")
    private BigDecimal taxesOnServiceFee;

    @Column(name = "applicable_amount_for_tcs")
    private BigDecimal applicableAmountForTcs;

    @Column(name = "applicable_amount_for_section_9_5")
    private BigDecimal applicableAmountForSection95;

    @Column(name = "tax_collected_at_source")
    private BigDecimal taxCollectedAtSource;

    @Column(name = "tcs_igst_amount")
    private BigDecimal tcsIgstAmount;

    @Column(name = "tds_194o_amount")
    private BigDecimal tds194OAmount;

    @Column(name = "gst_paid_by_zomato")
    private BigDecimal gstPaidByZomato;

    @Column(name = "gst_to_be_paid_by_restaurant")
    private BigDecimal gstToBePaidByRestaurant;

    // ================= OTHER DEDUCTIONS =================
    @Column(name = "government_charges")
    private BigDecimal governmentCharges;

    @Column(name = "customer_compensation")
    private BigDecimal customerCompensation;

    @Column(name = "delivery_charges_recovery")
    private BigDecimal deliveryChargesRecovery;

    @Column(name = "amount_received_in_cash")
    private BigDecimal amountReceivedInCash;

    @Column(name = "credit_debit_note_adjustment")
    private BigDecimal creditDebitNoteAdjustment;

    @Column(name = "promo_recovery_adjustment")
    private BigDecimal promoRecoveryAdjustment;

    @Column(name = "extra_inventory_ads")
    private BigDecimal extraInventoryAds;

    @Column(name = "brand_loyalty_points_redemption")
    private BigDecimal brandLoyaltyPointsRedemption;

    @Column(name = "express_order_fee")
    private BigDecimal expressOrderFee;

    // ================= FINAL SETTLEMENT =================
    @Column(name = "other_order_level_deductions")
    private BigDecimal otherOrderLevelDeductions;

    @Column(name = "net_deductions")
    private BigDecimal netDeductions;

    @Column(name = "net_additions")
    private BigDecimal netAdditions;

    @Column(name = "order_level_payout")
    private BigDecimal orderLevelPayout;

    @Column(name = "settlement_status")
    private String settlementStatus;

    @Column(name = "settlement_date")
    private LocalDate settlementDate;

    @Column(name = "bank_utr")
    private String bankUtr;

    @Column(name = "unsettled_amount")
    private BigDecimal unsettledAmount;

    @Column(name = "customer_id")
    private String customerId;
}