package entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "employees")
public class Employee {
    //TODO:
    @Id
    @EqualsAndHashCode.Include
    @Column(name = "employee_id")
    private String employeeID;

    @Column(name = "employee_name")
    private String employeeName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    private boolean gender;

    private String email;

    private String address;

    private boolean status;

    private String degree;

    @OneToOne(mappedBy = "employee")
    private Account account;


}
