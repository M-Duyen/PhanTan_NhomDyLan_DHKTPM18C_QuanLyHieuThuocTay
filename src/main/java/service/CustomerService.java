package service;

import entity.Customer;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.util.Map;

public interface CustomerService extends GenericService<Customer, String> {
    boolean checkPhoneNumber(String phone) throws RemoteException;
    String createCustomerID() throws RemoteException;
    double getCustomerPoint(String phone) throws RemoteException;
    boolean updateCustPoint_Decrease(String phone, double point) throws RemoteException;
    boolean updateCustPoint_Increase(String phone, double point) throws RemoteException;
    Map<String, Customer> getAllCustomersAsMap() throws RemoteException;
}
