package itstep.grek.OnlineStore.services.interfaces;


import itstep.grek.OnlineStore.Models.Order;

import java.util.Collection;

public interface OrderService {
    void add(Order order);

    void add(Collection<Order> orders);

    void update(Order order);

    Order  get(Long id);

    Collection<Order> getAll();

    void remove(Order order);

    void remove(String number);

    void remove(Long id);

    void remove(Collection<Order> orders);

    void removeAll();

    Order get(String number);

}

