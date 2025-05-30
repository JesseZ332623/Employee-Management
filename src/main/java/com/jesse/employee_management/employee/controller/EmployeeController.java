package com.jesse.employee_management.employee.controller;

import com.jesse.employee_management.employee.dto.EmployeeIDNameDTO;
import com.jesse.employee_management.employee.dto.EmployeeInfoDTO;
import com.jesse.employee_management.employee.service.EmployeeServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping(path = "/save_current_offset")
    public ResponseEntity<?>
    saveCurrentOffset(@RequestBody String newOffset)
    {
        try
        {
            this.employeeService.saveCurrentOffset(Integer.parseInt(newOffset));

            return ResponseEntity.ok("Set CURRENT_OFFSET = " + newOffset + ".");
        }
        catch (RuntimeException exception)
        {
            // log.error(exception.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body(exception.getMessage());
        }
    }

    @GetMapping(path = "/info/search_by_id/{employeeId}")
    public ResponseEntity<?>
    getEmployeeWholeInfo(@PathVariable Integer employeeId)
    {
        try
        {
            EmployeeInfoDTO employeeInfoDTO
                        = this.employeeService.getEmployeeWholeInfoById(employeeId);

            return ResponseEntity.ok(employeeInfoDTO);
        }
        catch (RuntimeException exception)
        {
            // log.error(exception.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body(exception.getMessage());
        }
    }

    @GetMapping(path = "/info/search_by_name/{employeeName}")
    public ResponseEntity<?>
    getEmployeeWholeInfosByName(
            @PathVariable
            String employeeName
    )
    {
        try
        {
            List<Integer> employeeIDs
                = this.employeeService.getEmployeeIdByName(employeeName);

            return ResponseEntity.ok(employeeIDs);
        }
        catch (RuntimeException exception)
        {
            // log.error(exception.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body(exception.getMessage());
        }
    }

    @GetMapping(path = "/info/range_info/{limit}_{offset}")
    public ResponseEntity<?>
    getAllEmployeeIDNameByRange(
            @PathVariable Integer limit,
            @PathVariable Integer offset
    )
    {
        //long totalEmployee = this.employeeService.getEmployeeAmount();

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
            // log.error(exception.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body(exception.getMessage());
        }
    }
}
