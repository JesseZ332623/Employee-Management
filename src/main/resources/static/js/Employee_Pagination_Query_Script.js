// 每页数据量
let ONE_PAGINATION_COUNT  = 30;

// 员工总数
let TOTAL_EMPLOYEE_AMOUNT = Number.parseInt(
    document.getElementById('employee_amount').textContent
);

// 总页数
let TOTAL_PAGES = Math.ceil(TOTAL_EMPLOYEE_AMOUNT / ONE_PAGINATION_COUNT);

// 当前偏移量
let CURRENT_OFFSET 
    = Number.parseInt(
        document.getElementById('current_offset').textContent.trim()
    );

// 初始化加载第一页
doEmployeePaginationQuery();

/**
 * 核心的分页查询逻辑。 
*/
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
        
        // 5. 保存当前的页面偏移量至服务器
        saveCurrentOffset();

    } catch (error) {
        handleFetchError(error);
    }
}

async function saveCurrentOffset() 
{
    try 
    {
        const response = await fetch(
            '/api/employee/save_current_offset',
            {
                method: 'PUT',
                headers: {
                    'Content-Type' : 'text/plain'
                },
                body: CURRENT_OFFSET.toString()
            }
        );

        if (!response.ok) 
        {
            const error = await response.text();
            throw new Error(`HTTP ${response.status}: ${error}`);
        }

        console.info(await response.text());
    }
    catch (error) {
        handleFetchError(error);
    }
}

/**
 * 去前一页。 
*/
function goToPreviousPage() 
{
    const newOffset = CURRENT_OFFSET - ONE_PAGINATION_COUNT;

    if (updateOffset(newOffset)) {
        doEmployeePaginationQuery();
    }
}

/**
 * 去后一页。 
*/
function goToNextPage() 
{
    const newOffset = CURRENT_OFFSET + ONE_PAGINATION_COUNT;

    if (updateOffset(newOffset)) {
        doEmployeePaginationQuery();
    }
}

/**
 * 去第一页。 
*/
function goToFirstPage() 
{
    if (updateOffset(0)) {
        doEmployeePaginationQuery();
    }
}

/**
 * 去最后一页。 
*/
function goToLastPage() 
{
    if (updateOffset(TOTAL_EMPLOYEE_AMOUNT - ONE_PAGINATION_COUNT)) {
        doEmployeePaginationQuery();
    }
}

/**
 * 跳转去指定的页。 
*/
function jumpToSpecificPage() 
{
    const input 
        = document.getElementById('page_to_go').value.trim();

    // 输入验证（对不是纯数字字符串的输入予以驳回）。
    // 正则表达式 /^\d+$/ 表示严格匹配纯数字字符串。
    if (!(/^\d+$/.test(input))) 
    {
        alert("Please enter valid integer page.");
        return;
    }

    // String -> Number
    const page = Number.parseInt(input);

    if (page < 1 || page > TOTAL_PAGES) 
    {
        alert(`Page range need bettwen 1 to ${TOTAL_PAGES}.`);
        return;
    }

    const newOffset = (page - 1) * ONE_PAGINATION_COUNT;

    if (updateOffset(newOffset)) 
    {
        document.getElementById('page_to_go').value = ""; // 清空输入框
        doEmployeePaginationQuery();
    }
}

/**
 * 更新页面偏移量，这部分的代码非常适合提取出来复用。
 * 
 * @param {Number} newOffset 新偏移量
*/
function updateOffset(newOffset) 
{
    // 边界检查
    if (newOffset < 0) {
        alert("Already the first page!");
        return false;
    }

    if (newOffset >= TOTAL_EMPLOYEE_AMOUNT) {
        alert("Already the last page!");
        return false;
    }

    CURRENT_OFFSET = newOffset;
    console.log(`Offset updated to: ${CURRENT_OFFSET}`);

    return true;
}

/**
 * 计算当前所在的页码并更新。
*/
function updatePageIndicator() 
{
    let currentPage = Math.floor(CURRENT_OFFSET / ONE_PAGINATION_COUNT) + 1;

    document.getElementById('page_catalogue').textContent
            = `Curent Page: ${currentPage} / ${TOTAL_PAGES}`;
}

/**
 * 验证员工的各项数据是否存在。
*/
function validateEmployeeData(emp) 
{
    return emp?.employeeId        &&
           emp?.firstName?.trim() &&
           emp?.lastName?.trim();
}

/**
 * 错误处理逻辑。
*/
function handleFetchError(error) 
{
    console.error("请求失败:", error);
    alert(`数据加载失败: ${error.message}`);
}

/**
 * 清空指定 ID 对应的元素的所有内容。
*/
function clearElement(id) {
    document.getElementById(id).innerHTML = '';
}

/**
 * 选择页面中所有的按钮，并依次给他们添加上监听器，
 * 当某个按钮被点击时，立刻移除它的焦点（focus）。
*/
document.querySelectorAll('button')
        .forEach((btn) => {
                btn.addEventListener('click', function (event) {
                    event.currentTarget.blur(); // 移除点击后的焦点样式
            }
        );
});