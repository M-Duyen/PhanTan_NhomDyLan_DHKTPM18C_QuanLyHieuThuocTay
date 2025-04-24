package dao;

import model.Promotion;
import jakarta.persistence.EntityManager;

public class PromotionDAO extends GenericDAO<Promotion, String> {
    public PromotionDAO(EntityManager em, Class<Promotion> entityClass) {
        super(em, entityClass);
    }

    public PromotionDAO(Class<Promotion> clazz) {
        super(clazz);
    }

    public static void main(String[] args) {
        PromotionDAO promotionDAO = new PromotionDAO(Promotion.class);
        Faker faker = new Faker();
        Promotion promotion = promotionDAO.createSamplePromotion(faker);

        System.out.println(promotionDAO.create(promotion));
        System.out.println("Result: ");

        promotionDAO.getAll().forEach(System.out::println);
    }
}