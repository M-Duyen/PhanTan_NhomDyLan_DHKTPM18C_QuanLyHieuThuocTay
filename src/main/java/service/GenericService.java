package service;

import java.util.List;

public interface GenericService<T, ID> {
    List<T> getAll();
    boolean create(T t);
    T findById(ID id);
    boolean update(T t);
    boolean delete(ID id);
}
