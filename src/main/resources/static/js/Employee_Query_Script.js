/** 
 * 显示指定员工的工资历史平滑的折线图。
 * 
 * @param {string}   name
 * @param {string}   salariesJSON
*/
function showSalariesHistory(name, salariesJSON) 
{
    console.log(salariesJSON);
    const salariesMap = new Map();

    for (const entry of Object.entries(salariesJSON)) {
        salariesMap.set(entry[0], entry[1]);
    }

    var salaryChart = echarts.init(
				document.getElementById('salary_charts')
	);

    // 指定图表的配置项和数据
    option = {
        backgroundColor: '#0d1117',
        title: {
            text: `Salary history of [${name}]`,
            textStyle: {
                color: '#ffffff',
                fontSize: 20
            },
            top: '2%',            // 增加顶部间距
            left: 'center'
        },
        tooltip: {
            backgroundColor: '#161b22',
            borderColor: '#30363d',
            textStyle: {
                color: '#c9d1d9'
            }
        },
        legend: {
            textStyle: {
                color: '#8b949e'
            },
            top: '12%',           // 下移图例位置
            itemGap: 25         // 增加图例项间距
        },
        grid: {                 // 新增grid配置控制绘图区域
            top: '20%',
            bottom: '15%',
            containLabel: true
        },
        xAxis: {
            show: false,
            type: 'category',
            axisLine: {
                lineStyle: {
                    color: '#30363d',
                }
            },
            axisLabel: {
                color: '#8b949e',
                fontSize: 10,     // 缩小字号
                top: '12%'
            },
            data: Array.from(salariesMap.keys())
        },
        yAxis: {
            type: 'value',
            axisLine: {
                lineStyle: {
                    color: '#30363d'
                }
            },
            axisLabel: {
                color: '#8b949e',
                formatter: '${value}'
            },
            splitLine: {
                lineStyle: {
                    color: '#21262d'
                }
            }
        },
        series: [{
            name: 'Salary',
            type: 'line',
            smooth: true,
            symbol: 'circle',
            symbolSize: 8,
            lineStyle: {
                color: '#58a6ff',
                width: 2
            },
            itemStyle: {
                color: '#edede9',
                borderColor: '#0d1117',
                borderWidth: 2
            },
            areaStyle: {
                color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                    offset: 0,
                    color: '#1f6feb66'
                }, {
                    offset: 1,
                    color: '#1f6feb00'
                }])
            },
            data: Array.from(salariesMap.values())
        }]
    };

    salaryChart.setOption(option);
    salaryChart.resize();

	window.addEventListener(
		'resize', 
		function() { salaryChart.resize(); }
	);
}


const clearElement = (id) => {
    document.getElementById(id).innerHTML = '';
};

/*
    通过 queryEmployee() 拿到 JSON，再布置到页面中去，有代表性的 JSON 示例如下：

    {
        "employeeId"	: 10010,
        "bornDate"		: "1963-06-01",
        "firstName"		: "Duangkaew",
        "lastName"		: "Piveteau",
        "gender"		: "F",
        "hireDate"		: "1989-08-24",
        "titles"		: ["Engineer"],
        "departments"	: ["Production", "Quality Management"],
        "salaries": {
            "1996-11-24 -> 1997-11-24": 72488,
            "1997-11-24 -> 1998-11-24": 74347,
            "1998-11-24 -> 1999-11-24": 75405,
            "1999-11-24 -> 2000-11-23": 78194,
            "2000-11-23 -> 2001-11-23": 79580,
            "2001-11-23 -> 9999-01-01": 80324
        },
        "fromDate" : ["1996-11-24", "2000-06-26"],
        "toDate"   : ["2000-06-26","9999-01-01"]
    }
*/
function setInfoToPage(queryJson) 
{
    // 清除所有列表和表格内容
    clearElement('titles_list');
    // clearElement('department_list');
    clearElement('department_timeline');
    clearElement('salary_history_data');

    document.getElementById('employee_id').innerText
        = `${queryJson.employeeId}`;

    document.getElementById('employee_name').innerHTML
        = `<strong>${queryJson.lastName}-${queryJson.firstName}<strong>`;

    document.getElementById('employee_gender').innerText
        = (queryJson.gender === 'M') ? '♂️ Male' : '♀️ Female';

    document.getElementById('employee_born_date').innerText
        = `${queryJson.bornDate}`;

    document.getElementById('employee_hire_date').innerText
        = `${queryJson.hireDate}`;

    const titlesList      = queryJson.titles;
    const departmentsList = queryJson.departments;
    const salariesMap     = queryJson.salaries;
    const fromDateList    = queryJson.fromDate;
    const toDateList      = queryJson.toDate;

    const titlesListElement
        = document.getElementById('titles_list');

    const departmentTimelineElement
        = document.getElementById('department_timeline');

    const salaryHistoryElement
        = document.getElementById('salary_history_data');

    for (const title of titlesList) 
    {
        titlesListElement.insertAdjacentHTML(
            'beforeend', `<li>${title}</li>`
        );
    }

    for (var index = 0; index < fromDateList.length; ++index) 
    {
        let insertHTML;

        if (toDateList[index] != '9999-01-01')
        {
               
            insertHTML = 
                `<tr>
                    <td>${departmentsList[index]}</td>
                    <td><time>${fromDateList[index]}</time></td>
                    <td><time>${toDateList[index]}</time></td>
                </tr>`;
        }
        else 
        {
            insertHTML = 
                `<tr>
                    <td>${departmentsList[index]}</td>
                    <td><time>${fromDateList[index]}</time></td>
                    <td>
                    <time style="color: #238636">Present<time>
                    </td>
                </tr>`;
        }

        departmentTimelineElement.insertAdjacentHTML(
            'beforeend', insertHTML
        );
    }

    for (const [date, salary] of Object.entries(salariesMap)) 
    {
        salaryHistoryElement.insertAdjacentHTML(
            'beforeend',
            `
			<tr>
				<td><time>${date}<time></td> 
				<td><strong>$${salary}</strong></td>
			</tr>`
        );
    }

    showSalariesHistory(`${queryJson.lastName}-${queryJson.firstName}`, salariesMap);
}

