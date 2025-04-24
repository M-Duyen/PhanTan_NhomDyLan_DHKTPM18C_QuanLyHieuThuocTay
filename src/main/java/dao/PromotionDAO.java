package dao;

import model.Promotion;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import net.datafaker.Faker;

import java.util.List;

public class PromotionDAO {
    private final EntityManager em;

    public PromotionDAO() {
        em = Persistence.createEntityManagerFactory("mariadb").createEntityManager();
    }

    public List getAllPromotion() {
        return em.createQuery("SELECT p FROM Promotion p").getResultList();
    }

    public boolean insertPromotion(Promotion promotion) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(promotion);
            tx.commit();
            return true;
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
            return false;
        }
    }

    public boolean updatePromotion(Promotion promotion) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(promotion);
            tx.commit();
            return true;
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
            return false;
        }
    }

    public boolean deletePromotion(Promotion promotion) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.remove(promotion);
            tx.commit();
            return true;
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
            return false;
        }
    }

    public Promotion getPromotionById(int id) {
        return em.find(Promotion.class, id);
    }

    public Promotion createSamplePromotion(Faker faker) {
        Promotion promotion = new Promotion();
        promotion.setPromotionId(faker.commerce().promotionCode()/*.substring(0, 1)*/);
        promotion.setPromotionName(faker.commerce().productName());
        return promotion;
    }

    public static void main(String[] args) {
        PromotionDAO dao = new PromotionDAO();
        Faker faker = new Faker();
        Promotion promotion = dao.createSamplePromotion(faker);

        System.out.println(dao.insertPromotion(promotion));
        System.out.println("Result: ");
        dao.getAllPromotion().forEach(System.out::println);
    }
}