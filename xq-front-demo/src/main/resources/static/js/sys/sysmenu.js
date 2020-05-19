var setting = {
    data: {
        simpleData: {
            enable: true,
            idKey: "id",
            pIdKey: "parentId",
            rootPId: -1
        },
        key: {
            url: "nourl"
        }
    }
};

var vm = new Vue({
    el: '#webapp',
    data: {
        showList: true,
        title: null,
        menu: {
            parentName: null,
            parentId: 0,
            type: 1,
            orderNum: 0
        },
        menuData: [],
        ztree: null
    },
    methods: {
        getMenu: function () {
            $.ajax({
                type: "GET",
                url: apiUrl + "sysMenu/select",
                success: function (r) {
                    vm.menuData = r.data;
                    vm.ztree = $.fn.zTree.init($("#menuTree"), setting, vm.menuData);
                    var node = vm.ztree.getNodeByParam("menuId", vm.menu.parentId);
                    vm.ztree.selectNode(node);
                    vm.$set(vm.menu, "parentName", vm.findParent(vm.menu.parentId));
                }
            });
        },
        create: function () {
            vm.showList = false;
            vm.title = "新規";
            vm.menu = {parentName: null, parentId: 0, type: 1, orderNum: 0};
            vm.getMenu();
        },
        update: function () {
            var id = getMenuId();
            if (id == null) {
                return;
            }
            $.ajax({
                type: "GET",
                url: apiUrl + "sysMenu/detail/" + id,
                success: function (r) {
                    vm.showList = false;
                    vm.title = "更新";
                    vm.menu = r.data;

                    vm.getMenu();
                }
            });
        },
        findParent: function (id) {
            for (var i = 0; i < vm.menuData.length; i++) {
                if (vm.menuData[i].id === id) {
                    return vm.menuData[i].name;
                }
            }
            return "";
        },
        del: function () {
            var ids = getSelectedRows();
            if(ids == null) {
                return ;
            }

            confirm('選択したデータを削除しますか？', function () {
                $.ajax({
                    type: "GET",
                    url: apiUrl + "sysMenu/delete/" +  + ids.join(","),
                    success: function (r) {
                        if (r.code === 0) {
                            alert('操作成功', function () {
                                vm.reload();
                            });
                        } else {
                            alert(r.message);
                        }
                    }
                });
            });
        },
        createOrUpdate: function () {
            if (vm.validator()) {
                return;
            }

            var url = vm.menu.id == null ? "sysMenu/create" : "sysMenu/update";
            $.ajax({
                type: "POST",
                url: apiUrl + url,
                data: JSON.stringify(vm.menu),
                success: function (r) {
                    if (r.code === 0) {
                        alert('成功', function () {
                            vm.reload();
                        });
                    } else {
                        alert(r.message);
                    }
                }
            });
        },
        menuTree: function () {
            layer.open({
                type: 1,
                offset: '50px',
                skin: 'layui-layer-molv',
                title: "メニュー選択",
                area: ['300px', '450px'],
                shade: 0,
                shadeClose: false,
                content: jQuery("#menuLayer"),
                btn: ['確定', '取消'],
                btn1: function (index) {
                    var node = vm.ztree.getSelectedNodes();
                    // 親メニューを選択
                    vm.menu.parentId = node[0].id;
                    vm.menu.parentName = node[0].name;

                    layer.close(index);
                }
            });
        },
        reload: function () {
            vm.showList = true;
            Menu.table.refresh();
        },
        validator: function () {
            if (isBlank(vm.menu.name)) {
                alert("メニュー名称を入力してください。");
                return true;
            }
            if (vm.menu.type === 1 && isBlank(vm.menu.url)) {
                alert("メニューURLを入力してください。");
                return true;
            }
        }
    }
});


var Menu = {
    id: "menuTable",
    table: null,
    layerIndex: -1
};

Menu.initColumn = function () {
    var columns = [
        {field: 'selectItem', radio: true},
        {title: '名称', field: 'name', align: 'center', valign: 'middle', sortable: true, width: '180px'},
        {
            title: 'アイコン',
            field: 'icon',
            align: 'center',
            valign: 'middle',
            sortable: true,
            width: '80px',
            formatter: function (item, index) {
                return item.icon == null ? '' : '<i class="' + item.icon + ' fa-lg"></i>';
            }
        },
        {
            title: '種類',
            field: 'type',
            align: 'center',
            valign: 'middle',
            sortable: true,
            width: '150px',
            formatter: function (item, index) {
                if (item.type === 0) {
                    return '<span class="label label-primary">カテゴリー</span>';
                }
                if (item.type === 1) {
                    return '<span class="label label-success">メニュー</span>';
                }
                if (item.type === 2) {
                    return '<span class="label label-warning">ボタン</span>';
                }
            }
        },
        {title: '順番', field: 'orderNum', align: 'center', valign: 'middle', sortable: true, width: '100px'},
        {title: 'メニューURL', field: 'url', align: 'center', valign: 'middle', sortable: true, width: '160px'},
        {title: 'Shiro権限', field: 'permission', align: 'center', valign: 'middle', sortable: true}]
    return columns;
};

function getMenuId() {
    var selected = $('#menuTable').bootstrapTreeTable('getSelections');
    if (selected.length == 0) {
        alert("一つのメニューを選択してください。");
        return false;
    } else {
        return selected[0].id;
    }
}

//
// $(function () {
//     var colunms = Menu.initColumn();
//     var table = new TreeTable(Menu.id, apiUrl + "sysMenu/list?access_token=" + getToken("access_token"), colunms);
//     table.setExpandColumn(2);
//     table.setIdField("id");
//     table.setCodeField("id");
//     table.setParentCodeField("parentId");
//     table.setExpandAll(false);
//     table.init();
//     Menu.table = table;
// });
