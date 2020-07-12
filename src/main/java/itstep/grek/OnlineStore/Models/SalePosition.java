package itstep.grek.OnlineStore.Models;

import javax.persistence.*;

@Entity
@Table(name = "tb_sales")
public class SalePosition  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", referencedColumnName = "id",nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "order_id", referencedColumnName = "id", nullable = false )
    private Order order;

    @Column(name = "quantity",nullable = false )
    private int quantity = 0;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getPrice() {
        return this.quantity * this.product.getPrice();
    }

    /**
     * Увеличивает количество товаров в позиции на 1.
     */
    public void quantityIncrement() {
        this.quantity++;
    }

    /**
     * Уменьшает  количество товаров в позиции на 1.
     */

    public void quantityDecrement() {
        if(quantity> 1) {
            this.quantity--;
        }
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public SalePosition() {
    }

    @Override
    public boolean equals(Object object) {
            SalePosition position = (SalePosition) object;
            boolean result = this.product.equals(position.product);
        return result;
                //(this.product!=null && position.product!=null)? this.product.equals(position.product) : false;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    public SalePosition(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

}