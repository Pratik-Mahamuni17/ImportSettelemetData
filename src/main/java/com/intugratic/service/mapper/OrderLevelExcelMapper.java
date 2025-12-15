package com.intugratic.service.mapper;

import com.intugratic.dto.OrderLevelReportDTO;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class OrderLevelExcelMapper {

    private static final DataFormatter FORMATTER = new DataFormatter();

    public OrderLevelReportDTO map(Row row, FormulaEvaluator evaluator) {
        System.out.println(">>> USING FORMULA EVALUATOR MAPPER <<<");

        OrderLevelReportDTO dto = new OrderLevelReportDTO();

        dto.setSno(getInt(row, 0, evaluator));
        dto.setOrderId(getString(row, 1, evaluator));
        dto.setOrderDate(getDateTime(row, 2, evaluator));
        dto.setWeekNo(getString(row, 3, evaluator));
        dto.setRestaurantName(getString(row, 4, evaluator));
        dto.setRestaurantId(getString(row, 5, evaluator));
        dto.setDiscountConstruct(getString(row, 6, evaluator));
        dto.setModeOfPayment(getString(row, 7, evaluator));
        dto.setOrderStatus(getString(row, 8, evaluator));
        dto.setCancellationPolicy(getString(row, 9, evaluator));
        dto.setCancellationReason(getString(row, 10, evaluator));
        dto.setCancelledRejectedState(getString(row, 11, evaluator));
        dto.setOrderType(getString(row, 12, evaluator));
        dto.setDeliveryStateCode(getString(row, 13, evaluator));

        dto.setSubtotal(getDecimal(row, 14, evaluator));
        dto.setPackagingCharge(getDecimal(row, 15, evaluator));
        dto.setDeliveryChargeSelfLogistics(getDecimal(row, 16, evaluator));
        dto.setRestaurantDiscountPromo(getDecimal(row, 17, evaluator));
        dto.setRestaurantDiscountOther(getDecimal(row, 18, evaluator));
        dto.setBrandPackSubscriptionFee(getDecimal(row, 19, evaluator));
        dto.setDeliveryChargeDiscount(getDecimal(row, 20, evaluator));
        dto.setTotalGstCollected(getDecimal(row, 21, evaluator));
        dto.setNetOrderValue(getDecimal(row, 22, evaluator));

        dto.setCommissionableSubtotal(getDecimal(row, 23, evaluator));
        dto.setCommissionablePackagingCharge(getDecimal(row, 24, evaluator));
        dto.setCommissionableGst(getDecimal(row, 25, evaluator));
        dto.setTotalCommissionableValue(getDecimal(row, 26, evaluator));

        dto.setBaseServiceFeePercent(getDecimal(row, 27, evaluator));
        dto.setBaseServiceFee(getDecimal(row, 28, evaluator));
        dto.setActualOrderDistanceKm(getDecimal(row, 29, evaluator));
        dto.setLongDistanceEnablementFee(getDecimal(row, 30, evaluator));
        dto.setDiscountOnLongDistanceFee(getDecimal(row, 31, evaluator));
        dto.setDiscountOnServiceFeeCapping(getDecimal(row, 32, evaluator));
        dto.setPaymentMechanismFee(getDecimal(row, 33, evaluator));

        dto.setServiceFeeAndPaymentMechanismFee(getDecimal(row, 34, evaluator));
        dto.setTaxesOnServiceFee(getDecimal(row, 35, evaluator));
        dto.setApplicableAmountForTcs(getDecimal(row, 36, evaluator));
        dto.setApplicableAmountForSection95(getDecimal(row, 37, evaluator));
        dto.setTaxCollectedAtSource(getDecimal(row, 38, evaluator));
        dto.setTcsIgstAmount(getDecimal(row, 39, evaluator));
        dto.setTds194OAmount(getDecimal(row, 40, evaluator));
        dto.setGstPaidByZomato(getDecimal(row, 41, evaluator));
        dto.setGstToBePaidByRestaurant(getDecimal(row, 42, evaluator));

        dto.setGovernmentCharges(getDecimal(row, 43, evaluator));
        dto.setCustomerCompensation(getDecimal(row, 44, evaluator));
        dto.setDeliveryChargesRecovery(getDecimal(row, 45, evaluator));
        dto.setAmountReceivedInCash(getDecimal(row, 46, evaluator));
        dto.setCreditDebitNoteAdjustment(getDecimal(row, 47, evaluator));
        dto.setPromoRecoveryAdjustment(getDecimal(row, 48, evaluator));
        dto.setExtraInventoryAds(getDecimal(row, 49, evaluator));
        dto.setBrandLoyaltyPointsRedemption(getDecimal(row, 50, evaluator));
        dto.setExpressOrderFee(getDecimal(row, 51, evaluator));

        dto.setOtherOrderLevelDeductions(getDecimal(row, 52, evaluator));
        dto.setNetDeductions(getDecimal(row, 53, evaluator));
        dto.setNetAdditions(getDecimal(row, 54, evaluator));
        dto.setOrderLevelPayout(getDecimal(row, 55, evaluator));

        dto.setSettlementStatus(getString(row, 56, evaluator));
        dto.setSettlementDate(getDate(row, 57, evaluator));
        dto.setBankUtr(getString(row, 58, evaluator));
        dto.setUnsettledAmount(getDecimal(row, 59, evaluator));
        dto.setCustomerId(getString(row, 60, evaluator));

        return dto;
    }

    // ================= HELPERS =================

    private String getString(Row row, int index, FormulaEvaluator evaluator) {
        Cell cell = row.getCell(index);
        if (cell == null) return null;

        String value = FORMATTER.formatCellValue(cell, evaluator).trim();
        return value.isEmpty() ? null : value;
    }

    private BigDecimal getDecimal(Row row, int index, FormulaEvaluator evaluator) {
        Cell cell = row.getCell(index);
        if (cell == null) return BigDecimal.ZERO;

        try {
            String value = FORMATTER.formatCellValue(cell, evaluator).trim();

            if (value.isEmpty() || value.equalsIgnoreCase("NA")) {
                return BigDecimal.ZERO;
            }

            value = value.replace("%", "");
            return new BigDecimal(value);

        } catch (Exception e) {
            return BigDecimal.ZERO;
        }
    }

    private Integer getInt(Row row, int index, FormulaEvaluator evaluator) {
        Cell cell = row.getCell(index);
        if (cell == null) return null;

        String value = FORMATTER.formatCellValue(cell, evaluator).trim();
        return value.isEmpty() ? null : Integer.parseInt(value);
    }

    private LocalDateTime getDateTime(Row row, int index, FormulaEvaluator evaluator) {
        Cell cell = row.getCell(index);
        if (cell == null) return null;

        try {
            // 🔥 Handle NUMERIC Excel date safely
            if (cell.getCellType() == CellType.NUMERIC && DateUtil.isValidExcelDate(cell.getNumericCellValue())) {
                return cell.getLocalDateTimeCellValue();
            }

            // 🔥 Handle STRING date safely
            String value = FORMATTER.formatCellValue(cell, evaluator).trim();
            if (value.isEmpty() || value.equalsIgnoreCase("NA")) {
                return null;
            }

            // Example formats:
            // 2025-12-06 21:55:49
            // 06-12-2025 21:55:49
            value = value.replace(" ", "T");

            return LocalDateTime.parse(value);

        } catch (Exception e) {
            return null; // never crash import
        }
    }


    private LocalDate getDate(Row row, int index, FormulaEvaluator evaluator) {
        LocalDateTime dt = getDateTime(row, index, evaluator);
        return dt == null ? null : dt.toLocalDate();
    }
}
