package itstep.grek.OnlineStore.services.impl;

import itstep.grek.OnlineStore.Models.SalePosition;
import itstep.grek.OnlineStore.repository.SalePositionRepository;
import itstep.grek.OnlineStore.services.interfaces.SalePositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

@Service
public class SalePositionServiceImpl implements SalePositionService {

    private SalePositionRepository repository;

    @Autowired
    public SalePositionServiceImpl(SalePositionRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public void add(SalePosition saleposition) {
        if (saleposition!=null) {
            this.repository.save(saleposition);
        }
    }

    @Override
    @Transactional
    public void update(SalePosition salePosition) {
        add(salePosition);
    }


    @Override
    @Transactional(readOnly = true)
    public SalePosition get(Long id) throws NullPointerException {
        Optional<SalePosition> optionalSalePosition = this.repository.findById(id);
        SalePosition salePosition;
        if (optionalSalePosition!=null) {
           salePosition = optionalSalePosition.get();
        }
        else{
            throw new NullPointerException("Can't find model by id " + id + "!");
        }
        return salePosition;
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<SalePosition> getAll() {
        return this.repository.findAll();
    }


    @Override
    @Transactional
    public void remove(SalePosition salePosition) {
        if (salePosition!=null) {
            this.repository.delete(salePosition);
        }
    }

    @Override
    @Transactional
    public void remove(Long id) {
        this.repository.deleteById(id);
    }

    @Override
    @Transactional
    public void removeAll() {
        this.repository.deleteAll();
    }

    @Override
    public void quantityDecrement(SalePosition salePosition) {

    }


}

