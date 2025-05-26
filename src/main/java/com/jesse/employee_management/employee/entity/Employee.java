package com.jesse.employee_management.employee.entity;

import com.jesse.employee_management.department.entity.DepartmentEmployee;
import com.jesse.employee_management.department.entity.DepartmentManager;
import com.jesse.employee_management.salary.entity.Salaries;
import com.jesse.employee_management.title.entity.Titles;
import com.jesse.employee_management.employee.controller.Gender;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * 员工表实体类。
 */
@Data
@Entity
@Table(name = "employees")
public class Employee
{
    @Id
    @Column(name = "emp_no")
    private Integer     employeeId;     // 员工 ID

    @Column(name = "birth_date", columnDefinition = "DATE")
    private LocalDate   bornDate;       // 员工出生日期

    @Column(name = "first_name", columnDefinition = "VARCHAR(14)")
    private String      firstName;      // 员工名

    @Column(name = "last_name", columnDefinition = "VARCHAR(16)")
    private String      lastName;       // 员工姓

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "gender", columnDefinition = "ENUM('M', 'F')")
    private Gender gender;              // 员工性别

    @Column(name = "hire_date", columnDefinition = "DATE")
    private LocalDate   hireDate;       // 员工入职日期

    /**
     * titles 字段被 OneToMany 注解，这表明一个员工可能会有多个头衔。
     * <br/>
     * <ol>
     *     <li>
     *         mappedBy 属性表明一对多关系由
     *                  Titles 实体的 employee 字段来维护（即外键在 titles 表中）
     *     </li>
     *     <li>
     *         cascade 属性表示级联操作，
     *                 对 Employee 的持久化操作都会自动传播到关联的 Titles 实体。
     *                 这里使用了 ALL 属性，包含了
     *                 <ul>
     *                     <li>PERSIST（保存）</li>
     *                     <li>MERGE（合并）</li>
     *                     <li>REMOVE（删除）</li>
     *                     <li>REFRESH / DETACH（刷新，分离）</li>
     *                 </ul>
     *     </li>
     *     <li>
     *         orphanRemoval 属性表示孤儿删除（什么怪名字？）
     *         当 Titles 实体不再被 Employee 引用时，自动删除该 Titles。
     *     </li>
     * </ol>
     */
    @OneToMany(
            mappedBy      = "employee",
            cascade       = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Titles> titles = new ArrayList<>();

    /**
     * salaries 字段被 OneToMany 注解，
     * 这表明一个员工随时间的推移工资会有涨跌。
     */
    @OneToMany(
            mappedBy      = "employee",
            cascade       = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Salaries> salaries = new ArrayList<>();

    /**
     * 员工中有多个是经理。
     */
    @OneToMany(
            mappedBy      = "employee",
            cascade       = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<DepartmentManager> managers = new ArrayList<>();

    /**
     * 员工可能在多个部门有过任职。
     */
    @OneToMany(
            mappedBy = "employee",
            cascade       = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<DepartmentEmployee> departmentEmployees = new ArrayList<>();
}
