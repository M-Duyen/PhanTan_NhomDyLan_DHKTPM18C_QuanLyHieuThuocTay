package service;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

public interface GenericService<T, ID> {
    List<T> getAll();
    boolean create(T t);
    T findById(ID id);
    boolean update(T t);
    boolean delete(ID id);
    @GetMapping("/search-entity")
    public List<?> searchByMultipleCriteria(@RequestParam String entityName, @RequestParam String keyword);

}
