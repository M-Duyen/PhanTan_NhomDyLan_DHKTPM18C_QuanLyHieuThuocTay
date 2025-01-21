package dao;

import java.util.List;

public interface CRUD <T, ID>{
    List<T> getAll();
    T create(T t);
    T read(ID id);
    T update(T t);
    boolean delete(ID id);
}
