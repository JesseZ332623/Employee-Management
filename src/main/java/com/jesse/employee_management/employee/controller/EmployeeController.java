package com.jesse.employee_management.employee.controller;

import com.jesse.employee_management.employee.dto.EmployeeIDNameDTO;
import com.jesse.employee_management.employee.dto.EmployeeInfoDTO;
import com.jesse.employee_management.employee.service.EmployeeServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/api/employee")
public class EmployeeController
{
    private final EmployeeServiceInterface employeeService;

    @Autowired
    public EmployeeController(EmployeeServiceInterface emp) {
        this.employeeService = emp;
    }

    @GetMapping(path = "/total_amount")
    public ResponseEntity<?>
    getEmployeeAmount() {
        return ResponseEntity.ok(this.employeeService.getEmployeeAmount());
    }

    @GetMapping(path = "/info/{employeeId}")
    public ResponseEntity<?>
    getEmployeeWholeInfo(@PathVariable Integer employeeId)
    {
        try
        {
            EmployeeInfoDTO employeeInfoDTO
                        = this.employeeService
                              .getEmployeeWholeInfoById(employeeId);

            return ResponseEntity.ok(employeeInfoDTO);
        }
        catch (RuntimeException exception)
        {
            log.error(exception.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body(exception.getMessage());
        }
    }

    @GetMapping(path = "/range_info/{limit}_{offset}")
    public ResponseEntity<?>
    getAllEmployeeIDNameByRange(
            @PathVariable Integer limit,
            @PathVariable Integer offset
    )
    {
        try
        {
            List<EmployeeIDNameDTO> employeeInfos
                    = this.employeeService.getAllEmployeeIDNameByRange(
                        limit, offset
                );

            return ResponseEntity.ok(employeeInfos);
        }
        catch (RuntimeException exception)
        {
            log.error(exception.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(exception.getMessage());
        }
    }
}
