package za.co.digitalplatoon.invoiceservice.service.repo;

import za.co.digitalplatoon.invoiceservice.entity.InvoiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepo extends JpaRepository<InvoiceEntity, Long> {

}
