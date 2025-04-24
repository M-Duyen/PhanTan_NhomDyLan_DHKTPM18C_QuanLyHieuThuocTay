package service.impl;

import dao.GenericDAO;
import dao.ManagerDAO;
import model.Manager;
import service.ManagerService;

import java.rmi.RemoteException;

public class ManagerImpl extends GenericServiceImpl<Manager, String> implements ManagerService {
    private ManagerDAO managerDAO;

    public ManagerImpl(ManagerDAO managerDAO) throws RemoteException {
        super(managerDAO);
        this.managerDAO = managerDAO;
    }
}
