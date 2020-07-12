package itstep.grek.OnlineStore.Models;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_product")
public class Product{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "article", nullable = false)
    private int article = 0;

    @Column(name = "title", nullable = false )
    private String title = "";

    @Column(name = "url", nullable = false )
    private String url = "";

    @Column(name = "parameters")
    private String parameters = "";

    @Column(name = "description")
    private String description = "";

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", referencedColumnName = "id" )
    private Category category;

   @Column(name = "fileName")
   private String FileName;

    @Column(name = "price", nullable = false)
    private double price = 0;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getArticle() {
        return article;
    }

    public void setArticle(int article) {
        this.article = article;
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

    public String getParameters() {
        return parameters;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getFileName() {
        return FileName;
    }

    public void setFileName(String fileName) {
        FileName = fileName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List< SalePosition > getSalePositions() {
        return salePositions;
    }

    public void setSalePositions(List< SalePosition > salePositions) {
        this.salePositions = salePositions;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product", cascade = CascadeType.REMOVE )

    private List<SalePosition> salePositions = new ArrayList<>();

    @Override
    public boolean equals(Object object) {
            Product product = (Product) object;

           boolean result = this.article == product.article &&
                    this.price == product.price &&
                    this.title.equals(product.title) &&
                    this.url.equals(product.url) &&
                    this.parameters.equals(product.parameters) &&
                    this.description.equals(product.description);
           return result;
    }
}



