$(function () {
    $("#gridTable").jqGrid({
        url: apiUrl + 'mastPersonal/list?access_token=' + getToken("access_token"),
        datatype: "json",
        colModel: [
			{ label: '個人情報ＩＤ', name: 'id', width: 80 }, 
			{ label: '', name: 'employeeId', width: 80 }, 
			{ label: 'システム上の表示名', name: 'dispName', width: 80 }, 
			{ label: '名前', name: 'name', width: 80 }, 
			{ label: 'カナ氏名', name: 'kanaName', width: 80 }, 
			{ label: '英語名', name: 'englishName', width: 80 }, 
			{ label: '誕生日', name: 'birthday', width: 80 }, 
			{ label: '性別', name: 'sex', width: 80 }, 
			{ label: '住所アドレス', name: 'address', width: 80 }, 
			{ label: '社員写真URL', name: 'photoUrl', width: 80 }, 
			{ label: 'メール', name: 'email', width: 80 }, 
			{ label: '携帯番号', name: 'mobile', width: 80 }, 
			{ label: 'wechat id', name: 'wechat', width: 80 }, 
			{ label: 'Line id', name: 'line', width: 80 }, 
			{ label: '電車線', name: 'tramLine', width: 80 }, 
			{ label: '最寄り駅', name: 'nearStation', width: 80 }, 
			{ label: '通勤時間', name: 'commutingMinus', width: 80 }, 
			{ label: '血液型', name: 'bloodGroup', width: 80 }, 
			{ label: '国籍', name: 'nationality', width: 80 }, 
			{ label: '旧名前', name: 'oldName', width: 80 }, 
			{ label: '旧名前仮名', name: 'oldJpName', width: 80 }, 
			{ label: '旧名前英語', name: 'oldEnName', width: 80 }, 
			{ label: '名前変更時間', name: 'changeNameTime', width: 80 }, 
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
			employeeId:"",
			employeeIdCondition: "=", 
			dispName:"",
	        dispNameCondition: "like", 
			name:"",
	        nameCondition: "like", 
			kanaName:"",
	        kanaNameCondition: "like", 
			englishName:"",
	        englishNameCondition: "like", 
			birthday:"",
			birthdayCondition: "=", 
			sex:"",
	        sexCondition: "like", 
			address:"",
	        addressCondition: "like", 
			photoUrl:"",
	        photoUrlCondition: "like", 
			email:"",
	        emailCondition: "like", 
			mobile:"",
	        mobileCondition: "like", 
			wechat:"",
	        wechatCondition: "like", 
			line:"",
	        lineCondition: "like", 
			tramLine:"",
	        tramLineCondition: "like", 
			nearStation:"",
	        nearStationCondition: "like", 
			commutingMinus:"",
			commutingMinusCondition: "=", 
			bloodGroup:"",
	        bloodGroupCondition: "like", 
			nationality:"",
	        nationalityCondition: "like", 
			oldName:"",
	        oldNameCondition: "like", 
			oldJpName:"",
	        oldJpNameCondition: "like", 
			oldEnName:"",
	        oldEnNameCondition: "like", 
			changeNameTime:"",
			changeNameTimeCondition: "=", 
			remark:"",
	        remarkCondition: "like", 
			createTime:"",
			createTimeCondition: "=", 
			updateTime:"",
			updateTimeCondition: "="
		},
		showList: true,
		title: null,
		mastPersonal: {}
	},
	methods: {
		select: function () {
			vm.reload();
		},
		create: function() {
			vm.showList = false;
			vm.title = "新規";
			vm.mastPersonal = {};
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
			var url = vm.mastPersonal.id == null ? "mastPersonal/create" : "mastPersonal/update";
			$.ajax({
				type: "POST",
			    url: apiUrl + url,
			    contentType: "application/json",
			    data: JSON.stringify(vm.mastPersonal),
			    success: function(r) {
			    	if(r.code === 0) {
						var msg = vm.mastPersonal.id == null ? "データ新規成功しました" : "データ更新成功しました";
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
				    url: apiUrl + "mastPersonal/delete/" + ids.join(","),
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
				url: apiUrl + "mastPersonal/detail/"+id,
                success: function(r) {
					if(r.code == 0) {
                        vm.mastPersonal = r.data;
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