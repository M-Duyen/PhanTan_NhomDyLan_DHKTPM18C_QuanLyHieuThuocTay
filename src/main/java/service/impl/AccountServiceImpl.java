package service.impl;

import dao.AccountDAO;
import dao.GenericDAO;
import model.Account;
import service.AccountService;

import java.rmi.RemoteException;

public class AccountServiceImpl extends GenericServiceImpl<Account, String> implements AccountService {
    private AccountDAO accountDAO;

    public AccountServiceImpl(GenericDAO<Account, String> genericDAO) throws RemoteException {
        super(genericDAO);
    }

    @Override
    public String getEmailByAccountID(String accountID) throws RemoteException {
        return accountDAO.getEmailByAccountID(accountID);
    }

    @Override
    public boolean updatePasswordByAccountID(String accountID, String password) throws RemoteException {
        return accountDAO.updatePasswordByAccountID(accountID, password);
    }
}
