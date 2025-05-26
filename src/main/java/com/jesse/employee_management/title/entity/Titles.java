package com.jesse.employee_management.title.entity;

import com.jesse.employee_management.employee.entity.Employee;
import com.jesse.employee_management.title.entity.composite.TitleId;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

/**
 * <p>
 *     员工头衔表，
 *     本表包含 1 个外键和 2 个主键，如下所示：
 * </p>
 * <ul>
 *     <li>外键：employeeId</li>
 *     <li>主键：title，fromDate</li>
 * </ul>
 *
 * <p>因此我需要 TitleId 这个复合主键类</p>
 */
@Data
@Entity
@Table(name = "titles")
@IdClass(TitleId.class)
public class Titles
{
    @Id
    @Column(name = "emp_no")
    private Integer employeeId; // 员工 ID

    @Id
    @Column(name = "title", columnDefinition = "VARCHAR(50)")
    private String title;   // 员工的其中一个头衔（若他有多个头衔的话）

    @Id
    @Column(name = "from_date", columnDefinition = "DATE")
    private LocalDate fromDate;     // 头衔授予时间

    @Column(name = "to_date", columnDefinition = "DATE")
    private LocalDate toDate;       // 头衔到期时间（届时会重新评估）

    @ManyToOne
    @JoinColumn(
            name = "emp_no", referencedColumnName = "emp_no",
            insertable = false, updatable = false
    )
    private Employee employee;      // 多个头衔会属于一个员工
}
