// 常量定义
const ONE_PAGINATION_COUNT  = 20; // 每页数据量
const TOTAL_EMPLOYEE_AMOUNT = Number.parseInt(
    document.getElementById('employee_amount').textContent
);
const TOTAL_PAGES = Math.ceil(TOTAL_EMPLOYEE_AMOUNT / ONE_PAGINATION_COUNT);

// 状态变量
let CURRENT_OFFSET = 0; // 当前偏移量

// 初始化加载第一页
doEmployeePaginationQuery();

//======================= 核心分页逻辑 =======================//
async function doEmployeePaginationQuery() 
{
    clearElement('employee_info_list'); // 清空列表

    try 
    {
        // 1. 发起 API 请求
        const response = await fetch(
            `/api/employee/range_info/${ONE_PAGINATION_COUNT}_${CURRENT_OFFSET}`
        );

        // 2. 错误处理
        if (!response.ok) {
            const error = await response.text();
            throw new Error(`HTTP ${response.status}: ${error}`);
        }

        // 3. 解析并验证数据
        const employees = await response.json();
        const listElement = document.getElementById('employee_info_list');

        for (const employee of employees) 
        {
            if (!validateEmployeeData(employee)) 
            {
                console.warn("Invalid employee data:", employee);
                return;
            }

            listElement.insertAdjacentHTML(
                'beforeend',
                `<li>
					<a href="/employee/query/${employee.employeeId}">
						Profile of employee: 
						[${employee.employeeId}] ${employee.firstName}-${employee.lastName}
					</a>
				</li>`
            );
        }

        // 4. 更新页码显示
        updatePageIndicator();

    } catch (error) {
        handleFetchError(error);
    }
}

//======================= 分页操作函数 =======================//
function goToPreviousPage() 
{
    const newOffset = CURRENT_OFFSET - ONE_PAGINATION_COUNT;

    if (updateOffset(newOffset)) {
        doEmployeePaginationQuery();
    }
}

function goToNextPage() 
{
    const newOffset = CURRENT_OFFSET + ONE_PAGINATION_COUNT;

    if (updateOffset(newOffset)) {
        doEmployeePaginationQuery();
    }
}

function goToFirstPage() 
{
    if (updateOffset(0)) {
        doEmployeePaginationQuery();
    }
}

function goToLastPage() 
{
    if (updateOffset(TOTAL_EMPLOYEE_AMOUNT - ONE_PAGINATION_COUNT)) {
        doEmployeePaginationQuery();
    }
}

function jumpToSpecificPage() 
{
    const input = document.getElementById('page_to_go').value.trim();

    // 输入验证
    if (!/^\d+$/.test(input)) 
    {
        alert("请输入有效整数页码！");
        return;
    }

    const page = Number.parseInt(input);

    if (page < 1 || page > TOTAL_PAGES) 
    {
        alert(`页码范围需在 1-${TOTAL_PAGES} 之间`);
        return;
    }

    const newOffset = (page - 1) * ONE_PAGINATION_COUNT;

    if (updateOffset(newOffset)) 
    {
        document.getElementById('page_to_go').value = ""; // 清空输入框
        doEmployeePaginationQuery();
    }
}

//======================= 工具函数 =======================//
function updateOffset(newOffset) 
{
    // 边界检查
    if (newOffset < 0) {
        alert("已经是第一页！");
        return false;
    }

    if (newOffset >= TOTAL_EMPLOYEE_AMOUNT) {
        alert("已经是最后一页！");
        return false;
    }

    CURRENT_OFFSET = newOffset;
    console.log(`Offset updated to: ${CURRENT_OFFSET}`);

    return true;
}

function updatePageIndicator() 
{
    const currentPage = Math.floor(CURRENT_OFFSET / ONE_PAGINATION_COUNT) + 1;
    document.getElementById('page_catalogue').textContent
        = `Curent Page: ${currentPage} / ${TOTAL_PAGES}`;
}

function validateEmployeeData(emp) 
{
    return emp?.employeeId &&
        emp?.firstName?.trim() &&
        emp?.lastName?.trim();
}

function handleFetchError(error) 
{
    console.error("请求失败:", error);
    alert(`数据加载失败: ${error.message}`);
}

function clearElement(id) {
    document.getElementById(id).innerHTML = '';
}

//======================= 事件绑定 =======================//
document.querySelectorAll('button').forEach(btn => {
    btn.addEventListener('click', function (e) {
        e.target.blur(); // 移除点击后的焦点样式
    });
});