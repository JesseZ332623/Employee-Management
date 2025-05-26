package com.jesse.employee_management.employee.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeIDNameDTO
{
    private Integer     employeeId;     // 员工 ID
    private String      firstName;      // 员工名
    private String      lastName;       // 员工姓
}
