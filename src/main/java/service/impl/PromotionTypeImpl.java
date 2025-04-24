package service.impl;

import dao.GenericDAO;
import dao.PromotionTypeDAO;
import model.PromotionType;
import service.PromotionTypeService;

import java.rmi.RemoteException;

public class PromotionTypeImpl extends GenericServiceImpl<PromotionType, String> implements PromotionTypeService {
    private PromotionTypeDAO promotionTypeDAO;


    public PromotionTypeImpl(PromotionTypeDAO promotionTypeDAO) throws RemoteException {
        super(promotionTypeDAO);
        this.promotionTypeDAO = promotionTypeDAO;
    }
}
