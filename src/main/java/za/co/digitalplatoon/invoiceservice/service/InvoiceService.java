package za.co.digitalplatoon.invoiceservice.service;

import za.co.digitalplatoon.invoiceservice.model.Invoice;
import za.co.digitalplatoon.invoiceservice.exception.InvoiceServiceException;

import java.util.List;

public interface InvoiceService {

    List<Invoice> getInvoices() throws InvoiceServiceException;

    Invoice getInvoice(long invoiceId) throws InvoiceServiceException;

    Invoice addInvoice(Invoice invoice) throws InvoiceServiceException;
}
