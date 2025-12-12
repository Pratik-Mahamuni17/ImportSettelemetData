package com.intugratic.util;

import com.intugratic.entities.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * ExcelOrderParser
 * - Assumes the column header row is at Excel row 7 -> POI index 6
 * - Normalizes headers and maps them to entity groups
 * - Logs detected headers and missing expected headers
 */
public class ExcelOrderParser {

    // MAIN ENTRY POINT
    public static List<OrderLevelReport> parse(InputStream is) throws Exception {
        Workbook workbook = new XSSFWorkbook(is);
        Sheet sheet = workbook.getSheetAt(0);

        for (int i = 0; i < 15; i++) {
            Row r = sheet.getRow(i);
            System.out.println("ROW " + i + ":");
            for (Cell c : r) {
                System.out.print("[" + getString(c) + "] ");
            }
            System.out.println("\n------------------------------");
        }


        // IMPORTANT: Excel row 7 -> POI index 6
        int headerRowIndex = 6;

        Row headerRow = sheet.getRow(headerRowIndex);
        Map<String, Integer> headerIndex = buildHeaderMap(headerRow);

        // Optional: log any expected headers that are missing (helps debugging)
        logMissingHeaders(headerIndex, expectedHeaders());

        List<OrderLevelReport> orders = new ArrayList<>();
        int lastRow = sheet.getLastRowNum();

        for (int i = headerRowIndex + 1; i <= lastRow; i++) {
            Row row = sheet.getRow(i);
            if (row == null || isRowEmpty(row)) continue;

            OrderLevelReport order = mapRowToOrder(row, headerIndex);
            orders.add(order);
        }

        workbook.close();
        return orders;
    }

    // ---------------------------- HEADER MAP ------------------------------

    private static Map<String, Integer> buildHeaderMap(Row row) {
        Map<String, Integer> map = new HashMap<>();
        if (row == null) return map;

        for (Cell cell : row) {
            String header = getString(cell);
            System.out.println("RAW HEADER: [" + header + "]");
            System.out.println("NORMALIZED HEADER: [" + normalize(header) + "]");
            if (header != null && !header.trim().isEmpty()) {
                String n = normalize(header);
                System.out.println("HEADER: " + n);
                map.put(n, cell.getColumnIndex());
            }
        }
        return map;
    }

    private static void logMissingHeaders(Map<String, Integer> headers, Set<String> expected) {
        List<String> missing = new ArrayList<>();
        for (String e : expected) {
            if (!headers.containsKey(e)) missing.add(e);
        }
        if (!missing.isEmpty()) {
            System.out.println("MISSING HEADER(S): ");
            missing.forEach(h -> System.out.println("  - " + h));
        } else {
            System.out.println("All expected headers found.");
        }
    }

    private static Set<String> expectedHeaders() {
        return new HashSet<>(Arrays.asList(
                // basic
                "s no", "order id", "order date", "week no", "res name", "res id",
                "discount construct", "mode of payment", "order status delivered cancelled rejected",
                "cancellation policy amount refunded back to restaurant partner",
                "cancellation rejection reason", "cancelled rejected state order status at the time it was cancelled rejected",
                "order type", "delivery state code", "customer id",

                // customer payable
                "subtotal items total", "packaging charge", "delivery charge for restaurants on self logistics",
                "restaurant discount promo", "restaurant discount bogo freebies gold brand pack others",
                "brand pack subscription fee", "delivery charge discount relisting discount",
                "total gst collected from customers", "net order value",

                // commission values
                "commissionable value of subtotal excluding restaurant discounts",
                "commissionable value of packaging charge",
                "commissionable value of total gst collected from customers",
                "total commissionable value",
                "base service fee percentage", "base service fee amount",

                // fees
                "actual order distance km", "long distance enablement fee",
                "discount on long distance enablement fee", "discount on service fee due to 30 capping",
                "payment mechanism fee", "service fee payment mechanism fee",
                "taxes on service fee payment mechanism fee", "applicable amount for tcs",
                "applicable amount for 9 5",

                // government charges
                "tax collected at source", "tcs igst amount", "tds 194o amount",
                "gst paid by zomato on behalf of restaurant under section 9 5",
                "gst to be paid by restaurant partner to govt", "government charges",

                // deductions
                "customer compensation recoupment", "delivery charges recovery",
                "amount received in cash on self delivery orders", "credit note debit note adjustment",
                "promo recovery adjustment", "extra inventory ads order level deduction",
                "brand loyalty points redemption", "express order fee",
                "other order level deductions", "net deductions",

                // additions
                "net additions",

                // settlement
                "order level payout", "settlement status", "settlement date",
                "bank utr", "unsettled amount"
        ));
    }

    private static String normalize(String s) {
        if (s == null) return "";
        return s.toLowerCase()
                .replace("\n", " ")
                .replace("\r", " ")
                .replaceAll("\\s+", " ")
                .trim();
    }

