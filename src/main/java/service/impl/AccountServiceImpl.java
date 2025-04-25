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
}
