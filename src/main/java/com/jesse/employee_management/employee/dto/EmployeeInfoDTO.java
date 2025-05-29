package com.jesse.employee_management.employee.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.*;

/**
 * 员工的详细信息 DTO。
 */
@Data
@NoArgsConstructor
public class EmployeeInfoDTO
{
    private Integer     employeeId;     // 员工 ID
    private Date        bornDate;       // 员工出生日期
    private String      firstName;      // 员工名
    private String      lastName;       // 员工姓
    private Character   gender;         // 员工性别
    private Date        hireDate;       // 员工入职日期
    private List<String>            titles;         // 员工的所有头衔
    private List<String>            departments;    // 员工所工作的部门（可能为多个按 , 分隔）
    private Map<String, Integer>    salaries;       // 员工工资历史（随时间推移由高到低）
    private List<Date>              fromDate;       // 员工何时就职某部门（可能为多个按 , 分隔）
    private List<Date>              toDate;         // 员工何时离开某部门（可能为多个按 , 分隔）

    /**
     * 需要手写构造函数，完成从字符串到容器的转换。
     */
    public EmployeeInfoDTO(
            Integer employeeNo,
            Date bornDate, String firstName, String lastName,
            Character gender, Date hireDate,
            String titles, String departments, String salaries,
            String fromDate, String toDate
    )
    {
        // 前面的几个字段无需转换，直接赋值即可
        this.employeeId = employeeNo;
        this.bornDate   = bornDate;
        this.firstName  = firstName;
        this.lastName   = lastName;
        this.gender     = gender;
        this.hireDate   = hireDate;

        this.titles      = this.splitString(titles);
        this.departments = this.splitString(departments);
        this.salaries    = this.parseSalaries(salaries);
        this.fromDate    = this.praseDateStrToList(fromDate);
        this.toDate      = this.praseDateStrToList(toDate);
    }

    /**
     * <p>按照 spliteNote 分割字符串 src，存入一列表后返回。</p>
     *
     * <p>
     *     比如把字符串：<br/>
     *     "Production, Quality Management" <br/>
     *     分割成列表：<br/>
     *     [Production, Quality Management]
     * </p>
     */
    private List<String>
    splitString(String src)
    {
        if (src == null || src.isEmpty()) {
            return Collections.emptyList();
        }

        return Arrays.asList(src.split(", "));
    }

    /**
     * <p> 将时间：工资字符串转化成一个 Map。</p>
     *
     * <p>
     *     比如把字符串：
     *     <pre>
     *     "1990-05-20 -> 1991-05-20: 61848, 1991-05-20 -> 1992-05-19: 63700"
     *     </pre>
     *
     *     分割成散列表：
     *     <pre>
     *     {
     *         {"1990-05-20 -> 1991-05-20" : 61848},
     *         {"1991-05-20 -> 1992-05-19" : 63700}
     *     }
     *     </pre>
     * </p>
     *
     */
    private Map<String, Integer> parseSalaries(String src)
    {
        Map<String, Integer> salaries = new TreeMap<>();

        if (src != null && !src.isEmpty())
        {
            Arrays.stream(src.split(", "))
                  .forEach(
                            (subStr) -> {
                                String[] parts = subStr.split(": ");
                                if (parts.length == 2)
                                {
                                    String  timeRange = parts[0].trim();
                                    int     salary    = Integer.parseInt(parts[1]);

                                    salaries.put(timeRange, salary);
                                }
                            }
                  );
        }

        return salaries;
    }

    /**
     * <p>将多个日期组成的字符串转化成日期列表。</p>
     *
     * <p>
     *     比如把字符串：              <br/>
     *     "1996-11-24, 2000-06-26"   <br/>
     *     分割成列表：                <br/>
     *     List{@literal <java.sql.Date>}[1996-11-24, 2000-06-26]
     * </p>
     */
    private List<Date>
    praseDateStrToList(String src)
    {
        List<Date> dates = new ArrayList<>();

        if (src != null && !src.isEmpty())
        {
            Arrays.stream(src.split(", "))
                  .map(String::trim)
                  .map(java.sql.Date::valueOf)
                  .forEach(dates::add);
        }

        return dates;
    }
}
