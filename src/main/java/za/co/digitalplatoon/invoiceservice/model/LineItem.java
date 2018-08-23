package za.co.digitalplatoon.invoiceservice.model;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class LineItem {

    private long id;
    private long quantity;
    private String description;
    private BigDecimal unitPrice;

    private BigDecimal lineItemTotal;
}
