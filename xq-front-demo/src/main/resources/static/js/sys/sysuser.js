$(function () {
    $("#gridTable").jqGrid({
        url: apiUrl + 'sysUser/list?access_token=' + getToken("access_token"),
        datatype: "json",
        colModel: [
            {label: 'ID', name: 'id', width: 20},
            {label: 'ユーザー名', name: 'username', width: 60},
            {label: 'メール', name: 'email', width: 100},
            {label: '携帯番号', name: 'mobile', width: 80},
            {
                label: '状態', name: 'status', width: 60, formatter: function (value, item, index) {
                var retVal = "";
                if (value === 0) {
                    retVal = '<span class="label label-danger">禁用</span>';
                } else if (value === 1) {
                    retVal = '<span class="label label-success">正常</span>';
                } else {
                    retVal = '<span class="label label-warning">限定</span>';
                }
                return retVal;
            }
            },
            {label: '作成時間', name: 'createTime', width: 80},
            {label: '更新時間', name: 'updateTime', width: 80}
        ],
        sortname: 'id',
        // 行目表示
        viewrecords: true,
        // ディフォルトオーダー順番
        height: 396,
        width: window.screen.availWidth - 400,
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
            username: "",
            usernameCondition: "like",
            password: "",
            passwordCondition: "like",
            salt: "",
            saltCondition: "like",
            email: "",
            emailCondition: "like",
            mobile: "",
            mobileCondition: "like",
            status: "",
            statusCondition: "=",
            createUserId: "",
            createUserIdCondition: "=",
            createTime: "",
            createTimeCondition: "=",
            updateTime: "",
            updateTimeCondition: "="
        },
        showList: true,
        showChange: false,
        isCreate: false,
        title: null,
        sysUser: {},
        newPassword: null,
        sysRoles: []
    },
    methods: {
        select: function () {
            vm.reload();
        },
        create: function () {
            vm.showList = false;
            vm.title = "新規";
            vm.sysUser = {};
            vm.isCreate = true;

            vm.getSysRoles();
            vm.sysUser.roleIdList = [];
            vm.sysUser.status = '1';
        },
        update: function (event) {
            var id = getSelectedRow();
            if (id == null) {
                return;
            }
            vm.isCreate = false;
            vm.getSysRoles();
            vm.sysUser.roleIdList = [];
            vm.showList = false;
            vm.title = "更新";
            vm.getDetail(id);
        },
        change: function (event) {
            var id = getSelectedRow();
            if (id == null) {
                return;
            }
            vm.sysUser.id = id;
            vm.sysUser.password = "";
            vm.newPassword = "";
            vm.showList = false;
            vm.showChange = true;
            vm.title = "パスワード更新";
        },
        changePassword: function (event) {
            if (!vm.newPassword) {
                alert("新パスワードを入力してください。");
                return;
            }
            if (!vm.sysUser.password) {
                alert("もう一度パスワードを入力してください。");
                return;
            }
            if (vm.newPassword != vm.sysUser.password) {
                alert("パスワード一致ではありません。")
                return;
            }
            $.ajax({
                type: "POST",
                url: apiUrl + "sysUser/changePassword",
                contentType: "application/json",
                data: JSON.stringify(vm.sysUser),
                success: function (r) {
                    if (r.code === 0) {
                        alert('パスワード更新成功', function (index) {
                            vm.reload();
                        });
                    } else {
                        alert(r.message);
                    }
                }
            });
        },
        createOrUpdate: function (event) {
            var url = vm.sysUser.id == null ? "sysUser/create" : "sysUser/update";
            if (!vm.sysUser.username) {
                alert("ユーザー名を入力してください。");
                return;
            }
            if (vm.sysUser.email && !validator.isEmail(vm.sysUser.email)) {
                alert("メールフォーマット不正。");
                return;
            }
            if (vm.sysUser.roleIdList.length == 0) {
                alert("ユーザー役柄を選択してください!");
                return;
            }
            $.ajax({
                type: "POST",
                url: apiUrl + url,
                contentType: "application/json",
                data: JSON.stringify(vm.sysUser),
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
                    url: apiUrl + "sysUser/delete/" + ids.join(","),
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
                url: apiUrl + "sysUser/detail/" + id,
                contentType: "application/json",
                success: function (r) {
                    if (r.code == 0) {
                        vm.sysUser = r.data;
                        vm.getUserRoles(id);
                    } else {
                        alert(r.message);
                    }
                }
            });
        },
        getSysRoles: function () {
            $.ajax({
                type: "GET",
                url: apiUrl + "sysRole/list",
                contentType: "application/json",
                success: function (r) {
                    if (r.code == 0) {
                        vm.sysRoles = r.data.list;
                    }
                }
            });
        },
        getUserRoles: function (id) {
            $.ajax({
                type: "GET",
                url: apiUrl + "sysRole/listByUserId?userId=" + id,
                contentType: "application/json",
                success: function (r) {
                    if (r.code == 0) {
                        vm.$set(vm.sysUser, "roleIdList", r.data);
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
            vm.showChange = false;
            vm.showList = true;
            vm.isCreate = false;
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
