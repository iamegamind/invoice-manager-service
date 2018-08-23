package za.co.digitalplatoon.invoiceservice.service.util;

import za.co.digitalplatoon.invoiceservice.model.LineItem;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class InvoiceUtil {

    public static BigDecimal calculateLineItemTotal(long quantity, BigDecimal unitPrice) {
        final BigDecimal totalCost = unitPrice.multiply(new BigDecimal(quantity));
        return totalCost.setScale(2, RoundingMode.HALF_UP);
    }

    public static BigDecimal calculateInvoiceTotal(List<LineItem> lineItems) {
        BigDecimal total = BigDecimal.ZERO;
        for (LineItem lineItem : lineItems) {
            total = total.add(lineItem.getLineItemTotal());
        }

        return total.setScale(2, RoundingMode.HALF_UP);
    }

    public static BigDecimal calculateVat(long vatRate, BigDecimal totalPrice) {
        final BigDecimal totalCost = totalPrice.multiply(new BigDecimal(((double) 15 / 100)));
        return totalCost.setScale(2, RoundingMode.HALF_UP);
    }

    public static BigDecimal calculateSubTotal(BigDecimal total, BigDecimal vat) {
        return total.subtract(vat).setScale(2, RoundingMode.HALF_UP);
    }
}
