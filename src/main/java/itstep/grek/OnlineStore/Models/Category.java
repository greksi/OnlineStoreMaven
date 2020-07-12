package itstep.grek.OnlineStore.Models;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;

@Entity
@Table(name = "tb_categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title = "";

    @Column(name = "url", nullable = false)
    private String url = "";

    @Column(name = "description")
    private String description = "";

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category", cascade = CascadeType.ALL )
    private Collection<Product> products = new HashSet<>();

    @Override
    public int hashCode() {
        int result = this.title.hashCode();
        result = 31 * result + this.url.hashCode();
        result = 31 * result + this.description.hashCode();
        return result;
    }

    public void addProduct(Product product) {
        if (product!=null) {
            this.products.add(product);
        }
    }

    public void addProducts(Collection<Product> products) {
        if (!products.isEmpty()) {
            this.products.addAll(products);
        }
    }

    public void removeProduct(final Product product) {
        if (product!=null) {
            this.products.remove(product);
        }
    }

    /**
     * Удаляет список товаров из списка текущей категории.
     *
     * @param products Список товаров, которые будут удалены из текущей категории.
     */
    public void removeProducts(Collection<Product> products) {
        if (!products.isEmpty()) {
            this.products.removeAll(products);
        }
    }

    /**
     * Очищает список товаров products.
     */
    public void clearProducts() {
        this.products.clear();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Collection< Product > getProducts() {
        return products;
    }

    public void setProducts(Collection< Product > products) {
        this.products = products;
    }
}

