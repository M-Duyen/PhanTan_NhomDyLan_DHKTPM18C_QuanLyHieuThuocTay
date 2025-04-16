package service.impl;

import java.util.List;

public interface GenericService<T, ID> {
    List<T> getAll();
    boolean create(T t);
    T read(ID id);
    boolean update(T t);
    boolean delete(ID id);
}
