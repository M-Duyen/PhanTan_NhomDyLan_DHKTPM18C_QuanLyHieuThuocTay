package service.impl;

import dao.GenericDAO;
import dao.PromotionDAO;
import model.Promotion;
import service.PromotionService;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class PromotionImpl extends GenericServiceImpl<Promotion, String> implements PromotionService {
    private PromotionDAO promotionDAO;

    public PromotionImpl(PromotionDAO promotionDAO) throws RemoteException {
        super(promotionDAO);
        this.promotionDAO = promotionDAO;
    }


    @Override
    public ArrayList<Promotion> getPromotionListByStatus(boolean status) throws RemoteException {
        return promotionDAO.getPromotionListByStatus(status);
    }

    @Override
    public String createPromotionID(String startDate, String endDate) throws RemoteException {
        return promotionDAO.createPromotionID(startDate, endDate);
    }

    @Override
    public void updatePromotionStatus() throws RemoteException {
        promotionDAO.updatePromotionStatus();
    }
}
