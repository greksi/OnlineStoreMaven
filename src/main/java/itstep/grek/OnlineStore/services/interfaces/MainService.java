package itstep.grek.OnlineStore.services.interfaces;

import java.util.Collection;

public interface MainService<T> {

    void add(T model);


    void update(T model);

    T get(long id);

    Collection<T> getAll();

    void remove(T model);


    void remove(long id);


    void removeAll();
}
