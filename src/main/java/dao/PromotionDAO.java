package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import model.Promotion;
import service.PromotionService;

import java.util.ArrayList;
import java.util.List;

public class PromotionDAO extends GenericDAO<Promotion, String> implements PromotionService {
    public PromotionDAO(EntityManager em, Class<Promotion> entityClass) {
        super(em, entityClass);
    }

    public PromotionDAO(Class<Promotion> clazz) {
        super(clazz);
    }

    /**
     * Cập nhật trạng thái của khuyến mãi
     */
    @Override
    public boolean updatePromotionStatus() {
        String jpql = "UPDATE Promotion p " +
                "SET p.status = false " +
                "WHERE p.endDate < CURRENT_DATE " +
                "AND p.status = true";

        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            int updatedCount = em.createQuery(jpql).executeUpdate();
            transaction.commit();
            return updatedCount > 0; // Trả về true nếu có bản ghi bị cập nhật
        } catch (Exception e) {
            if (transaction.isActive()) transaction.rollback();
            throw new RuntimeException("Lỗi khi cập nhật trạng thái khuyến mãi", e);
        }
    }
    /**
     * Lọc khuyến mãi theo tiêu chí bất kỳ
     *
     * @param criterious
     * @return
     */
    @Override
    public ArrayList<Promotion> getPromotionListByCriterous(boolean criterious, ArrayList<Promotion> proList) {

        ArrayList<Promotion> promotionListByCriterous = new ArrayList<>();
        ArrayList<Promotion> promotionList = proList;
        for (Promotion promotion : promotionList) {
            if (promotion.isStatus() == criterious) {
                promotionListByCriterous.add(promotion);
            }
        }

        return promotionListByCriterous;
    }

    /**
     * Lọc danh sách promotion theo status
     *
     * @param status
     * @return
     */
    @Override
    public List<Promotion> getPromotionListByStatus(boolean status) {
        String jpql = "SELECT p FROM Promotion p WHERE p.status = :status";
        return em.createQuery(jpql, Promotion.class)
                .setParameter("status", status)
                .getResultList();

    }

    /**
     * Tạo mã khuyến mãi tự động
     *
     * @param startDate
     * @param endDate
     * @return
     */

    @Override
    public String createPromotionID(String startDate, String endDate) {
        String prefix = "PR";
        String basePattern = prefix + startDate + endDate;

        // JPQL không hỗ trợ SUBSTRING nâng cao như SQL nên dùng LIKE
        String jpql = "SELECT p.id FROM Promotion p WHERE p.id LIKE :pattern";

        List<String> promotionIDs = em.createQuery(jpql, String.class)
                .setParameter("pattern", basePattern + "%")
                .getResultList();

        // Tìm giá trị số cuối lớn nhất từ danh sách
        int currentMax = promotionIDs.stream()
                .map(id -> id.substring(14))               // lấy phần đuôi (vị trí 15 trở đi)
                .filter(suffix -> suffix.matches("\\d+")) // chỉ lấy số
                .mapToInt(Integer::parseInt)
                .max()
                .orElse(0);

        int nextId = currentMax + 1;
        return basePattern + String.format("%02d", nextId);
    }
}