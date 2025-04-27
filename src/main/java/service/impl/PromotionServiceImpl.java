package service.impl;

import dao.PromotionDAO;
import model.Promotion;
import service.PromotionService;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class PromotionServiceImpl extends GenericServiceImpl<Promotion, String> implements PromotionService {
    private PromotionDAO promotionDAO;

    public PromotionServiceImpl(PromotionDAO promotionDAO) throws RemoteException {
        super(promotionDAO);
        this.promotionDAO = promotionDAO;
    }


    @Override
    public List<Promotion> getPromotionListByStatus(boolean status) throws RemoteException {
        return promotionDAO.getPromotionListByStatus(status);
    }

    @Override
    public String createPromotionID(String startDate, String endDate) throws RemoteException {
        return promotionDAO.createPromotionID(startDate, endDate);
    }

    @Override
    public ArrayList<Promotion> getPromotionListByCriterous(boolean criterious, ArrayList<Promotion> proList) throws RemoteException {
        return promotionDAO.getPromotionListByCriterous(criterious, proList);
    }

    @Override
    public boolean updatePromotionStatus() throws RemoteException {
       return promotionDAO.updatePromotionStatus();
    }
}
