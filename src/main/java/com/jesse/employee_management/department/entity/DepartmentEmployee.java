package com.jesse.employee_management.department.entity;

import com.jesse.employee_management.employee.entity.Employee;
import com.jesse.employee_management.department.entity.compsite.DepartmentEmployeeId;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

/**
 * 普通员工于部门的关系表实体类。
 */
@Data
@Entity
@Table(name = "dept_emp")
@IdClass(value = DepartmentEmployeeId.class)
public class DepartmentEmployee
{
    @Id
    @Column(name = "emp_no")
    private Integer employeeId;     // 员工 ID

    @Id
    @Column(name = "dept_no", columnDefinition = "CHAR(4)")
    private String departmentNo;    // 员工所在的部门码

    @Column(name = "from_date", columnDefinition = "DATE")
    private LocalDate fromDate;     // 就任时间

    @Column(name = "to_date", columnDefinition = "DATE")
    private LocalDate toDate;       // 卸任时间（届时会重新评估资格）

    /**
     * 多个部门可能有同一个员工工作的声影。
    */
    @ManyToOne
    @JoinColumn(
            name = "emp_no", referencedColumnName = "emp_no",
            insertable = false, updatable = false
    )
    private Employee employee;

    /**
     * 多个员工可能隶属于同一个部门。
     */
    @ManyToOne
    @JoinColumn(
            name = "dept_no", referencedColumnName = "dept_no",
            insertable = false, updatable = false
    )
    private Departments departments;
}
