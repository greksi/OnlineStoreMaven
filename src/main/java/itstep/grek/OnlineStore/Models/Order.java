package itstep.grek.OnlineStore.Models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Entity
@Table(name="tb_orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Номер заказа. Значение поля сохраняется
     * в колонке "number". Не может быть null.
     */
    @Column(name = "number", nullable = false)
    private String number = "";

    /**
     * Дата модификации заказа. Значение поля
     * сохраняется в колонке "date". Не может быть null.
     */
    @Column( name = "date",nullable = false)
    private Date date = new Date();


    @Column(name = "email")
    private String email = "";

    @Column(name = "phone")
    private String phone = "";

    @Column(name = "username")
    private String username = "";




    /**
     * Адрес доставки заказа. Значение поля
     * сохраняется в колонке "shipping_address".
     */
    @Column(name = "shipping_address")
    private String shippingAddress = "";

    /**
     * Детали доставки заказа. Значение поля
     * сохраняется в колонке "shipping_details".
     */
    @Column(name = "shipping_details")
    private String shippingDetails = "";

    /**
     * Описание заказа. Значение поля с
     * охраняется в колонке "description".
     */
    @Column(name = "description")
    private String description = "";

    /**
     * Клиент, оформивший заказ.
     * Значение поля (id объекта client) сохраняется в колонке "client_id".
     * Между объектами классов {@link Order} и {@link User} связь
     * один-к-одному, а именно каждая запись в одной таблице напрямую связана
     * с отдельной записью в другой таблице. Выборка объекта client до первого
     * доступа нему, при первом доступе к текущему объекту. Сущности связаны
     * полностью каскадным обновлением записей в базе данных.
     */
    @OneToOne(
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL
    )
    @JoinColumn(
            name = "client_id",
            referencedColumnName = "id"
    )
    private User client;

    /**
     * Менеджер, обработавший заказ. Значение поля (id объекта manager)
     * сохраняется в колонке "manager_id". Между объектами классов {@link Order}
     * и {@link User} связь много-к-одному, а именно каждая запись в одной таблице
     * напрямую связана с отдельной записью в другой таблице. Выборка объекта manager
     * до первого доступа нему, при первом доступе к текущему объекту.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
            name = "manager_id",
            referencedColumnName = "id"
    )
    private User manager;

    /**
     * Список торговых позиция текущего заказу. К текущему заказу можно добраться через
     * поле "order" в объекте класса {@link SalePosition}. Выборка продаж при первом
     * доступе к текущему объекту. Сущности связаны полностью каскадным
     * обновлением записей в базе данных.
     */
    @OneToMany(
            fetch = FetchType.EAGER,
            mappedBy = "order",
            cascade = CascadeType.ALL
    )

    private Collection<SalePosition> salePositions = new ArrayList<>();

    public Order() {
    }


    /**
     * Сравнивает текущий объект с объектом переданым как параметр.
     * Переопределенный метод родительского класса {@link Object}.
     *
     * @param object объект для сравнения с текущим объектом.
     * @return Значение типа boolean - результат сравнения текущего объекта
     * с переданным объектом.
     */
    @Override
    public boolean equals(Object object) {
        boolean result = super.equals(object);
        if (result) {
            final Order orderEntity = (Order) object;
            result = this.number.equals(orderEntity.number) &&
                    this.date.equals(orderEntity.date) &&
                    this.shippingAddress.equals(orderEntity.shippingAddress) &&
                    this.shippingDetails.equals(orderEntity.shippingDetails) &&
                    this.description.equals(orderEntity.description);
        }
        return result;
    }

    /**
     * Добавляет торговую позицию в текущий заказа.
     *
     * @param position Торговая позиция, которая будет добавлена в заказ.
     */
    public void addSalePosition(SalePosition position) {
        if (position!=null) {
            this.salePositions.add(position);
            if (position.getOrder() != this) {
                position.setOrder(this);
            }
        }
    }

    /**
     * Добавляет список торговых позиций в текущий заказ.
     *
     * @param positions Список торговых позиций,
     *                  которые будут дабавлены в заказ.
     */
    public void addSalePositions(final Collection<SalePosition> positions) {
        if (positions!=null) {
            positions.forEach(this::addSalePosition);
        }
    }

    /**
     * Удаляет торговую позицию из текущего заказа.
     *
     * @param position Торговая позиция, которая будет удалена из заказу.
     */
    public void removeSalePosition(final SalePosition position) {
        if (position!=null) {
            this.salePositions.remove(position);
        }
    }

    /**
     * Удаляет список торговых позиция из текущего заказа.
     *
     * @param positions Список торговых позиция, которые будут удалены из заказа.
     */
    public void removeSalePositions(final Collection<SalePosition> positions) {
        if (!positions.isEmpty()) {
            this.salePositions.removeAll(positions);
        }
    }

    /**
     * Очищает список торговых позиция текущего заказа.
     */
    public void clearSalePositions() {
        this.salePositions.clear();
    }

    /**
     * Конвертирует список торговых позиций текущего заказа в список
     * только для чтений и возвращает его.
     *
     * @return Объект типа {@link List} - список торговых позиция только
     * для чтения или пустой список.
     */
    public Collection<SalePosition> getSalePositions() {
        return new ArrayList<>(this.salePositions);
    }

    /**
     * Устанавливает список торговых позицияй текущему заказу.
     *
     * @param positions Список торговых позиция.
     */
    public void setSalePositions(final Collection<SalePosition> positions) {
        this.salePositions = positions;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Возвращает номер заказа.
     *
     * @return Значение типа {@link String} - номер заказа.
     */
    public String getNumber() {
        return this.number;
    }

    /**
     * Устанавливает номер заказа.
     *
     * @param number Номер заказа.
     */
    public void setNumber(final String number) {
        this.number = number;
    }

    /**
     * Возвращает дату последней модификации заказа.
     *
     * @return Значение типа {@link String} - дата модификации заказа.
     */
    public Date getDate() {
        return this.date;
    }

    /**
     * Устанавливает дату модификации заказа.
     *
     * @param date Дата модификации заказа.
     */
    public void setDate(final Date date) {
        this.date = date;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Возвращает клиента, оформивший заказ.
     *
     * @return Объект класса {@link User} - клиент, оформивший заказ.
     */


    public User getClient() {
        return this.client;
    }

    /**
     * Устанавливает клиента, оформившего заказ.
     *
     * @param client Клиент, оформивший заказ.
     */
    public void setClient(final User client) {
        this.client = client;
    }

    /**
     * Возвращает менеджера, обработавший заказ.
     *
     * @return Объект класса {@link User} - менеджер, обработавший заказ.
     */
    public User getManager() {
        return this.manager;
    }

    /**
     * Устанавливает менеджера, обработавший заказ.
     *
     * @param manager Менеджер, обработавший заказ.
     */
    public void setManager(final User manager) {
        this.manager = manager;
    }

    /**
     * Возвращает адрес доставки заказа.
     *
     * @return Значение типа {@link String} - адресс доставки заказа.
     */
    public String getShippingAddress() {
        return this.shippingAddress;
    }

    /**
     * Устанавливает адрес доставки заказа.
     *
     * @param shippingAddress Адрес доставки заказа.
     */
    public void setShippingAddress(final String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    /**
     * Возвращает детали доставки заказа.
     *
     * @return Значение типа {@link String} - детали доставки заказа.
     */
    public String getShippingDetails() {
        return this.shippingDetails;
    }

    /**
     * Устанавливает детали доставки заказа.
     *
     * @param shippingDetails Детали доставки заказа.
     */
    public void setShippingDetails(final String shippingDetails) {
        this.shippingDetails = shippingDetails;
    }

    /**
     * Возвращает описание заказа.
     *
     * @return Значение типа {@link String} - описание заказа.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Встанавливает описание заказа.
     *
     * @param description Описание заказа.
     */
    public void setDescription(final String description) {
        this.description = description;
    }

    /**
     * Возвращает цену заказа - общую стоимость всех торговых позиция.
     *
     * @return Значение типа double - цена заказа.
     */
    public double getPrice() {
        double price = 0;
        for (SalePosition salePosition : this.salePositions) {
            price += salePosition.getPrice();
        }
        return price;
    }

}
