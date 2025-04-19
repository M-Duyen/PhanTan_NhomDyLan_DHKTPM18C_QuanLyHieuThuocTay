package service;

import java.util.List;
import java.util.Map;

public interface GenericService<T, ID> {
    List<T> getAll();
    boolean create(T t);
    T findById(ID id);
    boolean update(T t);
    boolean delete(ID id);
    List<T> searchByMultipleCriteria(Class<T> clazz, Map<String, Object> criteria);

}
