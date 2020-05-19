$(function () {
    $("#gridTable").jqGrid({
        url: apiUrl + 'apply/listOwnApply?access_token=' + getToken("access_token"),
        datatype: "json",
        colModel: [
            // {label: '申請種別ＩＤ', name: 'applyTypeId', width: 80, sortable: false},
            // {label: '申請タイトル', name: 'title', width: 80, sortable: false},
            {label: '申請内容', name: 'content', width: 80, sortable: false},
            // {label: '補足', name: 'remark', width: 80, sortable: false},
            // {label: '開始時間', name: 'startTime', width: 80, sortable: false},
            // {label: '終了時間', name: 'endTime', width: 80, sortable: false},
            // { label: '項目(複数の場合カンマ区切り)', name: 'item', width: 80 },
            // { label: '項目値', name: 'itemValue', width: 80 },
            // { label: '次回承認順番', name: 'admitOrderNum', width: 80 },
            {
                label: '状態', name: 'status', width: 80, formatter: function (value, item, index) {
                var retVal = "";
                if (value === 0) {
                    retVal = '<span class="label label-warning">承認待ち</span>';
                } else if (value === 1) {
                    retVal = '<span class="label label-success">承認済み</span>';
                } else if (value === 2) {
                    retVal = '<span class="label label-danger">却下</span>';
                } else {
                    retVal = '<span class="label label-danger">取消</span>';
                }
                return retVal;
            }, sortable: false
            },
            {
                label: 'データ状態', name: 'disable', width: 80, formatter: function (value, item, index) {
                var retVal = "";
                if (value === 0) {
                    retVal = '<span class="label label-success">有効</span>';
                } else {
                    retVal = '<span class="label label-danger">無効</span>';
                }
                return retVal;
            }, sortable: false
            },
            {
                label: '詳細', name: 'empApplyAdmitList', width: 150, formatter: function (value, item, index) {
                var retValue = "";
                // 承認可能の判断処理
                for (var i = 0; i < value.length; i++) {
                    var v = value[i];
                    if (v.status === 1) {
                        retValue += v.orderNum + " " + v.admitEmployeeNames + ' 承認済み　承認担当:' + v.admittedEmployeeName + "<br/>";
                    } else if (v.status === 0) {
                        retValue += v.orderNum + " " +v.admitEmployeeNames + ' 承認待ち' + "<br/>";
                    } else if (v.status === 2) {
                        retValue += v.orderNum + " " +v.admitEmployeeNames + ' 差し戻し 　差し戻し担当:' + v.admittedEmployeeName + "<br/>";
                    }
                }
                return retValue;
            }, sortable: false
            }
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
            employeeId: "",
            employeeIdCondition: "=",
            applyTypeId: "",
            applyTypeIdCondition: "=",
            title: "",
            titleCondition: "like",
            content: "",
            contentCondition: "like",
            remark: "",
            remarkCondition: "like",
            startTime: "",
            startTimeCondition: "=",
            endTime: "",
            endTimeCondition: "=",
            item: "",
            itemCondition: "like",
            itemValue: "",
            itemValueCondition: "like",
            admitOrderNum: "",
            admitOrderNumCondition: "=",
            status: "",
            statusCondition: "=",
            disable: "",
            disableCondition: "=",
            createTime: "",
            createTimeCondition: "=",
            updateTime: "",
            updateTimeCondition: "="
        },
        showList: true,
        title: null,
        empApply: {}
    },
    methods: {
        select: function () {
            vm.reload();
        },
        create: function () {
            vm.showList = false;
            vm.title = "新規";
            vm.empApply = {};
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
            var url = vm.empApply.id == null ? "apply/create" : "apply/update";
            $.ajax({
                type: "POST",
                url: apiUrl + url,
                contentType: "application/json",
                data: JSON.stringify(vm.empApply),
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
                    url: apiUrl + "apply/delete/" + ids.join(","),
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
                url: apiUrl + "apply/detail/" + id,
                success: function (r) {
                    if (r.code == 0) {
                        vm.empApply = r.data;
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
        }
    }
});