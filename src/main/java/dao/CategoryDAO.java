package dao;


import model.Category;
import jakarta.persistence.EntityManager;
import service.CategoryService;

public class CategoryDAO extends GenericDAO<Category,String> implements CategoryService {

    public CategoryDAO(Class<Category> clazz) {
        super(clazz);
    }

    public CategoryDAO(EntityManager em, Class<Category> clazz) {
        super(em, clazz);
    }
}
