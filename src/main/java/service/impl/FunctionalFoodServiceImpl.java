package service.impl;

import dao.FunctionalFoodDAO;
import model.FunctionalFood;
import service.FunctionalFoodService;

import java.rmi.RemoteException;

public class FunctionalFoodServiceImpl extends GenericServiceImpl<FunctionalFood, String> implements FunctionalFoodService {
    private FunctionalFoodDAO foodDAO;

    public FunctionalFoodServiceImpl(FunctionalFoodDAO foodDAO) throws RemoteException {
        super(foodDAO);
        this.foodDAO = foodDAO;
    }
}
