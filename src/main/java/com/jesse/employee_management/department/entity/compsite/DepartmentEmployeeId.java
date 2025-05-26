package com.jesse.employee_management.department.entity.compsite;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentEmployeeId implements Serializable
{
    private Integer employeeId;     // 员工 ID
    private String  departmentNo;    // 员工所在的部门码

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DepartmentEmployeeId departmentEmployeeId = (DepartmentEmployeeId) o;

        return employeeId.equals(departmentEmployeeId.employeeId) &&
               departmentNo.equals(departmentEmployeeId.departmentNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeId, departmentNo);
    }
}
