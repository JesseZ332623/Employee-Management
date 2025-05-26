package com.jesse.employee_management.salary.entity;

import com.jesse.employee_management.employee.entity.Employee;
import com.jesse.employee_management.salary.entity.composite.SalariesId;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "salaries")
@IdClass(SalariesId.class)
public class Salaries
{
    @Id
    @Column(name = "emp_no")
    private Integer employeeId;     // 员工 ID

    @Column(name = "salary")
    private Integer salary;         // 员工工资

    @Id
    @Column(name = "from_date", columnDefinition = "DATE")
    private LocalDate fromDate;     // 从何时起

    @Column(name = "from_date", columnDefinition = "DATE")
    private LocalDate toDate;       // 从何时结束（届时会重新评估）

    @ManyToOne
    @JoinColumn(
            name = "emp_no", referencedColumnName = "emp_no",
            insertable = false, updatable = false
    )
    private Employee employee;      // 多个时间段员工工资也有涨跌
}
