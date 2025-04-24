package service.impl;

import dao.FunctionalFoodDAO;
import dao.GenericDAO;
import model.FunctionalFood;
import service.FunctionalFoodService;

import java.rmi.RemoteException;

public class FunctionalFoodImpl extends GenericServiceImpl<FunctionalFood, String> implements FunctionalFoodService {
    private FunctionalFoodDAO foodDAO;

    public FunctionalFoodImpl(FunctionalFoodDAO foodDAO) throws RemoteException {
        super(foodDAO);
        this.foodDAO = foodDAO;
    }
}
