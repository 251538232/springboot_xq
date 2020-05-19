/* <![CDATA[ */
var vm = new Vue({
    el: '#webapp',
    data: {
        q: {
            username: null
        },
        showList: true,
        roleList: {},
        user: {
            status: 1,
            deptId: null,
            deptName: null,
            roleIdList: []
        },
        username: null,
        password: null,
        interval: null,
        dayOfMonth: "", // 直近出勤日
        syukin: "", // 出勤時刻
        taikin: "",
        startTime: "",  // 開始時間
        endTime: "",    // 終了時間
        message: "",// 打刻メッセージ
        date: "",
        realTime: "",
        offset: 0

    },
    mounted: function () {
        $.ajax({
            type: "GET",
            url: encodeURI(apiUrl + "/sysTime"),
            contentType: "application/json",
            success: function (r) {
                if (r.code == 0) {
                    var now = new Date();
                    vm.offset = r.data - now.getTime();
                    vm.date = formatDateYMD(r.data);
                    vm.updateSeconds();
                    // vm.updateOffset();
                } else {
                    console.log("failed to synchronize with server time.")
                }
            }
        });
    },
    methods: {
        reload: function () {
            $.ajax({
                type: "GET",
                url: encodeURI(apiUrl + "/daily/getCurrentDaily"),
                contentType: "application/json",
                success: function (r) {
                    if (r.code == 0) {
                        vm.dayOfMonth = formatDateStrYMD(r.data.dayOfMonth);
                        vm.startTime = formatDateStrYMDHMS(r.data.startTime);
                        vm.endTime = formatDateStrYMDHMS(r.data.endTime);
                    } else {
                    }
                }
            });
        },
        startWork: function () {
            $.ajax({
                type: "GET",
                url: encodeURI(apiUrl + "/daily/punchByToken?punchType=start&time=" + new Date()),
                contentType: "application/json",
                beforeSend: function (request) {
                    request.setRequestHeader("access_token", getToken("access_token"));
                },
                success: function (r) {
                    vm.message = r.message;
                    vm.reload();
                }
            });
        },
        endWork: function () {
            $.ajax({
                type: "GET",
                url: encodeURI(apiUrl + "/daily/punchByToken?punchType=end&time=" + new Date()),
                contentType: "application/json",
                beforeSend: function (request) {
                    request.setRequestHeader("access_token", getToken("access_token"));
                },
                success: function (r) {
                    vm.message = r.message;
                    vm.reload();
                }
            });
        },
        updateSeconds: function () {
            setInterval(function clock() {
                var now = new Date();
                now.setTime(now.getTime() + vm.offset);
                var timeHH = now.getHours();
                var timeMM = now.getMinutes();
                var timeSS = now.getSeconds();
                var timeString = ((timeHH < 10) ? '0' : '') + timeHH
                    + ':' + ((timeMM < 10) ? '0' : '') + timeMM
                    + ':' + ((timeSS < 10) ? '0' : '') + timeSS;
                timeString = (timeString == null ? "test" : timeString)
                vm.realTime = timeString;
            }, 50);
        }
    }
});
vm.reload();

/* ]]> */