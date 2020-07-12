package itstep.grek.OnlineStore.repository;

import itstep.grek.OnlineStore.Models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Order findByNumber(String number);

    void deleteByNumber(String number);

    void deleteById(Long id);
}

