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
    const clearElement = (id) => {
        document.getElementById(id).innerHTML = '';
    };

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

    const titlesList = queryJson.titles;
    const departmentsList = queryJson.departments;
    const salariesMap = queryJson.salaries;
    const fromDateList = queryJson.fromDate;
    const toDateList = queryJson.toDate;

    const titlesListElement
        = document.getElementById('titles_list');

    const departmentTimelineElement
        = document.getElementById('department_timeline');

    const salaryHistoryElement
        = document.getElementById('salary_history_data');

    for (const title of titlesList) {
        titlesListElement.insertAdjacentHTML(
            'beforeend', `<li>${title}</li>`
        );
    }

    for (var index = 0; index < fromDateList.length; ++index) {
        let toDate = toDateList[index];

        departmentTimelineElement.insertAdjacentHTML(
            'beforeend',
            `
					<tr>
						<td>${departmentsList[index]}</td>
						<td><time>${fromDateList[index]}</time></td>
						<td><time>
							${(toDate === '9999-01-01' ? 'Present' : toDate)}
						</time></td>
					</tr>`
        );
    }

    for (const [date, salary] of Object.entries(salariesMap)) {
        salaryHistoryElement.insertAdjacentHTML(
            'beforeend',
            `
					<tr>
						<td><time>${date}<time></td> 
						<td><strong>$${salary}</strong></td>
					</tr>`
        );
    }
}

async function queryEmployee() 
{
    let employeeNo = document.getElementById('emp_no_input').value;

    if (!employeeNo) {
        alert('Enter employee no please~');
        return;
    }

    try 
    {
        const response = await fetch(`/api/employee/info/${employeeNo}`);

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

        setInfoToPage(queryJson);

        // 显示并触发动画
        const infoContainer = document.getElementById('employee_info');
        infoContainer.style.display = 'block';
        infoContainer.style.animation = 'none';
        void infoContainer.offsetWidth; // 触发重绘
        infoContainer.style.animation = 'fadeIn 0.4s ease-out forwards';
    }
    catch (error) {
        console.error(error);
    }
}