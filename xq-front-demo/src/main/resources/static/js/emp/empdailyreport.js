function getNowYMD() {
    var d = new Date(),
        month = '' + (d.getMonth() + 1),
        day = '' + d.getDate(),
        year = d.getFullYear();

    if (month.length < 2) month = '0' + month;
    if (day.length < 2) day = '0' + day;
    var condition = {}
    condition['dayOfMonth'] = [year, month, day].join('-');
    return condition;
}

function getCondition() {
    var condition = {}
    condition['dayOfMonthCondition'] = "=";
    condition['dayOfMonth'] = formatDateYMD000(new Date());
    return condition;
}

$(function () {
    $("#gridTable").jqGrid({
        url: apiUrl + 'daily/getDaily?access_token=' + getToken("access_token"),
        postData: getNowYMD(),
        datatype: "json",
        colModel: [
            {
                label: 'id', name: 'id', hidden: true, formatter: function (value, item, index) {
                vm.empDaily.id = value;
                return value;
            }
            },
            {
                label: '日付', name: 'dayOfMonth', width: 80,
                formatter: function (value, item, index) {
                    return value.split(" ")[0];
                }
            },
            {label: '始業時刻', name: 'startTime', width: 80},
            {label: '終業時刻', name: 'endTime', width: 80},
            {label: '勤務実働時間', name: 'workHours', width: 80},
            {
                label: '遅刻時間', name: 'lateHours', width: 80,
                formatter: function (value, item, index) {
                    if (null == value || value == undefined) {
                        return '無';
                    }
                    var number = parseFloat(value);
                    number = number.toFixed(2);
                    if (number <= 0) {
                        return '無';
                    } else {
                        return '<span class="label label-warning">' + number + '</span>';
                    }

                }
            },
            {
                label: '早退時間', name: 'retreatHours', width: 80,
                formatter: function (value, item, index) {
                    if (null == value || value == undefined) {
                        return '無';
                    }
                    var number = parseFloat(value);
                    number = number.toFixed(2);
                    if (number <= 0) {
                        return '無';
                    } else {
                        return '<span class="label label-warning">' + number + '</span>';
                    }

                }
            },
            {
                label: '普通残業時間', name: 'extraHours', width: 80,
                formatter: function (value, item, index) {
                    if (null == value || value == undefined) {
                        return '無';
                    }
                    var number = parseFloat(value);
                    number = number.toFixed(2);
                    if (number <= 0) {
                        return '無';
                    } else {
                        return '<span class="label label-success">' + number + '</span>';
                    }

                }
            },
            {
                label: '深夜残業時刻', name: 'nightExtraHours', width: 80,
                formatter: function (value, item, index) {
                    if (null == value || value == undefined) {
                        return '無';
                    }
                    var number = parseFloat(value);
                    number = number.toFixed(2);
                    if (number <= 0) {
                        return '無';
                    } else {
                        return '<span class="label label-success">' + number + '</span>';
                    }

                }
            }
        ],
        sortname: 'id',
        // 行目表示
        viewrecords: true,
        // ディフォルトオーダー順番
        // sortorder: "asc",
        height: screen.availHeight * 0.06,
        width: screen.availWidth,
        rowNum: 10,
        rowList: [10, 30, 50],
        rownumbers: false,
        rownumWidth: 25,
        autowidth: false,
        multiselect: false,
        shrinkToFit: true,
        // pager: "#gridPager",
        jsonReader: {
            root: "data.list"
        },
        prmNames: {
            page: "page",
            rows: "limit",
            order: "order"
        },
        // ロード完了した場合
        gridComplete: function () {
            $("#gridTable").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
            $("#gridTable").trigger("resize");
            var re_records = $("#gridTable").getGridParam('records');
            if(re_records == 0 || re_records == null){
                vm.daily_no_records = true;
            }else{
                vm.daily_no_records = false;
            }
        }
    });
});

