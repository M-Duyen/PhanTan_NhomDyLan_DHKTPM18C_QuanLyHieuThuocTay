package dao;


import model.Category;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import model.Customer;
import service.CategoryService;

public class CategoryDAO extends GenericDAO<Category,String> implements CategoryService {


    public CategoryDAO(Class<Category> clazz) {
        super(clazz);
    }
    public CategoryDAO(EntityManager em, Class<Category> clazz){
        super(em, clazz);
    }

    public CategoryDAO(EntityManager em) {
        super(em, Category.class);
    }

    public static void main(String[] args) {
        EntityManager em = Persistence.createEntityManagerFactory("mariadb").createEntityManager();
        CategoryDAO category_dao = new CategoryDAO(em);

    }
}
