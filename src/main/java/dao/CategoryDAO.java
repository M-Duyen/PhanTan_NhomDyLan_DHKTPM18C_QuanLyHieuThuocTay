package dao;


import model.Category;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

public class CategoryDAO extends GenericDAO<Category,String> {
    private EntityManager em;

    public CategoryDAO(EntityManager em) {
        super(em, Category.class);
    }

    public static void main(String[] args) {
        EntityManager em = Persistence.createEntityManagerFactory("mariadb").createEntityManager();
        CategoryDAO category_dao = new CategoryDAO(em);



    }

}
