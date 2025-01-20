package entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "managers")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Manager {
    @Id
    @Column (name = "manager_id", nullable = false, columnDefinition = "char(5)")
    @EqualsAndHashCode.Include
    private String managerID;

    @Column(name = "manager_name")
    private String managerName;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "phone_number")
    private String phoneNumber;

    @OneToMany(mappedBy = "manager")
    private List<Account> account;




}
