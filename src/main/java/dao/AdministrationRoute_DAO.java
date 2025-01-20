package dao;

import entity.AdministrationRoute;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

import java.util.List;

public class AdministrationRoute_DAO {
    private EntityManager em;

    public AdministrationRoute_DAO() {
        em = Persistence.createEntityManagerFactory("mariadb").createEntityManager();
    }

    public List<AdministrationRoute> getAll() {
        return em.createQuery("SELECT ar FROM AdministrationRoute ar").getResultList();
    }

    public boolean create(AdministrationRoute ar) {
        try {
            em.getTransaction().begin();
            em.persist(ar);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }

    public AdministrationRoute read(String id) {
        return em.find(AdministrationRoute.class, id);
    }

    public boolean update(AdministrationRoute ar) {
        try {
            em.getTransaction().begin();
            em.merge(ar);
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
            AdministrationRoute ar = em.find(AdministrationRoute.class, id);
            em.getTransaction().begin();
            em.remove(ar);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        AdministrationRoute_DAO dao = new AdministrationRoute_DAO();
//        Test get all
//        dao.getAll().forEach(ar -> System.out.println(ar.toString()));

//        Test create
//        AdministrationRoute ar = new AdministrationRoute();
//        ar.setAdministrationRouteID("AD-123-test");
//        ar.setAdministrationRouteName("Oral");
//        System.out.println(dao.create(ar));

//        Test read
//        System.out.println(dao.read("AD-123-test"));

//        Test update
//        AdministrationRoute ar = new AdministrationRoute("AD-123-test", "Updated");
//        dao.update(ar);

//        Test delete
        System.out.println(dao.delete("AD-123-test"));
    }
}
