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
        organization: {
            parentName: null,
            parentId: 0,
            orderNum: 0
        },
        orgData: [],
        ztree: null,

    },
    methods: {
        getOrganization: function () {
            $.get(apiUrl + "mastOrganization/select", function (r) {
                vm.orgData = r.data;
                vm.ztree = $.fn.zTree.init($("#orgTree"), setting, r.data);
                var node = vm.ztree.getNodeByParam("id", vm.organization.parentId);
                vm.ztree.selectNode(node);
                vm.$set(vm.organization, "parentName", vm.findParent(vm.organization.parentId));
            })
        },
        findParent: function (id) {
            console.log(vm.orgData);
            for (var i = 0; i < vm.orgData.length; i++) {
                if (vm.orgData[i].id === id) {
                    return vm.orgData[i].name;
                }
            }
            return "";
        },
        create: function () {
            vm.showList = false;
            vm.title = "新規";
            vm.organization = {parentName: null, parentId: 0, orderNum: 0};
            vm.getOrganization();
        },
        update: function () {
            var deptId = getOrganizationId();
            if (deptId == null) {
                return;
            }

            $.get(apiUrl + "mastOrganization/detail/" + deptId, function (r) {
                vm.showList = false;
                vm.title = "更新";
                vm.organization = r.data;
                vm.getOrganization();
            });
        },
        del: function () {
            var ids = getSelectedRows();
            if(ids == null) {
                return ;
            }

            confirm('レコード削除しますか？', function () {
                $.ajax({
                    type: "GET",
                    url: apiUrl + "mastOrganization/delete/" + ids.join(","),
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
        createOrUpdate: function (event) {
            var url = vm.organization.id == null ? "mastOrganization/create" : "mastOrganization/update";
            $.ajax({
                type: "POST",
                url: apiUrl + url,
                contentType: "application/json",
                data: JSON.stringify(vm.organization),
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
        },
        reload: function () {
            vm.showList = true;
            Organization.table.refresh();
        },
        orgTree: function () {
            layer.open({
                type: 1,
                offset: '50px',
                skin: 'layui-layer-molv',
                title: "部門選択",
                area: ['300px', '450px'],
                shade: 0,
                shadeClose: false,
                content: jQuery("#deptLayer"),
                btn: ['確定', 'キャンセル'],
                btn1: function (index) {
                    var node = vm.ztree.getSelectedNodes();
                    vm.organization.parentId = node[0].id;
                    vm.organization.parentName = node[0].name;

                    layer.close(index);
                }
            });
        }
    }
});

var Organization = {
    id: "orgTable",
    table: null,
    layerIndex: -1
};

function onClick(event, treeId, treeNode, clickFlag) {
    if (treeNode.isParent) {
        $.fn.zTree.getZTreeObj(treeId).expandNode(treeNode);
    }
}
function getOrganizationId() {
    var selected = $('#orgTable').bootstrapTreeTable('getSelections');
    if (selected.length == 0) {
        return false;
    } else {
        return selected[0].id;
    }
}

$(function () {
    $.get(apiUrl + "mastOrganization/getOrgParentId", function (r) {
        var columns = [
            {field: 'selectItem', radio: true},
            {title: 'ID', field: 'id', visible: false, align: 'center', valign: 'middle', width: '80px'},
            {title: '部門名称', field: 'name', align: 'center', valign: 'middle', sortable: true, width: '180px'},
            {title: '部門コード', field: 'code', align: 'center', valign: 'middle', sortable: true, width: '70px'},
            {title: '説明', field: 'description', align: 'center', valign: 'middle', sortable: true, width: '120px'},
            {title: '順番', field: 'orderNum', align: 'center', valign: 'middle', sortable: true, width: '100px'}]
        var table = new TreeTable(Organization.id, apiUrl + "mastOrganization/listAll", columns);
        table.setRootCodeValue(r.data);
        table.setExpandColumn(2);
        table.setIdField("id");
        table.setCodeField("id");
        table.setParentCodeField("parentId");
        table.setExpandAll(false);
        table.init();
        Organization.table = table;
    });
});
