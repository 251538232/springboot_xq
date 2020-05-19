$(function () {
    $("#gridTable").jqGrid({
        url: apiUrl + 'sysArea/list?access_token=' + getToken("access_token"),
        datatype: "json",
        colModel: [
			{ label: '', name: 'id', width: 80 }, 
			{ label: '', name: 'kenId', width: 80 }, 
			{ label: '', name: 'cityId', width: 80 }, 
			{ label: '', name: 'townId', width: 80 }, 
			{ label: '', name: 'zip', width: 80 }, 
			{ label: '', name: 'officeFlg', width: 80 }, 
			{ label: '', name: 'deleteFlg', width: 80 }, 
			{ label: '', name: 'kenName', width: 80 }, 
			{ label: '', name: 'kenFuri', width: 80 }, 
			{ label: '', name: 'cityName', width: 80 }, 
			{ label: '', name: 'cityFuri', width: 80 }, 
			{ label: '', name: 'townName', width: 80 }, 
			{ label: '', name: 'townFuri', width: 80 }, 
			{ label: '', name: 'townMemo', width: 80 }, 
			{ label: '', name: 'kyotoStreet', width: 80 }, 
			{ label: '', name: 'blockName', width: 80 }, 
			{ label: '', name: 'blockFuri', width: 80 }, 
			{ label: '', name: 'memo', width: 80 }, 
			{ label: '', name: 'officeName', width: 80 }, 
			{ label: '', name: 'officeFuri', width: 80 }, 
			{ label: '', name: 'officeAddress', width: 80 }, 
			{ label: '', name: 'newId', width: 80 }
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
			kenId:"",
			kenIdCondition: "=", 
			cityId:"",
			cityIdCondition: "=", 
			townId:"",
			townIdCondition: "=", 
			zip:"",
	        zipCondition: "like", 
			officeFlg:"",
			officeFlgCondition: "=", 
			deleteFlg:"",
			deleteFlgCondition: "=", 
			kenName:"",
	        kenNameCondition: "like", 
			kenFuri:"",
	        kenFuriCondition: "like", 
			cityName:"",
	        cityNameCondition: "like", 
			cityFuri:"",
	        cityFuriCondition: "like", 
			townName:"",
	        townNameCondition: "like", 
			townFuri:"",
	        townFuriCondition: "like", 
			townMemo:"",
	        townMemoCondition: "like", 
			kyotoStreet:"",
	        kyotoStreetCondition: "like", 
			blockName:"",
	        blockNameCondition: "like", 
			blockFuri:"",
	        blockFuriCondition: "like", 
			memo:"",
	        memoCondition: "like", 
			officeName:"",
	        officeNameCondition: "like", 
			officeFuri:"",
	        officeFuriCondition: "like", 
			officeAddress:"",
	        officeAddressCondition: "like", 
			newId:"",
	        newIdCondition: "like"
		},
		showList: true,
		title: null,
		sysArea: {}
	},
	methods: {
		select: function () {
			vm.reload();
		},
		create: function() {
			vm.showList = false;
			vm.title = "新規";
			vm.sysArea = {};
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
			var url = vm.sysArea.id == null ? "sysArea/create" : "sysArea/update";
			$.ajax({
				type: "POST",
			    url: apiUrl + url,
			    contentType: "application/json",
			    data: JSON.stringify(vm.sysArea),
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
				    url: apiUrl + "sysArea/delete/" + ids.join(","),
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
				url: apiUrl + "sysArea/detail/"+id,
                success: function(r) {
					if(r.code == 0) {
                        vm.sysArea = r.data;
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