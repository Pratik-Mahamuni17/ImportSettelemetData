package com.intugratic.service.impl;

import com.intugratic.dto.OrderLevelReportDTO;
import com.intugratic.entity.OrderLevelReportEntity;
import com.intugratic.repository.OrderLevelReportRepository;
import com.intugratic.service.OrderLevelImportService;
import com.intugratic.service.mapper.OrderLevelExcelMapper;
import jakarta.transaction.Transactional;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class OrderLevelImportServiceImpl implements OrderLevelImportService {

    private final OrderLevelExcelMapper mapper;
    private final OrderLevelReportRepository repository;

    public OrderLevelImportServiceImpl(OrderLevelExcelMapper mapper,
                                       OrderLevelReportRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    @Override
    public void importExcel(MultipartFile file) throws Exception {

        Workbook workbook = WorkbookFactory.create(file.getInputStream());
        FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
        Sheet sheet = workbook.getSheet("Order Level");

        DataFormatter formatter = new DataFormatter();
        List<OrderLevelReportEntity> entities = new ArrayList<>();

        for (int i = 8; i <= sheet.getLastRowNum(); i++) {

            Row row = sheet.getRow(i);
            if (row == null) continue;

            // Guard 1: Order ID
            String orderId = formatter.formatCellValue(row.getCell(1)).trim();
            if (orderId.isEmpty() || orderId.equalsIgnoreCase("Order ID")) continue;

            // Guard 2: Subtotal must exist
            String subtotal = formatter.formatCellValue(row.getCell(14)).trim();
            if (subtotal.isEmpty()) continue;

            OrderLevelReportDTO dto = mapper.map(row, evaluator);
            entities.add(toEntity(dto));
        }

        repository.saveAll(entities);
        workbook.close();
    }

    private OrderLevelReportEntity toEntity(OrderLevelReportDTO dto) {

        OrderLevelReportEntity e = new OrderLevelReportEntity();

        e.setSno(dto.getSno());
        e.setOrderId(dto.getOrderId());
        e.setOrderDate(dto.getOrderDate());
        e.setWeekNo(dto.getWeekNo());
        e.setRestaurantName(dto.getRestaurantName());
        e.setRestaurantId(dto.getRestaurantId());
        e.setDiscountConstruct(dto.getDiscountConstruct());
        e.setModeOfPayment(dto.getModeOfPayment());
        e.setOrderStatus(dto.getOrderStatus());
        e.setCancellationPolicy(dto.getCancellationPolicy());
        e.setCancellationReason(dto.getCancellationReason());
        e.setCancelledRejectedState(dto.getCancelledRejectedState());
        e.setOrderType(dto.getOrderType());
        e.setDeliveryStateCode(dto.getDeliveryStateCode());

        e.setSubtotal(dto.getSubtotal());
        e.setPackagingCharge(dto.getPackagingCharge());
        e.setDeliveryChargeSelfLogistics(dto.getDeliveryChargeSelfLogistics());
        e.setRestaurantDiscountPromo(dto.getRestaurantDiscountPromo());
        e.setRestaurantDiscountOther(dto.getRestaurantDiscountOther());
        e.setBrandPackSubscriptionFee(dto.getBrandPackSubscriptionFee());
        e.setDeliveryChargeDiscount(dto.getDeliveryChargeDiscount());
        e.setTotalGstCollected(dto.getTotalGstCollected());
        e.setNetOrderValue(dto.getNetOrderValue());

        e.setCommissionableSubtotal(dto.getCommissionableSubtotal());
        e.setCommissionablePackagingCharge(dto.getCommissionablePackagingCharge());
        e.setCommissionableGst(dto.getCommissionableGst());
        e.setTotalCommissionableValue(dto.getTotalCommissionableValue());

        e.setBaseServiceFeePercent(dto.getBaseServiceFeePercent());
        e.setBaseServiceFee(dto.getBaseServiceFee());
        e.setActualOrderDistanceKm(dto.getActualOrderDistanceKm());
        e.setLongDistanceEnablementFee(dto.getLongDistanceEnablementFee());
        e.setDiscountOnLongDistanceFee(dto.getDiscountOnLongDistanceFee());
        e.setDiscountOnServiceFeeCapping(dto.getDiscountOnServiceFeeCapping());
        e.setPaymentMechanismFee(dto.getPaymentMechanismFee());

        e.setServiceFeeAndPaymentMechanismFee(dto.getServiceFeeAndPaymentMechanismFee());
        e.setTaxesOnServiceFee(dto.getTaxesOnServiceFee());
        e.setApplicableAmountForTcs(dto.getApplicableAmountForTcs());
        e.setApplicableAmountForSection95(dto.getApplicableAmountForSection95());
        e.setTaxCollectedAtSource(dto.getTaxCollectedAtSource());
        e.setTcsIgstAmount(dto.getTcsIgstAmount());
        e.setTds194OAmount(dto.getTds194OAmount());
        e.setGstPaidByZomato(dto.getGstPaidByZomato());
        e.setGstToBePaidByRestaurant(dto.getGstToBePaidByRestaurant());

        e.setGovernmentCharges(dto.getGovernmentCharges());
        e.setCustomerCompensation(dto.getCustomerCompensation());
        e.setDeliveryChargesRecovery(dto.getDeliveryChargesRecovery());
        e.setAmountReceivedInCash(dto.getAmountReceivedInCash());
        e.setCreditDebitNoteAdjustment(dto.getCreditDebitNoteAdjustment());
        e.setPromoRecoveryAdjustment(dto.getPromoRecoveryAdjustment());
        e.setExtraInventoryAds(dto.getExtraInventoryAds());
        e.setBrandLoyaltyPointsRedemption(dto.getBrandLoyaltyPointsRedemption());
        e.setExpressOrderFee(dto.getExpressOrderFee());

        e.setOtherOrderLevelDeductions(dto.getOtherOrderLevelDeductions());
        e.setNetDeductions(dto.getNetDeductions());
        e.setNetAdditions(dto.getNetAdditions());
        e.setOrderLevelPayout(dto.getOrderLevelPayout());

        e.setSettlementStatus(dto.getSettlementStatus());
        e.setSettlementDate(dto.getSettlementDate());
        e.setBankUtr(dto.getBankUtr());
        e.setUnsettledAmount(dto.getUnsettledAmount());
        e.setCustomerId(dto.getCustomerId());

        return e;
    }

}

