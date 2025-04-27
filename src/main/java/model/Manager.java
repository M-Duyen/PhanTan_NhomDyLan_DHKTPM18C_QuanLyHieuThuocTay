package model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "managers")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Manager implements Serializable {
    @Id
    @Column (name = "manager_id", nullable = false)
    @EqualsAndHashCode.Include
    private String managerID;

    @Column(name = "manager_name")
    private String managerName;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "phone_number")
    private String phoneNumber;

    private boolean gender;

    private String email;

    private String address;

    private boolean status;

    private String degree;

    @OneToMany(mappedBy = "manager", cascade = CascadeType.ALL)
    private List<Account> accounts;





}
