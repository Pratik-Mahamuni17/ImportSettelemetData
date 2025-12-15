package com.intugratic.dto;

import lombok.Data;

import java.time.LocalDate;


import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class OrderLevelReportDTO {

    private Integer sno;
    private String orderId;
    private LocalDateTime orderDate;
    private String weekNo;
    private String restaurantName;
    private String restaurantId;
    private String discountConstruct;
    private String modeOfPayment;
    private String orderStatus;
    private String cancellationPolicy;
    private String cancellationReason;
    private String cancelledRejectedState;
    private String orderType;
    private String deliveryStateCode;

    private BigDecimal subtotal;
    private BigDecimal packagingCharge;
    private BigDecimal deliveryChargeSelfLogistics;
    private BigDecimal restaurantDiscountPromo;
    private BigDecimal restaurantDiscountOther;
    private BigDecimal brandPackSubscriptionFee;
    private BigDecimal deliveryChargeDiscount;
    private BigDecimal totalGstCollected;
    private BigDecimal netOrderValue;

    private BigDecimal commissionableSubtotal;
    private BigDecimal commissionablePackagingCharge;
    private BigDecimal commissionableGst;
    private BigDecimal totalCommissionableValue;

    private BigDecimal baseServiceFeePercent;
    private BigDecimal baseServiceFee;
    private BigDecimal actualOrderDistanceKm;
    private BigDecimal longDistanceEnablementFee;
    private BigDecimal discountOnLongDistanceFee;
    private BigDecimal discountOnServiceFeeCapping;
    private BigDecimal paymentMechanismFee;

    private BigDecimal serviceFeeAndPaymentMechanismFee;
    private BigDecimal taxesOnServiceFee;
    private BigDecimal applicableAmountForTcs;
    private BigDecimal applicableAmountForSection95;
    private BigDecimal taxCollectedAtSource;
    private BigDecimal tcsIgstAmount;
    private BigDecimal tds194OAmount;
    private BigDecimal gstPaidByZomato;
    private BigDecimal gstToBePaidByRestaurant;

    private BigDecimal governmentCharges;
    private BigDecimal customerCompensation;
    private BigDecimal deliveryChargesRecovery;
    private BigDecimal amountReceivedInCash;
    private BigDecimal creditDebitNoteAdjustment;
    private BigDecimal promoRecoveryAdjustment;
    private BigDecimal extraInventoryAds;
    private BigDecimal brandLoyaltyPointsRedemption;
    private BigDecimal expressOrderFee;

    private BigDecimal otherOrderLevelDeductions;
    private BigDecimal netDeductions;
    private BigDecimal netAdditions;
    private BigDecimal orderLevelPayout;

    private String settlementStatus;
    private LocalDate settlementDate;
    private String bankUtr;
    private BigDecimal unsettledAmount;
    private String customerId;
}

