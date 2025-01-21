package entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Objects;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "accounts")
public class Account {
    @Id
    @EqualsAndHashCode.Include
    @Column(name = "account_id", nullable = false, unique = true)
    private String accountID;

    private String password;

    @OneToOne
    @JoinColumn(name = "manager_id")
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

    public void setAccountID(String accountID) {
        if (accountID == null || accountID.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã tài khoản không được rỗng");
        }
        this.accountID = accountID;
    }

    public void setPassword(String password) {
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Mật khẩu không được rỗng");
        }
        this.password = password;
    }
}