    // ---------------------------- ROW PARSER ------------------------------

    private static OrderLevelReport mapRowToOrder(Row row, Map<String, Integer> h) {

        OrderLevelReport order = new OrderLevelReport();

        order.setOrderId(getString(getCell(row, h, "order id")));
        order.setOrderDate(getDate(getCell(row, h, "order date")));
        order.setWeekNo(getString(getCell(row, h, "week no")));
        order.setRestaurantName(getString(getCell(row, h, "res name")));
        order.setRestaurantId(getString(getCell(row, h, "res id")));
        order.setDiscountConstruct(getString(getCell(row, h, "discount construct")));
        order.setModeOfPayment(getString(getCell(row, h, "mode of payment")));

        order.setOrderStatus(getString(getCell(row, h, "order status delivered cancelled rejected")));
        order.setCancellationPolicy(getString(getCell(row, h, "cancellation policy amount refunded back to restaurant partner")));
        order.setCancellationReason(getString(getCell(row, h, "cancellation rejection reason")));
        order.setCancelledState(getString(getCell(row, h, "cancelled rejected state order status at the time it was cancelled rejected")));

        order.setOrderType(getString(getCell(row, h, "order type")));
        order.setDeliveryStateCode(getString(getCell(row, h, "delivery state code")));
        order.setCustomerId(getString(getCell(row, h, "customer id")));

        // grouped objects
        order.setCustomerPayable(buildCustomerPayable(row, h));
        order.setCommissionValues(buildCommissionValues(row, h));
        order.setFees(buildFees(row, h));
        order.setGovernmentCharges(buildGovernmentCharges(row, h));
        order.setDeductions(buildDeductions(row, h));
        order.setAdditions(buildAdditions(row, h));
        order.setSettlementInfo(buildSettlementInfo(row, h));

        return order;
    }

    // ---------------------------- GROUP BUILDERS ------------------------------

    private static CustomerPayable buildCustomerPayable(Row row, Map<String, Integer> h) {
        return CustomerPayable.builder()
                .subtotal(getDecimal(getCell(row, h, "subtotal items total")))
                .packagingCharge(getDecimal(getCell(row, h, "packaging charge")))
                .deliveryCharge(getDecimal(getCell(row, h, "delivery charge for restaurants on self logistics")))
                .restaurantDiscountPromo(getDecimal(getCell(row, h, "restaurant discount promo")))
                .restaurantDiscountOther(getDecimal(getCell(row, h, "restaurant discount bogo freebies gold brand pack others")))
                .brandPackSubscriptionFee(getDecimal(getCell(row, h, "brand pack subscription fee")))
                .deliveryChargeDiscount(getDecimal(getCell(row, h, "delivery charge discount relisting discount")))
                .totalGstCollected(getDecimal(getCell(row, h, "total gst collected from customers")))
                .netOrderValue(getDecimal(getCell(row, h, "net order value")))
                .build();
    }

    private static CommissionValues buildCommissionValues(Row row, Map<String, Integer> h) {
        return CommissionValues.builder()
                .commissionableSubtotal(getDecimal(getCell(row, h, "commissionable value of subtotal excluding restaurant discounts")))
                .commissionablePackaging(getDecimal(getCell(row, h, "commissionable value of packaging charge")))
                .commissionableGst(getDecimal(getCell(row, h, "commissionable value of total gst collected from customers")))
                .totalCommissionableValue(getDecimal(getCell(row, h, "total commissionable value")))
                .baseServiceFeePercentage(getDecimal(getCell(row, h, "base service fee percentage")))
                .baseServiceFeeAmount(getDecimal(getCell(row, h, "base service fee amount")))
                .build();
    }

    private static Fees buildFees(Row row, Map<String, Integer> h) {
        return Fees.builder()
                .orderDistanceKm(getDecimal(getCell(row, h, "actual order distance km")))
                .longDistanceFee(getDecimal(getCell(row, h, "long distance enablement fee")))
                .longDistanceFeeDiscount(getDecimal(getCell(row, h, "discount on long distance enablement fee")))
                .serviceFeeCappingDiscount(getDecimal(getCell(row, h, "discount on service fee due to 30 capping")))
                .paymentMechanismFee(getDecimal(getCell(row, h, "payment mechanism fee")))
                .totalServicePaymentFee(getDecimal(getCell(row, h, "service fee payment mechanism fee")))
                .serviceTaxAmount(getDecimal(getCell(row, h, "taxes on service fee payment mechanism fee")))
                .tcsApplicableAmount(getDecimal(getCell(row, h, "applicable amount for tcs")))
                .section95ApplicableAmount(getDecimal(getCell(row, h, "applicable amount for 9 5")))
                .build();
    }

