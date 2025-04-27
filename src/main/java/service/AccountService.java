package service;

import model.Account;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface AccountService extends GenericService<Account, String> {
    boolean sendEmail(String to, String title, String content) throws RemoteException;

    String getEmailByAccountID(String accountID) throws RemoteException;

    boolean updatePasswordByAccountID(String accountID, String password) throws RemoteException;

    String containUserName(String userName) throws RemoteException;

    List<String> login(String userName, String pass) throws RemoteException;

    public boolean logout(String accountId) throws RemoteException;

    public boolean loginCheck(String accountId, String password) throws RemoteException;

    public boolean isAccountLoggedIn(String accountId) throws RemoteException;

    public boolean updateLoggedInStatus(String accountId, boolean isLoggedIn) throws RemoteException;

    }
