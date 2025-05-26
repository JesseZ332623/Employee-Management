package com.jesse.employee_management.department.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

/**
 * 部门表实体类。
 */
@Data
@Entity
@Table(name = "departments")
public class Departments
{
    @Id
    @Column(name = "dept_no", columnDefinition = "CHAR(4)")
    private String departmentNo;        // 部门码

    @Column(name = "dept_name", columnDefinition = "VARCHAR(40)")
    private String departmentName;      // 部门名

    /**
     * 同一个部门会有多名经理。
     */
    @OneToMany(
            mappedBy      = "departments",
            cascade       = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<DepartmentManager> departmentManagers;

    /**
     * 同一个部门会有多名员工。
     */
    @OneToMany(
            mappedBy      = "departments",
            cascade       = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<DepartmentEmployee> departmentEmployees;
}
