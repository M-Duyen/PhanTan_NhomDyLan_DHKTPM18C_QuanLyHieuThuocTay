package service;

import model.Account;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface AccountService extends GenericService<Account, String> {
    String getEmailByAccountID(String accountID) throws RemoteException;
    boolean updatePasswordByAccountID(String accountID, String password) throws RemoteException;
}
