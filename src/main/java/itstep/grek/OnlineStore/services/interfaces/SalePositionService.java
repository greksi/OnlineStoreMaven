package itstep.grek.OnlineStore.services.interfaces;

import itstep.grek.OnlineStore.Models.SalePosition;

import java.util.Collection;

public interface SalePositionService {

    void add( SalePosition salePosition);


    void update(SalePosition salePosition);

    SalePosition get(Long id);

    Collection<SalePosition> getAll();

    void remove(SalePosition salePosition);


    void remove(Long id);

    void removeAll();

    void quantityDecrement(SalePosition salePosition);

}
