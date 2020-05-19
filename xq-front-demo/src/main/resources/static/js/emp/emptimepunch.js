$(function () {
    $("#gridTable").jqGrid({
        url: apiUrl + 'daily/monthDaily?access_token=' + getToken("access_token"),
        datatype: "json",
        colModel: [
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
        sortname: 'dayOfMonth',
        // 行目表示
        viewrecords: true,
        // ディフォルトオーダー順番
        // sortorder: "asc",
        height: screen.availHeight - 400,
        width: screen.availWidth,
        rowNum: 10,
        rowList: [10, 30, 50],
        rownumbers: true,
        rownumWidth: 25,
        autowidth: false,
        multiselect: false,
        shrinkToFit: true,
        pager: "#gridPager",
        jsonReader: {
            root: "data.list",
            page: "data.currPage",
            total: "data.totalPage",
            records: "data.totalCount"
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
        }
    });
});


var vm = new Vue({
    el: '#webapp',
    data: {
        q: {
            employeeId:"",
            employeeIdCondition: "=",
            dayOfMonth: "",
            dayOfMonthCondition: "between",
            timePunchTime:"",
            timePunchTimeCondition: "=",
            timePunchType:"",
            timePunchTypeCondition: "like",
            ipAddress:"",
            ipAddressCondition: "like",
            createTime:"",
            createTimeCondition: "=",
            updateTime:"",
            updateTimeCondition: "="
        },
        showList: true,
        title: null,
        empTimePunch: {},
        dailyExists:true
    },
    methods: {
        select: function () {
            vm.reload();
        },
        create: function () {
            vm.showList = false;
            vm.title = "新規";
            vm.empTimePunch = {};
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
            var url = vm.empTimePunch.id == null ? "empTimePunch/create" : "empTimePunch/update";
            $.ajax({
                type: "POST",
                url: apiUrl + url,
                contentType: "application/json",
                data: JSON.stringify(vm.empTimePunch),
                success: function (r) {
                    if (r.code === 0) {
                        var msg = vm.empTimePunch.id == null ? "データ新規成功しました" : "データ更新成功しました";
                        alert(msg, function (index) {
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
                    url: apiUrl + "empTimePunch/delete/" + ids.join(","),
                    success: function (r) {
                        if (r.code == 0) {
                            alert('`削除成功しました。', function (index) {
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
                url: apiUrl + "empTimePunch/detail/" + id,
                success: function (r) {
                    if (r.code == 0) {
                        vm.empTimePunch = r.data;
                    } else {
                        alert(r.message);
                    }
                }
            });
        },
        reload: function (event) {
            var condition = {};
            for (var key in vm.q) {
                // 検索条件取得処理
                if (key.indexOf("Condition") < 0 && vm.q[key] != "") {
                    if (key == 'dayOfMonth'){
                        var d = vm.q[key] + '-01';
                        var date = new Date(d);
                        var value = formatDateYMD000(date);
                        date.setMonth(date.getMonth() + 1)
                        value = value + ',' + formatDateYMD000(date);
                        condition[key] = value;
                        condition[key + "Condition"] = vm.q[key + "Condition"];
                    }else{
                        condition[key] = vm.q[key];
                        condition[key + "Condition"] = vm.q[key + "Condition"];
                    }

                }
            }
            vm.showList = true;
            var postData = $("#gridTable").jqGrid("getGridParam", "postData");
            $.each(postData, function (k, v) {
                delete postData[k];
            });
            var page = $("#gridTable").jqGrid('getGridParam', 'page');
            $("#gridTable").jqGrid('setGridParam', {
                postData: condition,
                page: page
            }).trigger("reloadGrid");
        }

    }
});