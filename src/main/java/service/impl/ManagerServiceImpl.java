package service.impl;

import dao.ManagerDAO;
import model.Manager;
import service.ManagerService;

import java.rmi.RemoteException;

public class ManagerServiceImpl extends GenericServiceImpl<Manager, String> implements ManagerService {
    private ManagerDAO managerDAO;

    public ManagerServiceImpl(ManagerDAO managerDAO) throws RemoteException {
        super(managerDAO);
        this.managerDAO = managerDAO;
    }
}
