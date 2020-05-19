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
        empApplyAdmit: {},
        empApplyAdmitList: [],
        employee: {}
    },
    methods: {
        select: function () {
            vm.reload();
        },
        initJqgrid: function () {
            $("#gridTable").jqGrid({
                url: apiUrl + 'apply/listAdmitApply?access_token=' + getToken("access_token"),
                datatype: "json",
                colModel: [
                    {label: '申請社員名', name: 'employeeName', width: 80},
                    {label: '申請種別ＩＤ', name: 'applyTypeId', width: 80},
                    {label: '申請タイトル', name: 'title', width: 80},
                    {label: '申請内容', name: 'content', width: 80},
                    {label: '補足', name: 'remark', width: 80},
                    {label: '開始時間', name: 'startTime', width: 80},
                    {label: '終了時間', name: 'endTime', width: 80},
                    // { label: '項目(複数の場合カンマ区切り)', name: 'item', width: 80 },
                    // { label: '項目値', name: 'itemValue', width: 80 },
                    {
                        label: '次回承認順番', name: 'admitOrderNum', width: 80
                    },
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
                    }
                    },
                    {
                        label: 'データ状態', name: 'disable', width: 80, formatter: function (value, item, index) {
                        var retVal = "";
                        if (value === 0) {
                            retVal = '<span class="label label-success">有効</span>';
                        } else {
                            retVal = '<span class="label label-warning">無効</span>';
                        }
                        return retVal;
                    }
                    },
                    {
                        label: '詳細', name: 'empApplyAdmitList', width: 120, formatter: function (value, item, index) {

                        // 承認可能の判断処理
                        for (var i = 0; i < value.length; i++) {
                            var v = value[i];
                            // 現在承認順番
                            // console.log(v);
                            if (!v.admitEmployeeIds) {
                                continue;
                            }
                            if (v.admitEmployeeIds.split(",").indexOf(vm.employee.id.toString()) > 0) {
                                vm.empApplyAdmitList.push(v);
                                if (v.admitOrderNum === v.orderNum) {
                                    if (v.status === 0) {
                                        return '<a href="javascript:vm.admitApply(' + v.id + ', 2);)">差し戻し</a>   ' + '<a href="javascript:vm.admitApply(' + v.id + ', 1);">承認</a>';
                                    }
                                }
                                // if (v.admitOrderNum - 1 === v.orderNum && value[i + 1].status === 0) {
                                //     return '<a href="javascript:vm.admitApply(' + v.id + ', 0);">承認取消</a>';
                                // }
                            }
                        }
                        return '';
                    }
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
        },
        getEmployee: function () {
            $.ajax({
                type: "GET",
                url: apiUrl + "/mastEmployee/getEmployee",
                contentType: "application/json",
                success: function (r) {
                    if (r.code === 0) {
                        vm.employee = r.data;
                        vm.initJqgrid();
                    } else {
                    }
                }
            });
        },
        admitApply: function (id, status) {
            console.log(vm.empApplyAdmitList);
            var applyAdmit;
            for (var i = 0; i < vm.empApplyAdmitList.length; i++) {
                if (vm.empApplyAdmitList[i].id === id) {
                    applyAdmit = vm.empApplyAdmitList[i];
                    break;
                }
            }
            applyAdmit.status = status;
            applyAdmit.comment = vm.comment;
            applyAdmit.admittedEmployeeId = vm.employee.id;
            $.ajax({
                type: "POST",
                url: apiUrl + "/apply/admitEmpApply",
                contentType: "application/json",
                data: JSON.stringify(applyAdmit),
                success: function (r) {
                    if (r.code === 0) {
                        vm.reload();
                    } else {
                    }
                }
            });
        },
        reload: function (event) {
            vm.empApplyAdmitList = [];
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

$(function () {
    vm.getEmployee();
    vm.empApplyAdmitList = [];
});