$(function () {
    vm.getOrgs();
    $("#gridTable").jqGrid({
        url: apiUrl + 'mastApplyRoute/list?access_token=' + getToken("access_token"),
        datatype: "json",
        colModel: [
            {label: 'コード', name: 'code', width: 50},
            {label: '内容', name: 'routeValue', width: 50},
            {label: '説明', name: 'discription', width: 160},
            {
                label: '状態', name: 'disable', width: 50, formatter: function (value, item, index) {
                if (value === 0) {
                    return '<span class="label label-success">有効</span>';
                } else {
                    return '<span class="label label-danger">無効</span>';
                }
            }
            },
            {label: '更新時間', name: 'updateTime', width: 80}
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
            code: "",
            codeCondition: "like",
            routeValue: "",
            routeValueCondition: "like",
            discription: "",
            discriptionCondition: "like",
            disable: "",
            disableCondition: "=",
            createTime: "",
            createTimeCondition: "=",
            updateTime: "",
            updateTimeCondition: "="
        },
        showList: true,
        title: null,
        mastApplyRoute: {},
        routeItems: [],
        orgs: []
    },
    methods: {
        select: function () {
            vm.reload();
        },
        create: function () {
            vm.routeItems = [];
            vm.showList = false;
            vm.title = "新規";
            vm.mastApplyRoute = {};
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
            if (!vm.mastApplyRoute.code) {
                alert("コードを入力してください。");
                return;
            }
            var routeValue = [];
            for (var i = 0; i < vm.routeItems.length; i++) {
                if (vm.routeItems[i] != "") {
                    routeValue.push(vm.routeItems[i]);
                }
            }
            vm.mastApplyRoute.routeValue = routeValue.join(",");
            if (!vm.mastApplyRoute.routeValue || vm.mastApplyRoute.routeValue.trim() === "") {
                alert("内容を入力してください。");
                return;
            }
            if (vm.mastApplyRoute.disable === "") {
                alert("状態を選択してください。");
                return;
            }
            var url = vm.mastApplyRoute.id == null ? "mastApplyRoute/create" : "mastApplyRoute/update";
            $.ajax({
                type: "POST",
                url: apiUrl + url,
                contentType: "application/json",
                data: JSON.stringify(vm.mastApplyRoute),
                success: function (r) {
                    if (r.code === 0) {
                        var msg = vm.mastApplyRoute.id == null ? "データ新規成功しました" : "データ更新成功しました";
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
                    url: apiUrl + "mastApplyRoute/delete/" + ids.join(","),
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
                url: apiUrl + "mastApplyRoute/detail/" + id,
                success: function (r) {
                    if (r.code == 0) {
                        vm.mastApplyRoute = r.data;
                        vm.routeItems = vm.mastApplyRoute.routeValue.split(",");
                    } else {
                        alert(r.message);
                    }
                }
            });
        },
        getOrgs: function () {
            $.ajax({
                type: "GET",
                url: apiUrl + "mastOrganization/listAll",
                success: function (r) {
                    if (r.code == 0) {
                        vm.orgs.push({"id": "d1", "name": "本人上1位組織"});
                        vm.orgs.push({"id": "d2", "name": "本人上2位組織"});
                        vm.orgs.push({"id": "d3", "name": "本人上3位組織"});
                        vm.orgs.push({"id": "d4", "name": "本人上4位組織"});
                        vm.orgs.push({"id": "d5", "name": "本人上5位組織"});
                        for (var i = 0; i < r.data.length; i++) {
                            vm.orgs.push(r.data[i]);
                        }
                    } else {
                        alert(r.message);
                    }
                }
            });
        },
        addRoute: function () {
            vm.routeItems.push("");
        },
        removeRoute: function (index) {
            vm.routeItems.splice(index, 1);
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