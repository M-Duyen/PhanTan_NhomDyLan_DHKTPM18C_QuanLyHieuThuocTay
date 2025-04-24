package service;

import model.Customer;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Map;

public interface CustomerService extends GenericService<Customer, String> {
    boolean checkPhoneNumber(String phone) throws RemoteException;
    String createCustomerID() throws RemoteException;
    Customer getCustomerByPhone(String phone) throws RemoteException;
    double getCustomerPoint(String phone) throws RemoteException;
    boolean updateCustPoint_Decrease(String phone, double point) throws RemoteException;
    boolean updateCustPoint_Increase(String phone, double point) throws RemoteException;
    Map<String, Customer> getAllCustomersAsMap() throws RemoteException;
}
