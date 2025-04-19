package dao;

import entity.Category;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import net.datafaker.Faker;

import java.util.List;

public class Category_DAO {
    private EntityManager em;

    public Category_DAO() {
        em = Persistence.createEntityManagerFactory("mariadb").createEntityManager();
    }

    public List<Category> getAll() {
        return em.createQuery("SELECT c FROM Category c").getResultList();
    }

    public boolean create(Category category) {
        try {
            em.getTransaction().begin();
            em.persist(category);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }
    public Category read(String id) {
        return em.find(Category.class, id);
    }

    public boolean update(Category category) {
        try {
            em.getTransaction().begin();
            em.merge(category);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(String id) {
        try {
            Category category = em.find(Category.class, id);
            em.getTransaction().begin();
            em.remove(category);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }


    public static void main(String[] args) {
        Category_DAO category_dao = new Category_DAO();

//        test getAll
//        category_dao.getAll().forEach(System.out::println);

//        test create
//        category_dao.create(new Category("C-1", "Category 1"));

//        test read
//        System.out.println(category_dao.read("C-1"));

//        test update
//        category_dao.update(new Category("C-1", "Category 1 updated"));

//        test delete
        category_dao.delete("C-1");
    }

}
