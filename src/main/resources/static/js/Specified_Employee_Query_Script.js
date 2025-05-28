renderSalaryCharts();

function renderSalaryCharts() 
{
    // 员工姓名
    var employeeName
        = document.getElementById('employee_name').textContent;

    // 工资历史 map 的长度
    let historySize
        = Number.parseInt(
            document.getElementById('salary_history_size').textContent
        );

    const durationSet = new Set(); // 工资时间段集合
    const salarySet   = new Set(); // 工资数值集合

    for (var index = 0; index < historySize; ++index) 
        {
        durationSet.add(
            document.getElementById(`salary_time_${index}`).textContent
        );

        salarySet.add(
            Number.parseInt(
                document.getElementById(`salary_val_${index}`)
                    .textContent.substring(1)
            )
        );
    }


    var salaryChart = echarts.init(
        document.getElementById('salary_charts')
    );

    // 指定图表的配置项和数据
    option = {
        backgroundColor: '#0d1117',
        title: {
            text: `Salary history of [${employeeName}]`,
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
                fontSize: 12,     // 缩小字号
                top: '12%'
            },
            data: Array.from(durationSet)
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
            data: Array.from(salarySet)
        }]
    };

    salaryChart.setOption(option);
    salaryChart.resize();

    window.addEventListener(
        'resize',
        function () { salaryChart.resize(); }
    );
}