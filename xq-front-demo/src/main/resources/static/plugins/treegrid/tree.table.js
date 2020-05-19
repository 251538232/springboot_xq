(function () {
    var TreeTable = function (tableId, url, columns) {
        this.btInstance = null;
        this.bstableId = tableId;
        this.url = url;
        this.method = "GET";
        this.columns = columns;
        this.data = {};
        this.expandColumn = null;
        this.id = 'id';
        this.code = 'id';
        this.parentCode = 'parentId';
        this.expandAll = false;
        this.toolbarId = tableId + "Toolbar";
        this.height = 430;
    };

    TreeTable.prototype = {
        init: function () {
            var tableId = this.bstableId;
            this.btInstance =
                $('#'+tableId).bootstrapTreeTable({
                    id: this.id,
                    code: this.code,
                    parentCode: this.parentCode,
                    rootCodeValue: this.rootCodeValue,
                    type: this.method,
                    url: this.url,
                    ajaxParams: this.data,
                    expandColumn: this.expandColumn,
                    striped: true,
                    expandAll: this.expandAll,
                    columns: this.columns,
                    toolbar: "#" + this.toolbarId,
                    height: this.height,
                });
            return this;
        },

        setExpandColumn: function (expandColumn) {
            this.expandColumn = expandColumn;
        },
        setIdField: function (id) {
            this.id = id;
        },
        setCodeField: function (code) {
            this.code = code;
        },
        setParentCodeField: function (parentCode) {
            this.parentCode = parentCode;
        },
        setRootCodeValue: function (rootCodeValue) {
            this.rootCodeValue = rootCodeValue;
        },
        setExpandAll: function (expandAll) {
        	this.expandAll = expandAll;
        },
        setHeight: function (height) {
        	this.height = height;
        },
        set: function (key, value) {
            if (typeof key == "object") {
                for (var i in key) {
                    if (typeof i == "function")
                        continue;
                    this.data[i] = key[i];
                }
            } else {
                this.data[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
            }
            return this;
        },

        setData: function (data) {
            this.data = data;
            return this;
        },

        clear: function () {
            this.data = {};
            return this;
        },
        refresh: function (parms) {
            if (typeof parms != "undefined") {
                this.btInstance.bootstrapTreeTable('refresh', parms.query);// 为了兼容bootstrap-table的写法
            } else {
                this.btInstance.bootstrapTreeTable('refresh');
            }
        }
    };

    window.TreeTable = TreeTable;

}());