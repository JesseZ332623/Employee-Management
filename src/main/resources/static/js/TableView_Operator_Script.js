/**
 * 显示或者隐藏一张表格。
 * 
 * @param {HTMLElement} button    按钮元素
 * @param {string}      elementId 要显示 / 隐藏的元素
 */
function displayOrHidden(button, elementId) 
{
    const element = document.getElementById(elementId);

    if (element != null)
    {
        if (element.style.display != 'none')
        {
            element.style.display = 'none';
            button.textContent    = 'Display Table';
        }
        else 
        {
            element.style.display = 'table';
            button.textContent    = 'Hidden Table';
        }
    }
}