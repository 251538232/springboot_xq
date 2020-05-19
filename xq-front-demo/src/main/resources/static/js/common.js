$.jgrid.defaults.width = 1000;
$.jgrid.defaults.responsive = true;
$.jgrid.defaults.styleUI = 'Bootstrap';

var apiUrl = getCookie("ipPort") == "" ? "http://localhost:8082/" : "http://" + getCookie("ipPort") + "/";

window.T = {};

var url = function (name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null)return unescape(r[2]);
    return null;
};
T.p = url;

window.alert = function (msg, callback) {
    var layer = parent.layer === undefined ? layui.layer : top.layer;
    layer.alert(msg, function (index) {
        layer.close(index);
        if (typeof(callback) === "function") {
            callback("ok");
        }
    });
}

window.confirm = function (msg, callback) {
    var layer = parent.layer === undefined ? layui.layer : top.layer;
    layer.confirm(msg,
        {btn: ['はい', 'いいえ']},
        function () {
            console.log(typeof(callback));
            if (typeof(callback) === "function") {
                callback("ok");
            }
        });
}

function getSelectedRow() {
    var grid = $("#gridTable");
    var rowKey = grid.getGridParam("selrow");
    if (!rowKey) {
        alert("select one please!");
        return;
    }

    var selectedIDs = grid.getGridParam("selarrrow");
    if (selectedIDs.length > 1) {
        alert("can only select one!");
        return;
    }

    return selectedIDs[0];
}

//
function getSelectedRows() {
    var grid = $("#gridTable");
    var rowKey = grid.getGridParam("selrow");
    if (!rowKey) {
        alert("please select one");
        return;
    }

    return grid.getGridParam("selarrrow");
}

// NULLチェック
function isBlank(value) {
    return !value || !/\S/.test(value)
}

// 半角数字チェック
function isNumeric(argValue) {
    if (argValue.match(/[^0-9|]/g)) {
        return false;
    } else {
        return true;
    }
}
// 小数判定
function isNumericDecimal(argValue) {
    if (argValue.match(/[^0-9|^.+-]/g)) {
        return false;
    } else {
        return true;
    }
}

// 半角英字
function isAlphabet(argValue) {
    if (argValue.match(/[^A-Z|^a-z]/g)) {
        return false;
    } else {
        return true;
    }
}

// 半角英数字
function isAlphabetNumeric(argValue) {
    if (argValue.match(/[^A-Z|^a-z|^0-9]/g)) {
        return false;
    } else {
        return true;
    }
}

function isEmail(email) {
    // email正式表現
    var reg = new RegExp("^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$");
    if (email === "") { //
        return false;
    } else if (!reg.test(email)) {
        return false;
    } else {
        return true;
    }
}
// TODO cookie脆弱性対応必要 暗号化保存必要
// cookieにデータ設定する
function setCookie(cname, cvalue, exdays) {
    var d = new Date();
    d.setTime(d.getTime() + (exdays * 24 * 60 * 60 * 1000));
    var expires = "expires=" + d.toGMTString();
    document.cookie = cname + "=" + cvalue + "; " + expires;
}

function saveToken(cvalue, exdays) {
    setCookie("access_token", cvalue, exdays);
}

// パラメータよりcookieからデータを取得する
function getToken(token) {
    if (document.cookie.length > 0) {
        var ca = document.cookie.split(';');
        for (var i = 0; i < ca.length; i++) {
            var c = ca[i].trim();
            if (c.indexOf(token) == 0) {
                return c.substring(token.length + 1, c.length);
            }
        }
    }
    return "";
}
// パラメータよりcookieからデータを取得する
function getCookie(name) {
    if (document.cookie.length > 0) {
        var ca = document.cookie.split(';');
        for (var i = 0; i < ca.length; i++) {
            var c = ca[i].trim();
            if (c.indexOf(name) == 0) {
                return c.substring(name.length + 1, c.length);
            }
        }
    }
    return "";
}

$.ajaxSetup({
    dataType: "json",
    cache: false,
    contentType: "application/json; charset=utf-8",
    beforeSend: function (request) {
        request.setRequestHeader("access_token", getToken("access_token"));
    }
});

function getNowFormatDate() {
    var date = new Date();
    var seperator1 = "-";
    var seperator2 = ":";
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate
        + " " + date.getHours() + seperator2 + date.getMinutes()
        + seperator2 + date.getSeconds();
    return currentdate;
}

function daysBetween(dateF, dateS) {
    var OneMonth = dateF.substring(5, dateF.lastIndexOf('-'));
    var OneDay = dateF.substring(dateF.length, dateF.lastIndexOf('-') + 1);
    var OneYear = dateF.substring(0, dateF.indexOf('-'));

    var TwoMonth = dateS.substring(5, dateS.lastIndexOf('-'));
    var TwoDay = dateS.substring(dateS.length, dateS.lastIndexOf('-') + 1);
    var TwoYear = dateS.substring(0, dateS.indexOf('-'));

    var cha = ((Date.parse(OneMonth + '/' + OneDay + '/' + OneYear) - Date.parse(TwoMonth + '/' + TwoDay + '/' + TwoYear)) / 86400000);
    return Math.abs(cha);
}

const weekDays = ['月', '火', '水', '木', '金', '土', '日']
function formatDateYMD(time) {
    var now = new Date();
    now.setTime(time);
    var day = now.getDay();
    var fullYear = now.getFullYear();
    var d = now.getDate();
    var month = now.getMonth() + 1;
    var weekDay = weekDays[day - 1];
    return fullYear + "年" + month + "月" + d + "日 (" + weekDay + ")";
}

function formatDateStrYMDHMS(time) {
    if (time) {
        var arr1 = time.split(" ");
        var sdate = arr1[0].split('-');
        var sdateHMS = arr1[1].split(':');
        return sdate[0] + "年" + sdate[1] + "月" + sdate[2] + "日 " + sdateHMS[0] + ":" + sdateHMS[1] + ":" + sdateHMS[2];
    }
}

function formatDateYMD000 (date) {
    var d = date,
        month = '' + (d.getMonth() + 1),
        day = '' + d.getDate(),
        year = d.getFullYear();

    if (month.length < 2) month = '0' + month;
    if (day.length < 2) day = '0' + day;
    return [year, month, day].join('-') + " 00:00:00";
}

function formatDateStrYMD(time) {
    if (time) {
        var arr1 = time.split(" ");
        var sdate = arr1[0].split('-');
        return sdate[0] + "年" + sdate[1] + "月" + sdate[2] + "日";
    }
}

function getApiUrl() {
    return getCookie("ipPort") == "" ? "http://localhost:8082/" : "http://" + getCookie("ipPort") + "/";
}
function isEmptyString(str){
    return (str == null ||typeof str == undefined || str =="" )
}