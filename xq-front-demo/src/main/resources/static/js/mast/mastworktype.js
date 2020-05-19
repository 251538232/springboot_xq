$(function () {
    $("#gridTable").jqGrid({
        url: apiUrl + 'mastWorktype/list?access_token=' + getToken("access_token"),
        datatype: "json",
        colModel: [
			{ label: 'ＩＤ', name: 'id', width: 20 },
			{ label: 'タイプコード', name: 'code', width: 80 },
			{ label: 'タイプ名', name: 'name', width: 80 },
			{ label: '勤務開始時間', name: 'workStartHm', width: 80 },
			{ label: '勤務終了時間', name: 'workEndHm', width: 80 },
			{ label: '基本出勤時間', name: 'workHours', width: 80 }, 
			{ label: '昼間休憩開始時間', name: 'dayRestStartHm', width: 80 }, 
			{ label: '昼間休憩終了時間', name: 'dayRestEndHm', width: 80 }, 
			{ label: '休暇時間(H)', name: 'restHours', width: 80 },
			{ label: '残業開始時間', name: 'extraStartHm', width: 80 }, 
			{ label: '説明', name: 'content', width: 80 },
			{ label: '状態', name: 'disable', width: 80 }, 
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
			code:"",
	        codeCondition: "like", 
			name:"",
	        nameCondition: "like", 
			workStartHm:"",
	        workStartHmCondition: "like", 
			workEndHm:"",
	        workEndHmCondition: "like", 
			workHours:"",
			workHoursCondition: "=", 
			dayRestStartHm:"",
	        dayRestStartHmCondition: "like", 
			dayRestEndHm:"",
	        dayRestEndHmCondition: "like", 
			restHours:"",
			restHoursCondition: "=", 
			extraStartHm:"",
	        extraStartHmCondition: "like", 
			content:"",
	        contentCondition: "like", 
			disable:"",
			disableCondition: "=", 
			createTime:"",
			createTimeCondition: "=", 
			updateTime:"",
			updateTimeCondition: "="
		},
		showList: true,
		title: null,
		mastWorktype: {}
	},
	methods: {
		select: function () {
			vm.reload();
		},
		create: function() {
			vm.showList = false;
			vm.title = "新規";
			vm.mastWorktype = {};
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
			var url = vm.mastWorktype.id == null ? "mastWorktype/create" : "mastWorktype/update";
			$.ajax({
				type: "POST",
			    url: apiUrl + url,
			    contentType: "application/json",
			    data: JSON.stringify(vm.mastWorktype),
			    success: function(r) {
			    	if(r.code === 0) {
						var msg = vm.mastWorktype.id == null ? "データ新規成功しました" : "データ更新成功しました";
						alert(msg, function(index) {
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
				    url: apiUrl + "mastWorktype/delete/" + ids.join(","),
				    success: function(r) {
						if(r.code == 0) {
							alert('`削除成功しました。', function(index) {
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
				url: apiUrl + "mastWorktype/detail/"+id,
                success: function(r) {
					if(r.code == 0) {
                        vm.mastWorktype = r.data;
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