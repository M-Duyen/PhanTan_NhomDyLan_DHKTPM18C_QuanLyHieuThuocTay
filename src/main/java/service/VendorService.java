package service;

import model.Vendor;

import java.rmi.RemoteException;
import java.util.ArrayList;

public interface VendorService extends GenericService<Vendor, String> {
    String removeAccent(String country) throws RemoteException;
    String getCountryID(String country) throws RemoteException;
    String createVendorID(String country) throws RemoteException;
    ArrayList<Vendor> getVendorListByCriteriasByCountry(String criterious, ArrayList<Vendor> arrayList) throws RemoteException;
}
