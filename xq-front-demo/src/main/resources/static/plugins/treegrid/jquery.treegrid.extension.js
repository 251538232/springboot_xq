(function($) {
    "use strict";

    $.fn.bootstrapTreeTable = function(options, param) {
        if (typeof options == 'string') {
            return $.fn.bootstrapTreeTable.methods[options](this, param);
        }

        options = $.extend({}, $.fn.bootstrapTreeTable.defaults, options || {});
        var hasSelectItem = false;
        var target = $(this);
        var _main_div = $("<div class='fixed-table-container'></div>");
        target.before(_main_div);
        _main_div.append(target);
        target.addClass("table table-hover treegrid-table table-bordered");
        if (options.striped) {
            target.addClass('table-striped');
        }
        if(options.toolbar){
            var _tool_div = $("<div class='fixed-table-toolbar'></div>");
            var _tool_left_div = $("<div class='bs-bars pull-left'></div>");
            _tool_left_div.append($(options.toolbar));
            _tool_div.append(_tool_left_div);
            _main_div.before(_tool_div);
        }
        target.getRootNodes = function(data) {
            var _root = options.rootCodeValue?options.rootCodeValue:null
            var result = [];
            $.each(data, function(index, item) {
                var _defaultRootFlag = item[options.parentCode] == '0'
                    || item[options.parentCode] == 0
                    || item[options.parentCode] == null
                    || item[options.parentCode] == '';
                if (!item[options.parentCode] || (_root?(item[options.parentCode] == options.rootCodeValue):_defaultRootFlag)){
                    result.push(item);
                }
                item.isShow = false;
            });
            return result;
        };
        var j = 0;
        target.getChildNodes = function(data, parentNode, parentIndex, tbody) {
            $.each(data, function(i, item) {
                if (item[options.parentCode] == parentNode[options.code]) {
                    var tr = $('<tr></tr>');
                    var nowParentIndex = (parentIndex + (j++) + 1);
                    tr.addClass('treegrid-' + nowParentIndex);
                    tr.addClass('treegrid-parent-' + parentIndex);
                    target.renderRow(tr,item);
                    item.isShow = true;
                    tbody.append(tr);
                    target.getChildNodes(data, item, nowParentIndex, tbody)

                }
            });
        };
        target.renderRow = function(tr,item){
            $.each(options.columns, function(index, column) {
                if(index==0&&column.field=='selectItem'){
                    hasSelectItem = true;
                    var td = $('<td style="text-align:center;width:36px"></td>');
                    if(column.radio){
                        var _ipt = $('<input name="select_item" type="radio" value="'+item[options.id]+'"></input>');
                        td.append(_ipt);
                    }
                    if(column.checkbox){
                        var _ipt = $('<input name="select_item" type="checkbox" value="'+item[options.id]+'"></input>');
                        td.append(_ipt);
                    }
                    tr.append(td);
                }else{
                    var td = $('<td style="'+((column.width)?('width:'+column.width):'')+'"></td>');
                    if (column.formatter) {
                        td.html(column.formatter.call(this, item, index));
                    } else {
                        td.text(item[column.field]);
                    }
                    tr.append(td);
                }
            });
        }
        target.load = function(parms){
            // Load前にクリア
            target.html("");
            var thr = $('<tr></tr>');
            $.each(options.columns, function(i, item) {
                var th = null;
                if(i==0&&item.field=='selectItem'){
                    hasSelectItem = true;
                    th = $('<th style="width:36px"></th>');
                }else{
                    th = $('<th style="padding:10px;'+((item.width)?('width:'+item.width):'')+'"></th>');
                }
                th.text(item.title);
                thr.append(th);
            });
            var thead = $('<thead class="treegrid-thead"></thead>');
            thead.append(thr);
            target.append(thead);
            var tbody = $('<tbody class="treegrid-tbody"></tbody>');
            target.append(tbody);
            // loading
            var _loading = '<tr><td colspan="'+options.columns.length+'"><div style="display: block;text-align: center;">データ読み込み中……</div></td></tr>'
            tbody.html(_loading);
            if(options.height){
                tbody.css("height",options.height);
            }
            $.ajax({
                type : options.type,
                url : options.url,
                data : parms?parms:options.ajaxParams,
                dataType : "JSON",
                success : function(data, textStatus, jqXHR) {
                    data = data.data;
                    tbody.html("");
                    if(!data||data.length<=0){
                        var _empty = '<tr><td colspan="'+options.columns.length+'"><div style="display: block;text-align: center;">データなし</div></td></tr>'
                        tbody.html(_empty);
                        return;
                    }
                    var rootNode = target.getRootNodes(data);
                    $.each(rootNode, function(i, item) {
                        var tr = $('<tr></tr>');
                        tr.addClass('treegrid-' + (j + "_" + i));
                        target.renderRow(tr,item);
                        item.isShow = true;
                        tbody.append(tr);
                        target.getChildNodes(data, item, (j + "_" + i), tbody);
                    });
                    $.each(data, function(i, item) {
                        if(!item.isShow){
                            var tr = $('<tr></tr>');
                            tr.addClass('treegrid-' + (j + "_" + i));
                            target.renderRow(tr,item);
                            tbody.append(tr);
                        }
                    });
                    target.append(tbody);
                    target.treegrid({
                        treeColumn: options.expandColumn?options.expandColumn:(hasSelectItem?1:0),
                        expanderExpandedClass : options.expanderExpandedClass,
                        expanderCollapsedClass : options.expanderCollapsedClass
                    });
                    if (!options.expandAll) {
                        target.treegrid('collapseAll');
                    }
                    target.find("tbody").find("tr").click(function(){
                        if(hasSelectItem){
                            var _ipt = $(this).find("input[name='select_item']");
                            if(_ipt.attr("type")=="radio"){
                                _ipt.prop('checked',true);
                                target.find("tbody").find("tr").removeClass("treegrid-selected");
                                $(this).addClass("treegrid-selected");
                            }else{
                                if(_ipt.prop('checked')){
                                    _ipt.prop('checked',false);
                                    $(this).removeClass("treegrid-selected");
                                }else{
                                    _ipt.prop('checked',true);
                                    $(this).addClass("treegrid-selected");
                                }
                            }
                        }
                    });
                },
                error:function(xhr,textStatus){
                    var _errorMsg = '<tr><td colspan="'+options.columns.length+'"><div style="display: block;text-align: center;">'+xhr.responseText+'</div></td></tr>'
                    tbody.html(_errorMsg);
                    debugger;
                },
            });
        }
        if (options.url) {
            target.load();
        } else {
        }

        return target;
    };

    $.fn.bootstrapTreeTable.methods = {
        getSelections : function(target, data) {
            var _ipt = target.find("tbody").find("tr").find("input[name='select_item']:checked");
            var chk_value =[];
            if(_ipt.attr("type")=="radio"){
                chk_value.push({id:_ipt.val()});
            }else{
                _ipt.each(function(_i,_item){
                    chk_value.push({id:$(_item).val()});
                });
            }
            return chk_value;
        },
        refresh : function(target, parms) {
            if(parms){
                target.load(parms);
            }else{
                target.load();
            }
        },
    };

    $.fn.bootstrapTreeTable.defaults = {
        id : 'id',// 选取记录返回的值
        code : 'id',// 用于设置父子关系
        parentCode : 'parentId',// 用于设置父子关系
        rootCodeValue: null,//设置根节点code值----可指定根节点，默认为null,"",0,"0"
        data : [], // 构造table的数据集合
        type : "GET", // 请求数据的ajax类型
        url : null, // 请求数据的ajax的url
        ajaxParams : {}, // 请求数据的ajax的data属性
        expandColumn : null,// 在哪一列上面显示展开按钮
        expandAll : true, // 是否全部展开
        striped : false, // 是否各行渐变色
        columns : [],
        toolbar: null,//顶部工具条
        height: 0,
        expanderExpandedClass : 'glyphicon glyphicon-chevron-down',// 展开的按钮的图标
        expanderCollapsedClass : 'glyphicon glyphicon-chevron-right'// 缩起的按钮的图标

    };
})(jQuery);