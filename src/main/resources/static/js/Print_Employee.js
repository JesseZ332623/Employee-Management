/**
 * 在执行打印前，先重新计算一次指定 ID 的图表的布局。
 * 
 * @param {string} chartsId 图表元素 ID
 */
function doPrint(chartsId)
{
    const chart = echarts.getInstanceByDom(document.getElementById(chartsId));

    chart.resize();

    window.print();
}