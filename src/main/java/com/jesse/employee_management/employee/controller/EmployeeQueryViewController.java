package com.jesse.employee_management.employee.controller;

import com.jesse.employee_management.employee.service.EmployeeServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping(path = "/employee")
public class EmployeeQueryViewController
{
    private final EmployeeServiceInterface employeeService;

    @Autowired
    public EmployeeQueryViewController(EmployeeServiceInterface emp) {
        this.employeeService = emp;
    }

    @GetMapping(path = "/query")
    String employeeQueryPage() {
        return "Employee_Query";
    }

    @GetMapping(path = "/query/{employeeId}")
    String specifiedEmployeeQueryPage(
            Model model,
            @PathVariable Integer employeeId
    )
    {
        try
        {
            model.addAttribute(
                    "EmployeeInfo",
                    this.employeeService.getEmployeeWholeInfoById(employeeId)
            );
        }
        catch (RuntimeException exception)
        {
            log.error(exception.getMessage());
        }

        return "Specified_Employee_Query";
    }

    @GetMapping(path = "/pagination_query")
    String employeeQueryByRangePage(Model model)
    {
        // 获取员工总数
        model.addAttribute(
                "EmployeeAmount",
                this.employeeService.getEmployeeAmount()
        );

        model.addAttribute(
                "CurrentOffset",
                this.employeeService.getCurrentOffset()
        );

        return "Employee_Pagination_Query";
    }
}
