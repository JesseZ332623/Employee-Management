package com.jesse.employee_management.title.entity.composite;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TitleId implements Serializable
{
    private Integer     employeeId;
    private String      title;
    private LocalDate   fromDate;

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TitleId titleId = (TitleId) o;

        return employeeId.equals(titleId.employeeId) &&
               title.equals(titleId.title)           &&
               fromDate.equals(titleId.fromDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeId, title, fromDate);
    }
}
