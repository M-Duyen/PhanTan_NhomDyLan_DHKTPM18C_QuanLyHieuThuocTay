package service.impl;

import dao.PromotionTypeDAO;
import model.PromotionType;
import service.PromotionTypeService;

import java.rmi.RemoteException;

public class PromotionTypeServiceImpl extends GenericServiceImpl<PromotionType, String> implements PromotionTypeService {
    private PromotionTypeDAO promotionTypeDAO;


    public PromotionTypeServiceImpl(PromotionTypeDAO promotionTypeDAO) throws RemoteException {
        super(promotionTypeDAO);
        this.promotionTypeDAO = promotionTypeDAO;
    }
}
