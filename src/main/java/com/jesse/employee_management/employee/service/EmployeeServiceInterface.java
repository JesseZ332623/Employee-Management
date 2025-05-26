package com.jesse.employee_management.employee.service;

import com.jesse.employee_management.employee.dto.EmployeeIDNameDTO;
import com.jesse.employee_management.employee.dto.EmployeeInfoDTO;

import java.util.List;

public interface EmployeeServiceInterface
{
    EmployeeInfoDTO
    getEmployeeWholeInfoById(Integer employeeId);

    List<EmployeeIDNameDTO>
    getAllEmployeeIDNameByRange(Integer limit, Integer offset);

    /**
     * 获取员工总数。
     */
    Long getEmployeeAmount();
}
