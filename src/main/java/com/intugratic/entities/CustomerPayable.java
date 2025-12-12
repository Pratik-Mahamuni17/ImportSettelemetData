package com.intugratic.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "customer_payable")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerPayable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // (1) Subtotal (items total)
    @Column(name = "subtotal", precision = 15, scale = 2)
    private BigDecimal subtotal;

    // (2) Packaging Charge
    @Column(name = "packaging_charge", precision = 15, scale = 2)
    private BigDecimal packagingCharge;

    // (3) Delivery charge for restaurants on self logistics
    @Column(name = "delivery_charge", precision = 15, scale = 2)
    private BigDecimal deliveryCharge;

    // (4) Restaurant discount (Promo)
    @Column(name = "restaurant_discount_promo", precision = 15, scale = 2)
    private BigDecimal restaurantDiscountPromo;

    // (5) Restaurant discount (BOGO, Freebies, Gold, Brand pack & others)
    @Column(name = "restaurant_discount_other", precision = 15, scale = 2)
    private BigDecimal restaurantDiscountOther;

    // (6) Brand pack subscription fee
    @Column(name = "brand_pack_subscription_fee", precision = 15, scale = 2)
    private BigDecimal brandPackSubscriptionFee;

    // (7) Delivery charge discount / Relisting discount
    @Column(name = "delivery_charge_discount", precision = 15, scale = 2)
    private BigDecimal deliveryChargeDiscount;

    // (8) Total GST collected from customers
    @Column(name = "total_gst_collected", precision = 15, scale = 2)
    private BigDecimal totalGstCollected;

    // (A) Net Order Value
    @Column(name = "net_order_value", precision = 15, scale = 2)
    private BigDecimal netOrderValue;
}
