package itstep.grek.OnlineStore.services.impl;

import itstep.grek.OnlineStore.Models.SalePosition;
import itstep.grek.OnlineStore.Models.ShoppingCart;
import itstep.grek.OnlineStore.repository.ShoppingCartRepository;
import itstep.grek.OnlineStore.services.interfaces.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service

public class ShoppingCartServiceImpl implements ShoppingCartService {
    /**
     * Реализация интерфейса для работы з торговой корзиной.
     */
    private ShoppingCartRepository shoppingCartRepository;

    /**
     * Конструктор для инициализации основных переменных сервиса.
     * Помечаный аннотацией @Autowired, которая позволит Spring
     * автоматически инициализировать объект.
     *
     * @param shoppingCartRepository Реализация интерфейса для работы з торговой корзиной.
     */
    @Autowired
    @SuppressWarnings("SpringJavaAutowiringInspection")
    public ShoppingCartServiceImpl(ShoppingCartRepository shoppingCartRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
    }

    /**
     * Возвращает объект корзину.
     * Режим только для чтения.
     *
     * @return Объект класса {@link ShoppingCart} - торговая корзина.
     * @throws NullPointerException Бросает исключение, если корзина отсутствует.
     */
    @Override
    @Transactional(readOnly = true)
    public ShoppingCart getShoppingCart() throws NullPointerException {
        final ShoppingCart shoppingCart = this.shoppingCartRepository.get();
        if (shoppingCart== null) {
            throw new NullPointerException("Can't find shopping cart!");
        }
        return shoppingCart;
    }

    /**
     * Добавляет торговую позицию в список корзины.
     *
     * @param position Торговая позиция,
     *                 которая будет добавлена в корзину.
     */
    @Override
    @Transactional
    public void add(SalePosition position) {
        if (position!=null) {
            this.shoppingCartRepository.addSalePosition(position);
        }
    }

    /**
     * Возвращает список всех торговых позиций в корзине.
     * Режим только для чтения.
     *
     * @return Объект типа {@link List} - список торговых позиций.
     */
    @Override
    @Transactional(readOnly = true)
    public Collection<SalePosition> getSalePositions() {
        return this.shoppingCartRepository.getSalePositions();
    }

    /**
     * Удаляет торговую позицию из корзины.
     *
     * @param position Торговая позиция для удаления из корзины.
     */
    @Override
    @Transactional
    public void remove(SalePosition position) {
        if (position!=null) {
            this.shoppingCartRepository.removeSalePosition(position);
        }
    }


    /**
     * Очищает корзину.
     * Удаляет все торговые позиции в корзине.
     */
    @Override
    @Transactional
    public void clear() {
        this.shoppingCartRepository.clearSalePositions();
    }

    /**
     * Возвращает цену корзины - цена всех продаж.
     * Режим только для чтения.
     *
     * @return Значение типа double - цена корзины.
     */
    @Override
    @Transactional(readOnly = true)
    public double getPrice() {
        return this.shoppingCartRepository.getPrice();
    }

    /**
     * Возвращает размер корзины, то есть количество товаров в корзине.
     * Режим только для чтения.
     *
     * @return Значение типа int - количество товаров в корзине.
     */
    @Override
    @Transactional(readOnly = true)
    public int getSize() {
        return this.shoppingCartRepository.getSize();
    }

    @Override
    public void decrementSalePosition(SalePosition salePosition) {
        if (salePosition!=null) {
            this.shoppingCartRepository.quantityDecrement(salePosition);
        }
    }


}

