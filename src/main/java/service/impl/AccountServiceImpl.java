package service.impl;

import dao.AccountDAO;
import dao.GenericDAO;
import model.Account;
import service.AccountService;

import java.rmi.RemoteException;
import java.util.List;

public class AccountServiceImpl extends GenericServiceImpl<Account, String> implements AccountService {
    private AccountDAO accountDAO;


    public AccountServiceImpl(AccountDAO accountDAO) throws RemoteException {
        super(accountDAO);
        this.accountDAO = accountDAO;
    }

    @Override
    public boolean sendEmail(String to, String title, String content) throws RemoteException {
        return accountDAO.sendEmail(to, title, content);
    }

    @Override
    public String getEmailByAccountID(String accountID) throws RemoteException {
        return accountDAO.getEmailByAccountID(accountID);
    }

    @Override
    public boolean updatePasswordByAccountID(String accountID, String password) throws RemoteException {
        return accountDAO.updatePasswordByAccountID(accountID, password);
    }

    @Override
    public String containUserName(String userName) throws RemoteException {
        return accountDAO.containUserName(userName);
    }

    @Override
    public List<String> login(String userName, String pass) throws RemoteException {
        return accountDAO.login(userName, pass);
    }

    @Override
    public boolean logout(String accountId) throws RemoteException {
        return accountDAO.logout(accountId);
    }

    @Override
    public boolean loginCheck(String accountId, String password) throws RemoteException {
        return accountDAO.loginCheck(accountId, password);
    }

    @Override
    public boolean isAccountLoggedIn(String accountId) throws RemoteException {
        return accountDAO.isAccountLoggedIn(accountId);
    }

    @Override
    public boolean updateLoggedInStatus(String accountId, boolean isLoggedIn) throws RemoteException {
        return accountDAO.updateLoggedInStatus(accountId, isLoggedIn);
    }
}
