package com.jesse.employee_management.department.entity.compsite;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartMentManagerId implements Serializable
{
    private Integer employeeId;
    private String departmentNo;

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DepartMentManagerId departMentManagerId
                                = (DepartMentManagerId) o;

        return employeeId.equals(departMentManagerId.employeeId) &&
               departmentNo.equals(departMentManagerId.departmentNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeId, departmentNo);
    }
}
