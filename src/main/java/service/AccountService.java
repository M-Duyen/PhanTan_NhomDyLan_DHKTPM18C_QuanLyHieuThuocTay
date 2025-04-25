package service;

import model.Account;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface AccountService extends GenericService<Account, String>, Remote {
    String getEmailByAccountID(String accountID) throws RemoteException;
    boolean updatePasswordByAccountID(String accountID, String password) throws RemoteException;
    String containUserName(String userName) throws RemoteException;
    List<String> login(String userName, String pass) throws RemoteException;
}
