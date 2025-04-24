package dao;

import model.Promotion;
import jakarta.persistence.EntityManager;

import java.util.ArrayList;

public class PromotionDAO extends GenericDAO<Promotion, String> implements service.PromotionService {
    public PromotionDAO(EntityManager em, Class<Promotion> entityClass) {
        super(em, entityClass);
    }

    public PromotionDAO(Class<Promotion> clazz) {
        super(clazz);
    }

    /**
     * Lọc danh sách promotion theo status
     *
     * @param status
     * @return
     */
    @Override
    public ArrayList<Promotion> getPromotionListByStatus(boolean status) {
        return null;
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
        return null;
    }

    /**
     * Cập nhật trạng thái của khuyến mãi
     */
    @Override
    public void updatePromotionStatus() {

    }
}