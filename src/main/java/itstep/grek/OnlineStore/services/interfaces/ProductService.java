package itstep.grek.OnlineStore.services.interfaces;

import itstep.grek.OnlineStore.Models.Product;

import java.util.Collection;

    public interface ProductService {

        void add(Product product);


        void update(Product product);

        Product get(Long id);

        Collection<Product> getAll();

        void remove(Product product);


        void remove(long id);

        void removeAll();
        Collection<Product> getByCategoryId(long id);

        void removeByCategoryId(long id);

    }
