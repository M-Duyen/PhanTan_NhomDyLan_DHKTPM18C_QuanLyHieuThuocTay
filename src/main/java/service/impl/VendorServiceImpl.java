package service.impl;

import dao.GenericDAO;
import dao.VendorDAO;
import model.Vendor;
import service.VendorService;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class VendorServiceImpl extends GenericServiceImpl<Vendor, String> implements VendorService {

    private final VendorDAO vendorDao;

    public VendorServiceImpl(VendorDAO vendorDAO) throws RemoteException {
        super(vendorDAO);
        this.vendorDao = vendorDAO;
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

    @Override
    public ArrayList<Vendor> getVendorListByCriteriasByCountry(String criterious, ArrayList<Vendor> arrayList) throws RemoteException {
        return vendorDao.getVendorListByCriteriasByCountry(criterious, arrayList);
    }
}
