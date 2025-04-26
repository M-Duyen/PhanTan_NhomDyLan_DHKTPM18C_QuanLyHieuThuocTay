package dao;


import model.Category;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import service.CategoryService;
import utils.JPAUtil;

public class CategoryDAO extends GenericDAO<Category,String> implements CategoryService {

    public CategoryDAO(Class<Category> clazz) {
        super(clazz);
        this.em = JPAUtil.getEntityManager();


    }

    public CategoryDAO(EntityManager em, Class<Category> clazz) {
        super(em, clazz);
    }
}
