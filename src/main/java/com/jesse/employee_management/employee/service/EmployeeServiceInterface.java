package com.jesse.employee_management.employee.service;

import com.jesse.employee_management.employee.dto.EmployeeIDNameDTO;
import com.jesse.employee_management.employee.dto.EmployeeInfoDTO;

import java.util.List;

public interface EmployeeServiceInterface
{
    /**
     * 缓存当前的页面偏移量，
     * 每一次刷新页面都能跳转到上一次浏览的页。
     */
    void saveCurrentOffset(int newOffset);
    int  getCurrentOffset();

    EmployeeInfoDTO
    getEmployeeWholeInfoById(Integer employeeId);

    List<EmployeeIDNameDTO>
    getAllEmployeeIDNameByRange(Integer limit, Integer offset);

    /**
     * 获取员工总数。
     */
    Long getEmployeeAmount();
}
