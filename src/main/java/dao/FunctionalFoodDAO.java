package dao;

import entity.FunctionalFood;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

import java.time.LocalDate;
import java.util.List;

public class FunctionalFoodDAO {
    private EntityManager em;

    public FunctionalFoodDAO() {
        em = Persistence.createEntityManagerFactory("mariadb").createEntityManager();
    }

    public List<FunctionalFood> getAll() {
        return em.createQuery("SELECT ff FROM FunctionalFood ff", FunctionalFood.class).getResultList();
    }


    public boolean create(FunctionalFood functionalFood) {
        try {
            em.getTransaction().begin();
            em.persist(functionalFood);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(FunctionalFood functionalFood) {
        try {
            em.getTransaction().begin();
            em.merge(functionalFood);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(FunctionalFood functionalFood) {
        try {
            em.getTransaction().begin();
            FunctionalFood toDelete = em.find(FunctionalFood.class, functionalFood.getProductID());
            if (toDelete != null) {
                em.remove(toDelete);
            }
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }

    public FunctionalFood findById(String id) {
        return em.find(FunctionalFood.class, id);
    }

    public static void main(String[] args) {
        FunctionalFoodDAO functionalFoodDAO = new FunctionalFoodDAO();

        FunctionalFood ff1 = new FunctionalFood();
        ff1.setProductID("FF001");
        ff1.setProductName("Vitamin C Supplement");
        ff1.setRegistrationNumber("REG12345");
        ff1.setPurchasePrice(200.00);
        ff1.setTaxPercentage(5.0);
        ff1.setEndDate(LocalDate.now().plusYears(2));
        ff1.setMainNutrients("Vitamin C");
        ff1.setSupplementaryIngredients("Zinc, Calcium");
        boolean created = functionalFoodDAO.create(ff1);
        System.out.println("Đã thêm: " + created);

        System.out.println("Danh sách Functional Foods:");
        functionalFoodDAO.getAll().forEach(System.out::println);

        ff1.setProductName("Vitamin C Plus");
        boolean updated = functionalFoodDAO.update(ff1);
        System.out.println("Test cập nhật: " + updated);

        FunctionalFood found = functionalFoodDAO.findById("FF001");
        System.out.println("Sản phẩm có max FF001: " + found);

        boolean deleted = functionalFoodDAO.delete(ff1);
        System.out.println("Đã xóa: " + deleted);
    }
}
