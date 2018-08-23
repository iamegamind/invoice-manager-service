package za.co.digitalplatoon.invoiceservice.service.repo;

import za.co.digitalplatoon.invoiceservice.entity.LineItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LineItemRepo extends JpaRepository<LineItemEntity, Long> {

}
