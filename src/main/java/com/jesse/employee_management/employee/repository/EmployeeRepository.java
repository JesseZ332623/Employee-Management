package com.jesse.employee_management.employee.repository;

import com.jesse.employee_management.employee.dto.EmployeeIDNameDTO;
import com.jesse.employee_management.employee.dto.EmployeeInfoDTO;
import com.jesse.employee_management.employee.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Integer>
{
    /**
     * <p>通过员工 id 去查找该员工的完整信息，包括：</p>
     *
     * <ol>
     *     <li>员工 ID</li>
     *     <li>员工出生日期</li>
     *     <li>员工名</li>
     *     <li>员工姓</li>
     *     <li>员工性别</li>
     *     <li>员工入职日期</li>
     *     <li>员工的头衔</li>
     *     <li>员工不同时间段的薪水</li>
     *     <li>员工所在的部门的信息</li>
     *     <li>员工何时就职某部门</li>
     *     <li>员工何时离开某部门</li>
     * </ol>
     */
    @Query(
            value = """
                    SELECT emp_no 		AS employeeId,
                    	   birth_date	AS bornDate,
                           first_name	AS firstName,
                           last_name    AS lastName,
                           gender		AS gender,
                           hire_date    AS hireDate,
                           GROUP_CONCAT(DISTINCT tl.title     ORDER BY tl.title     SEPARATOR ', ')	AS titles,
                           GROUP_CONCAT(DISTINCT dp.dept_name ORDER BY dp.dept_name SEPARATOR ', ') AS departments,
                           GROUP_CONCAT(
                                DISTINCT CONCAT(sa.from_date, ' -> ', sa.to_date, ': ', sa.salary)
                                ORDER BY sa.salary SEPARATOR ', '
                           ) AS salaries,
                           GROUP_CONCAT(DISTINCT de.from_date ORDER BY de.from_date SEPARATOR ', ') AS fromDate,
                           GROUP_CONCAT(DISTINCT de.to_date   ORDER BY de.to_date   SEPARATOR ', ') AS toDate
                    FROM employees         AS ep
                    INNER JOIN titles      AS tl USING(emp_no)
                    INNER JOIN salaries    AS sa USING(emp_no)
                    INNER JOIN dept_emp    AS de USING(emp_no)
                    INNER JOIN departments AS dp USING(dept_no)
                    WHERE ep.emp_no = :employeeId
                    GROUP BY employeeId
                    """,
            nativeQuery = true
    )
    EmployeeInfoDTO
    getEmployeeWholeInfoById(
       @Param(value = "employeeId") Integer employeeId
    );

    /**
     * <p>分页查找所有员工的 id 和 姓名。</p>
     */
    @Query(
            value = """
                    SELECT emp_no, first_name, last_name
                    FROM employees
                    LIMIT :limit OFFSET :offset
                    """,
            nativeQuery = true
    )
    List<EmployeeIDNameDTO>
    getAllEmployeeIDNameByRange(
            @Param(value = "limit")  Integer limit,
            @Param(value = "offset") Integer offset
    );
}