    private static GovernmentCharges buildGovernmentCharges(Row row, Map<String, Integer> h) {
        return GovernmentCharges.builder()
                .taxCollectedAtSource(getDecimal(getCell(row, h, "tax collected at source")))
                .tcsIgstAmount(getDecimal(getCell(row, h, "tcs igst amount")))
                .tds194oAmount(getDecimal(getCell(row, h, "tds 194o amount")))
                .gstPaidUnder95(getDecimal(getCell(row, h, "gst paid by zomato on behalf of restaurant under section 9 5")))
                .gstPayableByRestaurant(getDecimal(getCell(row, h, "gst to be paid by restaurant partner to govt")))
                .totalGovernmentCharges(getDecimal(getCell(row, h, "government charges")))
                .build();
    }

    private static Deductions buildDeductions(Row row, Map<String, Integer> h) {
        return Deductions.builder()
                .customerCompensation(getDecimal(getCell(row, h, "customer compensation recoupment")))
                .deliveryChargesRecovery(getDecimal(getCell(row, h, "delivery charges recovery")))
                .cashReceived(getDecimal(getCell(row, h, "amount received in cash on self delivery orders")))
                .creditDebitAdjustment(getDecimal(getCell(row, h, "credit note debit note adjustment")))
                .promoRecovery(getDecimal(getCell(row, h, "promo recovery adjustment")))
                .extraInventoryAds(getDecimal(getCell(row, h, "extra inventory ads order level deduction")))
                .brandLoyaltyRedemption(getDecimal(getCell(row, h, "brand loyalty points redemption")))
                .expressOrderFee(getDecimal(getCell(row, h, "express order fee")))
                .otherOrderLevelDeductions(getDecimal(getCell(row, h, "other order level deductions")))
                .netDeductions(getDecimal(getCell(row, h, "net deductions")))
                .build();
    }

    private static Additions buildAdditions(Row row, Map<String, Integer> h) {
        return Additions.builder()
                .netAdditions(getDecimal(getCell(row, h, "net additions")))
                .build();
    }

    private static SettlementInfo buildSettlementInfo(Row row, Map<String, Integer> h) {
        return SettlementInfo.builder()
                .orderLevelPayout(getDecimal(getCell(row, h, "order level payout")))
                .settlementStatus(getString(getCell(row, h, "settlement status")))
                .settlementDate(getDate(getCell(row, h, "settlement date")))
                .bankUtr(getString(getCell(row, h, "bank utr")))
                .unsettledAmount(getDecimal(getCell(row, h, "unsettled amount")))
                .build();
    }

    // ---------------------------- HELPERS ------------------------------

    private static boolean isRowEmpty(Row row) {
        for (Cell c : row) {
            if (c != null && c.getCellType() != CellType.BLANK) {
                String v = getString(c);
                if (v != null && !v.trim().isEmpty()) return false;
            }
        }
        return true;
    }

    private static Cell getCell(Row row, Map<String, Integer> headers, String headerName) {
        Integer idx = headers.get(normalize(headerName));
        return idx == null ? null : row.getCell(idx, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
    }

    private static String getString(Cell cell) {
        if (cell == null) return null;

        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue().trim();
            case NUMERIC -> {
                if (DateUtil.isCellDateFormatted(cell))
                    yield cell.getLocalDateTimeCellValue().toLocalDate().toString();
                double d = cell.getNumericCellValue();
                yield (d == (long) d) ? String.valueOf((long) d) : String.valueOf(d);
            }
            case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
            case FORMULA -> {
                try {
                    yield cell.getStringCellValue();
                } catch (Exception e) {
                    yield String.valueOf(cell.getNumericCellValue());
                }
            }
            default -> null;
        };
    }

    private static BigDecimal getDecimal(Cell cell) {
        if (cell == null) return BigDecimal.ZERO;
        try {
            if (cell.getCellType() == CellType.NUMERIC)
                return BigDecimal.valueOf(cell.getNumericCellValue());

            if (cell.getCellType() == CellType.STRING) {
                String s = cell.getStringCellValue()
                        .trim()
                        .replace(",", "");
                return s.isEmpty() ? BigDecimal.ZERO : new BigDecimal(s);
            }
        } catch (Exception ignored) {
        }

        return BigDecimal.ZERO;
    }

    private static LocalDate getDate(Cell cell) {
        if (cell == null)
            return null;

        try {
            if (DateUtil.isCellDateFormatted(cell))
                return cell.getLocalDateTimeCellValue().toLocalDate();

            String text = getString(cell);
            if (text == null || text.isEmpty())
                return null;

            for (String f : List.of("yyyy-MM-dd", "dd/MM/yyyy", "d-M-yyyy")) {
                try {
                    return LocalDate.parse(text, DateTimeFormatter.ofPattern(f));
                } catch (Exception ignored) {
                }
            }

        } catch (Exception ignored) {
        }

        return null;
    }
}
