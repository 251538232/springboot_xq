$(function () {
    $("#gridTable").jqGrid({
        url: apiUrl + 'sysConfig/list?access_token=' + getToken("access_token"),
        datatype: "json",
        colModel: [
			{ label: 'id', name: 'id', width: 80 }, 
			{ label: '種類', name: 'type', width: 80 }, 
			{ label: 'key', name: 'paramKey', width: 80 }, 
			{ label: 'value', name: 'paramValue', width: 80 }, 
			{ label: '1:disable 0:enable', name: 'disable', width: 80 }, 
			{ label: 'description', name: 'description', width: 80 }, 
			{ label: '作成時間', name: 'createTime', width: 80 }, 
			{ label: '更新時間', name: 'updateTime', width: 80 }
        ],
		sortname: 'id',
        // 行目表示
		viewrecords: true,
        // ディフォルトオーダー順番
		// sortorder: "asc",
        height: screen.availHeight - 400,
        width: screen.availWidth,
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true,
        rownumWidth: 25,
        autowidth: false,
        multiselect: true,
        shrinkToFit: true,
        pager: "#gridPager",
        jsonReader : {
            root: "data.list",
            page: "data.currPage",
            total: "data.totalPage",
            records: "data.totalCount"
        },
        prmNames : {
            page:"page",
            rows:"limit",
            order: "order"
        },
		// ロード完了した場合
        gridComplete:function(){
            $("#gridTable").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
            $("#gridTable").trigger("resize");
        }
    });
});

var vm = new Vue({
	el:'#webapp',
	data:{
	    q: {
			id:"",
			idCondition: "=", 
			type:"",
	        typeCondition: "like", 
			paramKey:"",
	        paramKeyCondition: "like", 
			paramValue:"",
	        paramValueCondition: "like", 
			disable:"",
			disableCondition: "=", 
			description:"",
	        descriptionCondition: "like", 
			createTime:"",
			createTimeCondition: "=", 
			updateTime:"",
			updateTimeCondition: "="
		},
		showList: true,
		title: null,
		sysConfig: {}
	},
	methods: {
		select: function () {
			vm.reload();
		},
		create: function() {
			vm.showList = false;
			vm.title = "新規";
			vm.sysConfig = {};
		},
		update: function (event) {
			var id = getSelectedRow();
			if(id == null){
				return ;
			}
			vm.showList = false;
            vm.title = "更新";

            vm.getDetail(id)
		},
        createOrUpdate: function (event) {
			var url = vm.sysConfig.id == null ? "sysConfig/create" : "sysConfig/update";
			$.ajax({
				type: "POST",
			    url: apiUrl + url,
			    contentType: "application/json",
			    data: JSON.stringify(vm.sysConfig),
			    success: function(r) {
			    	if(r.code === 0) {
						alert('成功', function(index) {
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
			if(ids == null) {
				return ;
			}

			confirm('選択したデータを削除しますか？', function() {
				$.ajax({
					type: "GET",
				    url: apiUrl + "sysConfig/delete/" + ids.join(","),
				    success: function(r) {
						if(r.code == 0) {
							alert('成功', function(index) {
								$("#gridTable").trigger("reloadGrid");
							});
						} else {
							alert(r.message);
						}
					}
				});
			});
		},
		getDetail: function(id) {
            $.ajax({
				type: "GET",
				url: apiUrl + "sysConfig/detail/"+id,
                success: function(r) {
					if(r.code == 0) {
                        vm.sysConfig = r.data;
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
			var page = $("#gridTable").jqGrid('getGridParam','page');
			$("#gridTable").jqGrid('setGridParam', {
			    postData:condition,
                page:page
            }).trigger("reloadGrid");
		}
	}
});