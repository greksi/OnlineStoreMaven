package itstep.grek.OnlineStore.services.impl;

import itstep.grek.OnlineStore.Models.Order;
import itstep.grek.OnlineStore.repository.OrderRepository;
import itstep.grek.OnlineStore.services.interfaces.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
public class OrderServiceImpl implements OrderService {
    /**
     * Реализация интерфейса {@link OrderRepository}
     * для работы заказов с базой данных.
     */
    private  OrderRepository repository;

    /**
     * Конструктор для инициализации основных переменных сервиса.
     * Помечаный аннотацией @Autowired, которая позволит Spring
     * автоматически инициализировать объект.
     *
     * @param repository Реализация интерфейса {@link OrderRepository}
     *                   для работы категорий с базой данных.
     */
    @Autowired
    @SuppressWarnings("SpringJavaAutowiringInspection")
    public OrderServiceImpl(OrderRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public void add(Order order) {
        if (order!=null) {
            this.repository.save(order);
        }
    }


    @Override
    public void add(Collection< Order > orders) {
        if(!orders.isEmpty()){
            this.repository.saveAll(orders);
        }
    }

    @Override
    public void update(Order order) {

    }

    @Override
    @Transactional(readOnly = true)
    public Order get(Long id) {
        Order order = this.repository.getOne(id);
        if (order==null) {
            throw new NullPointerException("Can't find order by id " + id + "!");
        }
        return order;
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Order> getAll() {
        return this.repository.findAll();
    }

    @Override
    @Transactional
    public void remove(Order order) {
        if (order != null) {
            this.repository.delete(order);
        }
    }


    @Override
    @Transactional
    public void remove(Long id) {
            this.repository.deleteById(id);
    }

    @Override
    @Transactional
    public void remove(Collection< Order > orders) {
        if (!orders.isEmpty()) {
            this.repository.deleteAll(orders);
        }
    }

    @Override
    @Transactional
    public void removeAll() {
        this.repository.deleteAll();
    }

    /**
     * Возвращает заказ из базы даных, у которого совпадает
     * уникальный номером с значением входящего параметра.
     * Режим только для чтения.
     *
     * @param number Номер заказа для возврата.
     * @return Объект класса {@link Order} - заказ с уникальным номером.
     * @throws IllegalArgumentException Бросает исключение,
     *                                  если пустой входной параметр number.
     * @throws NullPointerException     Бросает исключение,
     *                                  если не найден заказ с входящим параметром number.
     */
    @Override
    @Transactional(readOnly = true)
    public Order get(final String number) throws IllegalArgumentException, NullPointerException {
        if (number.isEmpty()) {
            throw new IllegalArgumentException("No orderEntity number!");
        }
        Order orderEntity = this.repository.findByNumber(number);
        if (orderEntity==null) {
            throw new NullPointerException("Can't find orderEntity by number " + number + "!");
        }
        return orderEntity;
    }

    /**
     * Удаляет заказ из базы даных, у которого совпадает
     * уникальный номером с значением входящего параметра.
     *
     * @param number Номер заказа для удаление.
     */
    @Override
    @Transactional
    public void remove(final String number) {
        if (!number.isEmpty()) {
            this.repository.deleteByNumber(number);
        }
    }
}