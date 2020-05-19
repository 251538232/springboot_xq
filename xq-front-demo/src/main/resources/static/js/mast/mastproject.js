$(function () {
    $("#gridTable").jqGrid({
        url: apiUrl + 'mastProject/list?access_token=' + getToken("access_token"),
        datatype: "json",
        colModel: [
            // {label: 'ＩＤ', name: 'id', width: 30},
            {label: 'コード', name: 'code', width: 80},
            {label: 'プロジェクト名', name: 'name', width: 120},
            {label: '作業種別', name: 'type', width: 50},
            {label: '売上日付', name: 'earningTime', width: 80},
            {label: '受注状態', name: 'orderStatus', width: 80},
            {label: '受注額', name: 'orderAmount', width: 80},
            {label: '労務費', name: 'serviceAmount', width: 80},
            {label: '経費', name: 'fundsAmount', width: 80},
            {label: '売上金額', name: 'profitsAmount', width: 80},
            {label: '総工数（Ｈ）', name: 'manHours', width: 80},
            {label: '開始時間', name: 'startTime', width: 80},
            {label: '終了時間', name: 'endTime', width: 80},
            // {label: '詳細', name: 'tasksDetail', width: 80},
        ],
        sortname: 'id',
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
        multiselect: true,
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
            id: "",
            idCondition: "=",
            organizationIds: "",
            organizationIdsCondition: "like",
            code: "",
            codeCondition: "like",
            name: "",
            nameCondition: "like",
            type: "",
            typeCondition: "like",
            earningTime: "",
            earningTimeCondition: "=",
            orderStatus: "",
            orderStatusCondition: "=",
            orderAmount: "",
            orderAmountCondition: "=",
            serviceAmount: "",
            serviceAmountCondition: "=",
            fundsAmount: "",
            fundsAmountCondition: "=",
            profitsAmount: "",
            profitsAmountCondition: "=",
            manHours: "",
            manHoursCondition: "=",
            content: "",
            contentCondition: "like",
            remark: "",
            remarkCondition: "like",
            startTime: "",
            startTimeCondition: "=",
            endTime: "",
            endTimeCondition: "=",
            disable: "",
            disableCondition: "=",
            createTime: "",
            createTimeCondition: "=",
            updateTime: "",
            updateTimeCondition: "="
            },
        showList: true,
        title: null,
        mastProject: {},
        earningTime: "",
        format: 'yyyy/mm/dd',
        tasks:[]
    },
    methods: {
        select: function () {
            vm.reload();
        },
        create: function () {
            vm.showList = false;
            vm.title = "新規";
            vm.mastProject = {};
            vm.tasks=[];
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

            var url = vm.mastProject.id == null ? "mastProject/createWithTasks" : "mastProject/update";
            vm.mastProject.tasks = vm.tasks;
            $.ajax({
                type: "POST",
                url: apiUrl + url,
                contentType: "application/json",
                data: JSON.stringify(vm.mastProject),
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
                    url: apiUrl + "mastProject/delete/" + ids.join(","),
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
            $.get(apiUrl + "mastProject/detail/" + id, function (r) {
                vm.mastProject = r.data;
            });
        },
        reload: function (event) {
            var condition = {};
            for (var key in vm.q) {
                // 検索条件取得処理
                if (key.indexOf("Condition") < 0 && vm.q[key] != "") {
                    condition[key] = vm.q[key];
                    condition[key + "Condition"] = vm.q[key + "Condition"];
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
        },
        addNewTask:function (event) {
            var task= {};
            vm.tasks.push(task);

        },
        removeTask:function (index) {
            vm.tasks.splice(index,1);
        }
    }
});