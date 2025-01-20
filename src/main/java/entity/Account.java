package entity;

import java.util.Objects;

public class Account {
    private String accountID;
    private String password;

    private Manager manager;

    public Account() {
    }

    public Account(String accountID) {
        setAccountID(accountID);
    }

    public Account(String accountID, String password, Manager manager) {
        setAccountID(accountID);
        setPassword(password);
        setManager(manager);
    }

    public String getAccountID() {
        return accountID;
    }

    public void setAccountID(String accountID) {
        if (accountID == null || accountID.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã tài khoản không được rỗng");
        }
        this.accountID = accountID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Mật khẩu không được rỗng");
        }
        this.password = password;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Account other = (Account) obj;
        return Objects.equals(this.accountID, other.accountID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountID);
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountID='" + accountID + '\'' +
                ", password='" + password + '\'' +
                ", manager=" + manager +
                '}';
    }
}
