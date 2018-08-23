package za.co.digitalplatoon.invoiceservice.service.impl;

import za.co.digitalplatoon.invoiceservice.service.InvoiceService;
import za.co.digitalplatoon.invoiceservice.entity.InvoiceEntity;
import za.co.digitalplatoon.invoiceservice.entity.LineItemEntity;
import za.co.digitalplatoon.invoiceservice.model.Invoice;
import za.co.digitalplatoon.invoiceservice.exception.InvoiceServiceException;
import za.co.digitalplatoon.invoiceservice.model.LineItem;
import za.co.digitalplatoon.invoiceservice.service.repo.InvoiceRepo;
import za.co.digitalplatoon.invoiceservice.service.util.InvoiceUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class InvoiceServiceImpl implements InvoiceService {

    private InvoiceRepo invoiceRepo;

    @Autowired
    public InvoiceServiceImpl(InvoiceRepo invoiceRepo) {
        this.invoiceRepo = invoiceRepo;
    }

    @Override
    public List<Invoice> getInvoices() throws InvoiceServiceException {
        final ArrayList<Invoice> invoices = new ArrayList<>();

        invoiceRepo.findAll().forEach(invoice -> {
            Invoice invoiceData = populate(invoice);
            handleCalculations(invoiceData);
            invoices.add(invoiceData);
        });
        return invoices;
    }

    @Override
    public Invoice getInvoice(long invoiceId) throws InvoiceServiceException {
        if (invoiceRepo.existsById(invoiceId)) {
            final InvoiceEntity invoice = invoiceRepo.findById(invoiceId).get();
            Invoice invoiceData = populate(invoice);
            handleCalculations(invoiceData);
            return invoiceData;
        }
        throw new InvoiceServiceException(HttpStatus.NO_CONTENT, String.format("No Invoice with [%s]", invoiceId));
    }

    @Override
    public Invoice addInvoice(Invoice invoice) throws InvoiceServiceException {
        final InvoiceEntity inv = new InvoiceEntity();
        inv.setClient(invoice.getClient());
        inv.setInvoiceDate(new Date());
        inv.setVatRate(invoice.getVatRate());
        final InvoiceEntity savedInvoice = invoiceRepo.save(inv);
        invoice.setId(savedInvoice.getId());

        return invoice;
    }

    private Invoice populate(InvoiceEntity invoice) {
        final Invoice invoiceData = new Invoice();
        invoiceData.setId(invoice.getId());
        invoiceData.setClient(invoice.getClient());
        invoiceData.setVatRate(invoice.getVatRate());
        invoiceData.setInvoiceDate(invoice.getInvoiceDate());
        invoiceData.setLineItems(buildItems(invoice.getLineDataSet()));
        return invoiceData;
    }

    private List<LineItem> buildItems(Set<LineItemEntity> lineItemSet) {
        final List<LineItem> items = new ArrayList<>();
        lineItemSet.forEach(li -> {
            LineItem lineItem = new LineItem();
            lineItem.setDescription(li.getDescription());
            lineItem.setId(li.getId());
            lineItem.setQuantity(li.getQuantity());
            lineItem.setUnitPrice(li.getUnitPrice());

            //Calculate total price.
            final BigDecimal total = InvoiceUtil.calculateLineItemTotal(lineItem.getQuantity(), lineItem.getUnitPrice());
            lineItem.setLineItemTotal(total);
            items.add(lineItem);
        });
        return items;
    }

    private void handleCalculations(Invoice invoiceData) {
        final BigDecimal total = InvoiceUtil.calculateInvoiceTotal(invoiceData.getLineItems());
        final BigDecimal vat = InvoiceUtil.calculateVat(15l, total);
        final BigDecimal subTotal = InvoiceUtil.calculateSubTotal(total, vat);

        invoiceData.setVat(vat);
        invoiceData.setTotal(total);
        invoiceData.setSubTotal(subTotal);
    }
}