var vm = new Vue({
    el: '#webapp',
    data: {
        q: {
            dayOfMonth: "",
            dayOfMonthCondition: "="
        },
        empDaily: {},
        showList: true,
        title: null,
        dailyExists: false,
        rests: [],
        works: [],
        result: {},
        newRests: [],
        newWorks:[],
        editedRest:{},
        editedWork:{},
        projects:[
        ],
        daily_no_records:false
    },
    mounted: function () {
        this.loadDetails(getCondition());
    },
    computed: {
        computedDayOfMonth: function () {
            if (vm.q.dayOfMonth == "" || vm.q.dayOfMonth == null || vm.q.dayOfMonth == undefined) {
                var formatDateYMD = formatDateYMD000(new Date());
                return formatDateYMD000(new Date());
            } else {
                return vm.q.dayOfMonth + " 00:00:00";
            }
        }

    },
    methods: {
        select: function () {
            vm.reload();
        },
        create: function () {
            vm.showList = false;
            vm.title = "新規";
            vm.empHoliday = {};
        },
        update: function (event) {
            var id = getSelectedRow();
            if (id == null) {
                return;
            }
            vm.showList = false;
            vm.title = "更新";
            vm.getDetail(id)
        },
        createOrUpdate: function (event) {
            var url = vm.empHoliday.id == null ? "empHoliday/create" : "empHoliday/update";
            $.ajax({
                type: "POST",
                url: apiUrl + url,
                contentType: "application/json",
                data: JSON.stringify(vm.empHoliday),
                success: function (r) {
                    if (r.code === 0) {
                        alert('成功', function (index) {
                            vm.reload();
                        });
                    } else {
                        alert(r.message);
                    }
                }
            });
        },
        del: function (event) {
            var ids = getSelectedRows();
            if (ids == null) {
                return;
            }
            confirm('選択したデータを削除しますか？', function () {
                $.ajax({
                    type: "GET",
                    url: apiUrl + "empHoliday/delete/" + ids.join(","),
                    success: function (r) {
                        if (r.code == 0) {
                            alert('成功', function (index) {
                                $("#gridTable").trigger("reloadGrid");
                            });
                        } else {
                            alert(r.message);
                        }
                    }
                });
            });
        },
        getDetail: function (id) {
            $.ajax({
                type: "GET",
                url: apiUrl + "empHoliday/detail/" + id,
                success: function (r) {
                    if (r.code == 0) {
                        vm.empHoliday = r.data;
                    } else {
                        alert(r.message);
                    }
                }
            });
        },
        addNewRest: function () {
            var rest = {"dailyId": vm.empDaily.id};
            vm.newRests.push(rest);
        },
        removeRestItem: function (index) {
            vm.newRests.splice(index, 1);
        },
        addNewWork: function () {
            var work = {"dailyId": vm.empDaily.id};
            vm.newWorks.push(work);
        },
        removeWorkItem: function (index) {
            vm.newWorks.splice(index, 1);
        },
        computeRestHours: function (index) {
            var rest = vm.newRests[index];
            var startTime = rest.startTime;
            var endTime = rest.endTime;

            if (!isEmptyString(startTime) && !isEmptyString(endTime)) {
                var endDate = new Date(endTime);
                var startDate = new Date(startTime);
                var diff = ((endDate - startDate) / 3600000).toFixed(2);
                rest.restHours = diff;
            }
        },
        computeWorkHours: function (index) {
            var work = vm.newWorks[index];
            var startTime = work.startTime;
            var endTime = work.endTime;

            if (!isEmptyString(startTime) && !isEmptyString(endTime)) {
                var endDate = new Date(endTime);
                var startDate = new Date(startTime);
                var diff = ((endDate - startDate) / 3600000).toFixed(2);
                work.workHours = diff;
            }

        },
        computeEditedRestHours:function () {
            var startTime = vm.editedRest.startTime;
            var endTime = vm.editedRest.endTime;

            if (!isEmptyString(startTime) && !isEmptyString(endTime)) {
                var endDate = new Date(endTime);
                var startDate = new Date(startTime);
                var diff = ((endDate - startDate) / 3600000).toFixed(2);
                vm.editedRest.restHours = diff;
            }
        },
        computeEditedWorkHours:function () {
            var startTime = vm.editedWork.startTime;
            var endTime = vm.editedWork.endTime;

            if (!isEmptyString(startTime) && !isEmptyString(endTime)) {
                var endDate = new Date(endTime);
                var startDate = new Date(startTime);
                var diff = ((endDate - startDate) / 3600000).toFixed(2);
                vm.editedWork.workHours = diff;
            }
        },
        reload: function () {
            vm.rests = [];
            vm.works = [];
            var condition = {};
            condition['dayOfMonth'] = vm.computedDayOfMonth;
            condition['dayOfMonthCondition'] = vm.q["dayOfMonthCondition"];
            var postData = $("#gridTable").jqGrid("getGridParam", "postData");
            $.each(postData, function (k, v) {
                delete postData[k];
            });
            var page = $("#gridTable").jqGrid('getGridParam', 'page');
            $("#gridTable").jqGrid('setGridParam', {
                postData: condition,
                page: page
            }).trigger("reloadGrid");

            vm.loadDetails(condition);
        },
        editRest: function (editedRest) {
            var startTimeS = editedRest.startTime.split(" ");
            var endTimeS = editedRest.endTime.split(" ");
            vm.editedRest = $.extend(true,{},editedRest);
            vm.editedRest.startTime = startTimeS[0]+"T"+startTimeS[1].substr(0,5);
            vm.editedRest.endTime = endTimeS[0]+"T"+endTimeS[1].substr(0,5);
            $('#editRestModal').modal("show");
        },
        deleteRest: function (restId) {
            confirm("選択したデータを削除しますか？", function () {
                $.ajax({
                    url: apiUrl + "empDailyRest/delete/"+restId+'?access_token=' + getToken("access_token"),
                    type:"DELETE",
                    contentType: "application/json",
                    success: function (r) {
                        if (r.code == 0) {
                            alert("成功.")
                            vm.reload();
                        } else {
                            alert("失败.")
                        }
                    }
                })
            })
        },
        loadDetails: function (condition) {
            var url = 'daily/listByConditionMap?access_token=' + getToken("access_token");
            $.ajax({
                type: "POST",
                url: apiUrl + url,
                contentType: "application/json",
                data: JSON.stringify(condition),
                success: function (r) {
                    if (r.code === 0) {
                        vm.result = r.data;
                        if (r.data.list != undefined && r.data.list.length > 0) {
                            vm.rests = r.data.list[0].restList;
                            vm.works = r.data.list[0].workList;
                        }
                    } else {
                        alert(r.message);
                    }
                }
            });
            this.getProjects();
        },
        addNewRests: function () {
            $.ajax({
                url: apiUrl + 'empDailyRest/createBatch?access_token=' + getToken("access_token"),
                type: "POST",
                contentType: "application/json",
                data: JSON.stringify(vm.newRests),
                success: function (result) {
                    if (result.code == 0) {
                        alert("成功.");
                        $('#newRestsModal').modal("hide");
                        vm.reload();
                    } else {
                        alert("失败.")
                    }
                }
            })
        },
        updateRest:function () {
            $.ajax({
                url: apiUrl + 'empDailyRest/update/?access_token=' + getToken("access_token"),
                type: "PUT",
                contentType: "application/json",
                data: JSON.stringify(vm.editedRest),
                success: function (result) {
                    if (result.code == 0) {
                        alert("成功.");
                        $('#editRestModal').modal("hide");
                        vm.reload();
                    } else {
                        alert("失败.")
                    }
                }
            })
        },
        editWork: function (editedWork) {
            var startTimeS = editedWork.startTime.split(" ");
            var endTimeS = editedWork.endTime.split(" ");
            vm.editedWork = $.extend(true,{},editedWork);
            vm.editedWork.startTime = startTimeS[0]+"T"+startTimeS[1].substr(0,5);
            vm.editedWork.endTime = endTimeS[0]+"T"+endTimeS[1].substr(0,5);
            $('#editWorkModal').modal("show");
        },
        deleteWork: function (workId) {
            confirm("選択したデータを削除しますか？", function () {
                $.ajax({
                    url: apiUrl + "empDailyWork/delete/"+workId+'?access_token=' + getToken("access_token"),
                    type:"DELETE",
                    contentType: "application/json",
                    success: function (r) {
                        if (r.code == 0) {
                            alert("成功.")
                            vm.reload();
                        } else {
                            alert("失败.")
                        }
                    }
                })
            })
        },
        showRestsModal:function () {
            vm.newRests = [];
            $("#newRestsModal").modal('show');
        },
        showWorksModal:function () {
            vm.newWorks = [];
            $("#newWorksModal").modal('show');
        },
        addNewWorks: function () {
            $.ajax({
                url: apiUrl + 'empDailyWork/createBatch?access_token=' + getToken("access_token"),
                type: "POST",
                contentType: "application/json",
                data: JSON.stringify(vm.newWorks),
                success: function (result) {
                    if (result.code == 0) {
                        alert("成功.");
                        $('#newWorksModal').modal("hide");
                        vm.reload();
                    } else {
                        alert("失败.")
                    }
                }
            })
        },
        updateWork:function () {
            $.ajax({
                url: apiUrl + 'empDailyWork/update/?access_token=' + getToken("access_token"),
                type: "PUT",
                contentType: "application/json",
                data: JSON.stringify(vm.editedWork),
                success: function (result) {
                    if (result.code == 0) {
                        alert("成功.");
                        $('#editWorkModal').modal("hide");
                        vm.reload();
                    } else {
                        alert("失败.")
                    }
                }
            })
        },
        getProjects:function () {
            $.ajax({
                url: apiUrl + 'mastProject/listByEmp/?access_token=' + getToken("access_token"),
                type: "GET",
                contentType: "application/json",
                success: function (result) {
                    if (result.code == 0) {
                        vm.projects = result.data;
                    } else {
                        alert("失败.")
                    }
                }
            })
        },
        projectName:function(projectId){
            for(i =0;i<vm.projects.length;i++){
                var project = vm.projects[i];
                if(project.id === projectId){
                    return project.name;
                }
            }
        }
    }
});