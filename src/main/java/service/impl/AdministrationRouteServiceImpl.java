package service.impl;

import dao.AdministrationRouteDAO;
import model.AdministrationRoute;
import service.AdministrationRouteService;

import java.rmi.RemoteException;

public class AdministrationRouteServiceImpl extends GenericServiceImpl<AdministrationRoute, String> implements AdministrationRouteService {
    private AdministrationRouteDAO administrationRouteDAO;

    public AdministrationRouteServiceImpl(AdministrationRouteDAO administrationRouteDAO) throws RemoteException {
        super(administrationRouteDAO);
        this.administrationRouteDAO = administrationRouteDAO;
    }
}