function queryEmployee()
{
    const employeeQueryInputElement 
            = document.getElementById('emp_query_input');

    let input = employeeQueryInputElement.value.trim();

    // 如果用户啥也没输入
    if (!input) 
    {
        alert('Enter employee no please~');
        return;
    }

    try 
    {
        // 倘若用户输入的纯数字字符串，则认为他输入的是员工 ID
        if (/^\d+$/.test(input)) {
            queryEmployeeUseNo(input);
        }
        // 如果用户输入的是 last_name-first_name 这样的格式，则认为他输入的是员工姓名
        else if (/^[A-Z][a-zA-Z]*-[a-zA-Z]+$/.test(input)) {
            queryEmployeeUseName(input);
        }
        // 若两者皆不匹配，就提示用户重新输入
        else 
        {
            alert(`Invalid input! ${input} isn't an employee-No. or Name!`);
            employeeQueryInputElement.value = '';
            return;
        }
    }
    catch (error) 
    {
        console.error(error);
        alert(error.message);
    }
}

/**
 * 使用员工号查询。
*/
async function queryEmployeeUseNo(employeeNo)
{
    const response = await fetch(`/api/employee/info/search_by_id/${employeeNo}`);

    if (!response.ok) 
    {
        const errorMessage
            = `Query failed! Status:${response.status}, Error: ${await response.text()}.`;

        console.error(errorMessage);
        alert(errorMessage);

        throw new Error(errorMessage);
     }

    const queryJson = await response.json();

    console.log(queryJson);

    const employeeInfoLinks
        = document.getElementById('employee_info_links');

    employeeInfoLinks.style.display = 'none';

    // 显示并触发动画
    const infoContainer             = document.getElementById('employee_info');
    infoContainer.style.display     = 'block';
    infoContainer.style.animation   = 'none';
    requestAnimationFrame(
        () => {
            infoContainer.style.animation = 'fadeIn 0.4s ease-out forwards';
        }
    );
    
    // 早期触发浏览器重绘的经典操作，现在可以被 requestAnimationFrame(callback) 替代
    // void infoContainer.offsetWidth; // 触发重绘
    // infoContainer.style.animation = 'fadeIn 0.4s ease-out forwards';

    setInfoToPage(queryJson);
}

/**
 * 使用员工姓名查询。
*/
async function queryEmployeeUseName(employeeName) 
{
    const response 
            = await fetch(`/api/employee/info/search_by_name/${employeeName}`);

    if (!response.ok) 
    {
        const errorMessage
                = `Query failed! Status:${response.status}, Error: ${await response.text()}.`;

        console.error(errorMessage);
        alert(errorMessage);

        throw new Error(errorMessage);
    }

    const queryIds = await response.json();

    console.log(queryIds);

    const infoContainer = document.getElementById('employee_info');

    if (infoContainer != null) 
    {
        infoContainer.style.display   = 'none';
        infoContainer.style.animation = 'none';
    }

    const employeeInfoLinks
            = document.getElementById('employee_info_links');
            
    employeeInfoLinks.style.display = 'block';
            
    clearElement('employee_info_links');
            
    for (var empId of queryIds)
    {
        employeeInfoLinks.insertAdjacentHTML(
            'beforeend',
            `<li>
				<a href="/employee/query/${empId}">
					Profile of employee: 
					[${empId}] ${employeeName}
				</a>
			</li>`
        );
    }

    document.getElementById('query_count').innerText
            = `Total ${queryIds.length} query result.`;
}