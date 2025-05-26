package com.jesse.employee_management.employee.service.impl;

import com.jesse.employee_management.employee.dto.EmployeeIDNameDTO;
import com.jesse.employee_management.employee.dto.EmployeeInfoDTO;
import com.jesse.employee_management.employee.repository.EmployeeRepository;
import com.jesse.employee_management.employee.service.EmployeeServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static java.lang.String.format;

@Slf4j
@Service
public class EmployeeService implements EmployeeServiceInterface
{
    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository emp) {
        this.employeeRepository = emp;
    }

    /**
     * <p>通过员工 id 去查找该员工的完整信息</p>
     */
    @Override
    public EmployeeInfoDTO
    getEmployeeWholeInfoById(Integer employeeId)
    {
        try
        {
            return Objects.requireNonNull(
                    this.employeeRepository
                        .getEmployeeWholeInfoById(employeeId)
            );
        }
        catch (NullPointerException exception)
        {
            log.error("Query employee id {} NOT FOUND!", employeeId);

            throw new RuntimeException(
                    format(
                            "Query employee id %d NOT FOUND!",
                            employeeId
                    )
            );
        }
    }

    /**
     * 分页查询所有员工信息。
     *
     * @param limit  每一页几条
     * @param offset 从第几条开始查询（起始为 1）
     */
    @Override
    public List<EmployeeIDNameDTO>
    getAllEmployeeIDNameByRange(Integer limit, Integer offset)
    {
        try
        {
            return Objects.requireNonNull(
                        this.employeeRepository.getAllEmployeeIDNameByRange(
                        limit, offset
                    )
            );
        }
        catch (NullPointerException exception)
        {
            log.error(
                    "Query employee limit = {}, offset = {}, NOT FOUND!",
                    limit, offset
            );

            throw new RuntimeException(
                    format(
                            "Query employee limit = %d, offset = %d, NOT FOUND!",
                            limit, offset
                    )
            );
        }
    }

    @Override
    public Long getEmployeeAmount() {
        return this.employeeRepository.count();
    }
}
