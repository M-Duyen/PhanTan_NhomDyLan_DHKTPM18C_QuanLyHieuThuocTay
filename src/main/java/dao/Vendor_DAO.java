package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import entity.Vendor;

import java.util.List;

public class Vendor_DAO {
    private EntityManager em;

    public Vendor_DAO() {
        em = Persistence.createEntityManagerFactory("mariadb").createEntityManager();
    }

    public List<Vendor> getAll() {
        return em.createQuery("SELECT v FROM Vendor v").getResultList();
    }
    public boolean create(Vendor vendor) {
        try {
            em.getTransaction().begin();
            em.persist(vendor);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }
    public Vendor read(String id) {
        return em.find(Vendor.class, id);
    }
    public boolean update(Vendor vendor) {
        try {
            em.getTransaction().begin();
            em.merge(vendor);
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
            Vendor vendor = em.find(Vendor.class, id);
            em.getTransaction().begin();
            em.remove(vendor);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        Vendor_DAO vendor_dao = new Vendor_DAO();
//        vendor_dao.getAll().forEach(System.out::println);

//        Vendor vendor = new Vendor("V-123", "ABC", "VietNam");
//        vendor_dao.create(vendor);

//        System.out.println(vendor_dao.read("V-123"));
//        vendor.setCountry("USA");
//        vendor_dao.update(vendor);

        vendor_dao.delete("V-123");
    }
}
