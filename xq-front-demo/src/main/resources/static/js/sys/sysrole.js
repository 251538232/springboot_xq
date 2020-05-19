$(function () {
    $("#gridTable").jqGrid({
        url: apiUrl + 'sysRole/list?access_token=' + getToken("access_token"),
        datatype: "json",
        colModel: [
            {label: 'ID', name: 'id', width: 20},
            {label: '役柄名称', name: 'roleName', width: 80},
            {label: '説明', name: 'description', width: 80},
            {label: '作成者ＩＤ', name: 'createUserId', width: 20},
            {label: '作成時間', name: 'createTime', width: 80},
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
//メニュー
var menu_ztree;
var menu_setting = {
    data: {
        simpleData: {
            enable: true,
            idKey: "id",
            pIdKey: "parentId",
            rootPId: -1
        },
        key: {
            url: ""
        }
    },
    check: {
        enable: true,
        nocheckInherit: true
    }
};

var vm = new Vue({
    el: '#webapp',
    data: {
        q: {
            id: "",
            idCondition: "=",
            roleName: "",
            roleNameCondition: "like",
            description: "",
            descriptionCondition: "like",
            createUserId: "",
            createUserIdCondition: "=",
            createTime: "",
            createTimeCondition: "=",
            updateTime: "",
            updateTimeCondition: "="
        },
        showList: true,
        title: null,
        sysRole: {}
    },
    methods: {
        select: function () {
            vm.reload();
        },
        getRole: function (roleId) {
            $.ajax({
                type: "GET",
                url: apiUrl + "sysRole/detail/" + roleId,
                success: function (r) {
                    if (r.code === 0) {
                        vm.sysRole = r.data;
                        var menuIds = r.data.menuIdList;
                        for (var i = 0; i < menuIds.length; i++) {
                            var node = menu_ztree.getNodeByParam("id", menuIds[i]);
                            if (node){
                                menu_ztree.checkNode(node, true, false);
                            }
                        }
                    } else {
                        alert(r.message);
                    }
                }
            });
        },
        create: function () {
            vm.showList = false;
            vm.title = "新規";
            vm.sysRole = {};
            vm.getMenuTree(null);
        },
        update: function (event) {
            var id = getSelectedRow();
            if (id == null) {
                return;
            }
            vm.showList = false;
            vm.title = "更新";
            vm.getMenuTree(id);
        },
        createOrUpdate: function (event) {
            var nodes = menu_ztree.getCheckedNodes(true);
            var menuIdList = new Array();
            for (var i = 0; i < nodes.length; i++) {
                menuIdList.push(nodes[i].id);
            }
            vm.sysRole.menuIdList = menuIdList;
            var url = vm.sysRole.id == null ? "sysRole/create" : "sysRole/update";
            $.ajax({
                type: "POST",
                url: apiUrl + url,
                contentType: "application/json",
                data: JSON.stringify(vm.sysRole),
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
        getMenuTree: function (roleId) {
            // メニューTree
            $.get(apiUrl + "sysMenu/list", function (r) {
                menu_ztree = $.fn.zTree.init($("#menuTree"), menu_setting, r.data);
                menu_ztree.expandAll(true);
                if (roleId != null) {
                    vm.getDetail(roleId);
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
                    url: apiUrl + "sysRole/delete/" + ids.join(","),
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
                url: apiUrl + "sysRole/detail/" + id,
                success: function (r) {
                    vm.sysRole = r.data;
                    if (r.code === 0) {
                        vm.sysRole = r.data;
                        var menuIds = r.data.menuIdList;
                        for (var i = 0; i < menuIds.length; i++) {
                            var node = menu_ztree.getNodeByParam("id", menuIds[i]);
                            if (node){
                                menu_ztree.checkNode(node, true, false);
                            }
                        }
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