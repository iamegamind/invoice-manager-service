package za.co.digitalplatoon.invoiceservice.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import lombok.Data;


@Data
public class Invoice {

    private Long id;
    private long vatRate;
    private String client;
    private Date invoiceDate;
    private List<LineItem> lineItems;

    private BigDecimal subTotal;
    private BigDecimal vat;
    private BigDecimal total;
}
