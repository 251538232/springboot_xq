$(function () {
    $("#gridTable").jqGrid({
        url: apiUrl + 'empSalary/list?access_token=' + getToken("access_token"),
        datatype: "json",
        colModel: [
            {
                label: '給料年月', name: 'salaryYm', width: 80, formatter: function (value, item, index) {
                return value.substr(0, 4) + "年" + value.substr(4, 2) + "月";
            }
            },
            {
                label: '給料ファイル', name: 'salaryYm', width: 80, formatter: function (value, item, index) {
                return '<a href="javascript:vm.getfile(' + value + ')">ダウンロード</a>';
            }
            },
            {
                label: '更新時間', name: 'updateTime', width: 80, formatter: function (value, item, index) {
                var day = daysBetween(value.substr(0, 10), getNowFormatDate().substr(0, 10));
                if (day < 3) {
                    return value + '　<span style="color:red">新</span> ';
                }
                return value;
            }
            }
        ],
        sortname: 'id',
        // 行目表示
        viewrecords: true,
        // ディフォルトオーダー順番
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
var isquery = true;
var vm = new Vue({
    el: '#webapp',
    data: {
        q: {
            id: "",
            idCondition: "=",
            employeeId: "",
            employeeIdCondition: "=",
            salaryYm: "",
            salaryYmCondition: "like",
            salaryFile: "",
            salaryFileCondition: "=",
            createTime: "",
            createTimeCondition: "=",
            updateTime: "",
            updateTimeCondition: "="
        },
        showList: true,
        title: null,
        empSalary: {}
    },
    methods: {
        select: function () {
            vm.reload();
        },
        create: function () {
            vm.showList = false;
            vm.title = "新規";
            vm.empSalary = {};
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
            var url = vm.empSalary.id == null ? "empSalary/create" : "empSalary/update";
            $.ajax({
                type: "POST",
                url: apiUrl + url,
                contentType: "application/json",
                data: JSON.stringify(vm.empSalary),
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
                    url: apiUrl + "empSalary/delete/" + ids.join(","),
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
        getfile: function (salaryYm) {
            var form = $("<form>");//定义一个form表单
            form.attr("style", "display:none");
            form.attr("target", "");
            form.attr("method", "GET");
            form.attr("action", apiUrl + "empSalary/getSalaryFile");
            var fileInput = $("<input type='hidden' id='salaryYm' name='salaryYm' value='" + salaryYm + "'>");
            var accessToken = $("<input type='hidden' id='access_token' name='access_token' value='" + getToken("access_token") + "'>");
            $("body").append(form);
            form.append(fileInput);
            form.append(accessToken);
            form.submit();
            form.remove();
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