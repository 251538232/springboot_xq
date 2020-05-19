/**
 * Created by X02107 on 2018/08/27.
 */
var vm = new Vue({
    el: "#app",
    data: {
        q: {
            dayOfMonth: "",
            dayOfMonthCondition: "between"
        },
        // pcTypes:[
        //     {"ID":0},
        //     {},
        //    "ProjectHours",
        //     "ProjectCost"
        // ],
        currentPCType:"",
        workHoursOption: {
            title : {
                text: 'WorkHours',
                subtext: 'WorkHours'
            },
            tooltip: {
                show: true
            },
            legend: {
                data: []
            },
            xAxis: [],
            yAxis: [
                {
                    type: 'value'
                }
            ],
            series: []
        },
        projectHoursOption: {
            title : {
                text: 'ProjectHours',
                subtext: 'ProjectHours'
            },
            tooltip: {
                show: true
            },
            xAxis: [],
            yAxis: [
                {
                    type: 'value'
                }
            ],
            series: []
        },
        projectCostOption: {
            title : {
                text: 'ProjectCost',
                subtext: 'ProjectCost'
            },
            tooltip: {
                show: true
            },
            legend: {
                data: []
            },
            xAxis: [],
            yAxis: [
                {
                    type: 'value'
                }
            ],
            series: []
        },
        projects: []
    },
    mounted: function () {
        this.getProjects();
    },
    methods: {
        loadCharts: function () {
            vm.loadWorkHoursChart();
            vm.loadProjectHoursChart();
        },
        loadWorkHoursChart: function () {
            $.ajax({
                url: apiUrl + 'daily/listByConditionMap?access_token=' + getToken("access_token"),
                type: "POST",
                data: JSON.stringify(vm.generateCondition()),
                success: function (result) {
                    if (result.code == 0) {
                        var list = result.data.list;
                        vm.fillWorkHours(list);
                        vm.showChart("work-hours-chart",vm.workHoursOption);
                    }else{
                        alert("loadWorkHoursChart error")
                    }
                }
            })
        },
        showChart:function (divId,option) {
            var myChart = echarts.init(document.getElementById(divId));
            myChart.setOption(option);
        },
        loadProjectHoursChart: function () {
            $.ajax({
                url: apiUrl + 'empDailyWork/projectsHours',
                type: "GET",
                success: function (result) {
                    if (result.code == 0) {
                        console.log("-----loadProjectHoursChart------");
                        console.log(result);
                        var map = result.data;

                        vm.fillProject(map,vm.projectHoursOption);
                        console.log(vm.projectCostOption)
                        vm.showChart("project-chart",vm.projectHoursOption);
                    }
                }
            })
        },
        loadProjectCostChart:function () {
            $.ajax({
                url: apiUrl + 'empDailyWork/projectsCost',
                type: "GET",
                success: function (result) {
                    if (result.code == 0) {
                        var map = result.data;
                        vm.fillProject(map,vm.projectCostOption);
                        vm.showChart("project-chart",vm.projectCostOption);
                    }
                }
            })
        },
        fillWorkHours: function (list) {
            vm.workHoursOption.xAxis = [];
            vm.workHoursOption.series=[];

            var xAxisType = {"type": "category"};
            var x = [];
            var series = [];
            var legendData = [];
            if (list != undefined) {
                for (var i = 0, len = list.length; i < len; i++) {
                    var item = list[i];
                    x.push(item.dayOfMonth.split(" ")[0]);

                    var workList = item.workList;
                    var projectNames = []
                    for (var j = 0, jLen = workList.length; j < jLen; j++) {
                        var workItem = workList[j];
                        var projectName = vm.getProjectName(workItem.projectId);
                        if(isEmptyString(projectName)){
                            continue;
                        }else{
                            if($.inArray(projectName,legendData) < 0){
                                legendData.push(projectName);
                            }
                        }
                        var serie = vm.getSerie(series, projectName,i);
                        serie.data.push(workItem.workHours);
                        projectNames.push(projectName);
                    }
                    vm.fillZero(series, projectNames);
                }
            }else{
                series.push({
                    "type":"bar",
                    "data":[]
                })
            }

            xAxisType["data"] = x;
            vm.workHoursOption.xAxis.push(xAxisType);
            vm.workHoursOption.series = series;
            vm.workHoursOption.legend.data = legendData;
        },
        fillProject:function (map,option) {
            var xAxis = {"type": "category"};
            var serieItem = {
                "type":"bar"
            }
            var data = [];
            var serieData = [];
            for(key in map){
                var projectName = vm.getProjectName(key);
                if (isEmptyString(projectName)) {
                    continue;
                }
                data.push(projectName);
                serieData.push(map[key]);
            }
            xAxis["data"] = data;
            serieItem["data"] = serieData;
            option.xAxis = [];
            option.series=[];
            option.xAxis.push(xAxis);
            option.series.push(serieItem);

        },
        changeMonth: function () {
            vm.loadWorkHoursChart();
        },
        changePCType:function () {
            console.log("vm.currentPCType : "+vm.currentPCType);
            if (vm.currentPCType == "ProjectHours") {
                vm.loadProjectHoursChart();
            }else if("ProjectCost"==vm.currentPCType){
                vm.loadProjectCostChart();
            }
        },
        getProjectName: function (projectId) {
            for (var i = 0, len = vm.projects.length; i < len; i++) {
                var project = vm.projects[i];
                if (project.id == projectId) {
                    return project.name;
                }
            }
        },
        getProjects: function () {
            $.ajax({
                url: apiUrl + 'mastProject/list/?access_token=' + getToken("access_token"),
                type: "GET",
                contentType: "application/json",
                success: function (result) {
                    if (result.code == 0) {
                        vm.projects = result.data.list;
                        console.log("projects: ");
                        console.log(vm.projects);
                        vm.loadCharts();
                    } else {
                        alert("失败.")
                    }
                }
            })
        },
        generateCondition: function () {
            var condition = {};
            var date;
            if (isEmptyString(vm.q.dayOfMonth)) {
                var d0 = new Date();
                date = new Date(d0.getFullYear(), d0.getMonth(), 1);
            } else {
                var d = vm.q.dayOfMonth + '-01';
                date = new Date(d);
            }
            var value = formatDateYMD000(date);
            date.setMonth(date.getMonth() + 1)
            value = value + ',' + formatDateYMD000(date);
            condition["dayOfMonth"] = value;
            condition["dayOfMonthCondition"] = vm.q.dayOfMonthCondition;
            return condition;
        },
        getSerie: function (series, name,number) {
            if (series.length == 0) {
                var serie = {"name": name, "type": "bar", "data": []};
                for (var j = 0; j < number; j++) {
                    serie.data.push(0);
                }
                series.push(serie);
                return serie;
            }
            for (var i = 0, len = series.length; i < len; i++) {
                var serie = series[i];
                if (serie.name == name) {
                    return serie;
                }
            }
            var serie = {"name": name, "type": "bar", "data": []};
            for (var j = 0; j < number; j++) {
                serie.data.push(0);
            }
            series.push(serie);
            return serie;

        },
        fillZero: function (series, projectNames) {
            for (var i = 0, len = series.length; i < len; i++) {
                var serie = series[i];
                if($.inArray(serie.name,projectNames) < 0){
                    serie.data.push(0);
                }
            }
        }
    }
})
