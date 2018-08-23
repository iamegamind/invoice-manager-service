package za.co.digitalplatoon.invoiceservice.api;

import za.co.digitalplatoon.invoiceservice.service.InvoiceService;
import za.co.digitalplatoon.invoiceservice.exception.InvoiceServiceException;
import za.co.digitalplatoon.invoiceservice.model.Invoice;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.List;
import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/invoices")
@Api(value = "Invoice Service")
public class InvoiceController {

    private final InvoiceService service;

    @Autowired
    public InvoiceController(InvoiceService service) {
        this.service = service;
    }

    @GetMapping
    @ApiOperation("Get all invoices")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Get all invoices", response = Invoice[].class)})
    public @ResponseBody
    ResponseEntity<List<Invoice>> viewAllInvoices(HttpServletRequest request) {
        try {
            return ResponseEntity.ok().body(service.getInvoices());
        } catch (InvoiceServiceException ex) {
            return ResponseEntity.status(ex.getStatus()).header("message", ex.getMessage()).body(null);
        }
    }

    @GetMapping("/{invoiceId}")
    @ApiOperation("Get an invoice by ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Get an invoice by ID", response = Invoice.class)})
    public @ResponseBody
    ResponseEntity<Invoice> viewInvoice(HttpServletRequest request, @PathVariable Long invoiceId) {
        try {
            return ResponseEntity.ok().body(service.getInvoice(invoiceId));
        } catch (InvoiceServiceException ex) {
            return ResponseEntity.status(ex.getStatus()).header("message", ex.getMessage()).body(null);
        }
    }

    @PostMapping
    @ApiOperation("Add a new invoice")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Add a new invoice", response = Invoice.class)})
    public @ResponseBody
    ResponseEntity<Invoice> addInvoice(HttpServletRequest request, @RequestBody Invoice invoice) {
        try {
            return ResponseEntity.ok().body(service.addInvoice(invoice));
        } catch (InvoiceServiceException ex) {
            return ResponseEntity.status(ex.getStatus()).header("message", ex.getMessage()).body(null);
        }
    }
}
