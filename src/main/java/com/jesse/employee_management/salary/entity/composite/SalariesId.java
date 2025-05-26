package com.jesse.employee_management.salary.entity.composite;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalariesId implements Serializable
{
    private Integer     employeeId;
    private LocalDate   fromDate;

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SalariesId salariesId = (SalariesId) o;

        return employeeId.equals(salariesId.employeeId) &&
               fromDate.equals(salariesId.fromDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeId, fromDate);
    }
}
