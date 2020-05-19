$(function () {
    $("#gridTable").jqGrid({
        url: apiUrl + 'empHoliday/list?access_token=' + getToken("access_token"),
        datatype: "json",
        colModel: [
			{ label: 'システムユーザーＩＤ', name: 'userId', width: 80 },
			{ label: '全ての休暇日数', name: 'allDays', width: 80 }, 
			{ label: 'もう休みの日数', name: 'usedDays', width: 80 }, 
			{ label: '未休の日数 ', name: 'leftDays', width: 80 }, 
			{ label: '期限切れの日数 ', name: 'outDays', width: 80 }, 
			{ label: '休暇開始日', name: 'start', width: 80 }, 
			{ label: '休暇终了日', name: 'end', width: 80 }, 
			{ label: '休暇タイプ\n0:普通休暇\n１：年次有給休暇', name: 'type', width: 80 }, 
			{ label: '補足など', name: 'remark', width: 80 }, 
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
			userId:"",
			userIdCondition: "=", 
			allDays:"",
			allDaysCondition: "=", 
			usedDays:"",
			usedDaysCondition: "=", 
			leftDays:"",
			leftDaysCondition: "=", 
			outDays:"",
			outDaysCondition: "=", 
			start:"",
			startCondition: "=", 
			end:"",
			endCondition: "=", 
			type:"",
			typeCondition: "=", 
			remark:"",
	        remarkCondition: "like", 
			createTime:"",
			createTimeCondition: "=", 
			updateTime:"",
			updateTimeCondition: "="
		},
		showList: true,
		title: null,
		empHoliday: {}
	},
	methods: {
		select: function () {
			vm.reload();
		},
		create: function() {
			vm.showList = false;
			vm.title = "新規";
			vm.empHoliday = {};
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
			var url = vm.empHoliday.id == null ? "empHoliday/create" : "empHoliday/update";
			$.ajax({
				type: "POST",
			    url: apiUrl + url,
			    contentType: "application/json",
			    data: JSON.stringify(vm.empHoliday),
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
				    url: apiUrl + "empHoliday/delete/" + ids.join(","),
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
				url: apiUrl + "empHoliday/detail/"+id,
                success: function(r) {
					if(r.code == 0) {
                        vm.empHoliday = r.data;
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