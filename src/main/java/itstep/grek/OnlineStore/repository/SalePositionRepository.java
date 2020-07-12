package itstep.grek.OnlineStore.repository;

import itstep.grek.OnlineStore.Models.SalePosition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SalePositionRepository extends JpaRepository< SalePosition, Long> {
    Optional< SalePosition > findById(Long id);
}
