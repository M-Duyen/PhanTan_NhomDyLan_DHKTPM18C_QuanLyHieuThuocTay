package service.impl;

import dao.GenericDAO;
import dao.VendorDAO;
import entity.Vendor;
import service.VendorService;

import java.rmi.RemoteException;

public class VendorServiceImpl extends GenericServiceImpl<Vendor, String> implements VendorService {

    private VendorDAO vendorDao;

    public VendorServiceImpl(GenericDAO<Vendor, String> genericDAO) throws RemoteException {
        super(genericDAO);
    }


    @Override
    public String removeAccent(String country) throws RemoteException {
        return vendorDao.removeAccent(country);
    }

    @Override
    public String getCountryID(String country) throws RemoteException {
        return vendorDao.getCountryID(country);
    }

    @Override
    public String createVendorID(String country) throws RemoteException {
        return vendorDao.createVendorID(country);
    }
}
