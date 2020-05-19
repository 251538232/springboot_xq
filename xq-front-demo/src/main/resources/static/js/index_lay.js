$(window).on('resize', function () {
    var $content = $('#sc-tab .layui-tab-content');
    $content.height($(this).height() - 140);
    $content.find('iframe').each(function () {
        $(this).height($content.height());
    });
}).resize();

// メニュー生成
var menuItem = Vue.extend({
    name: 'menu-item',
    props: {item: {}},
    template: [
        //  メニューディフォルト【展開状態】layui-nav-itemed
        '<li class="layui-nav-item layui-nav-itemed">',
        '<a v-if="item.type === 0" href="javascript:;">',
        '<i v-if="item.icon != null" v-bind:class="item.icon"></i>',
        '<span> {{item.name}}</span>',
        '<em class="layui-nav-more"></em>',
        '</a>',
        '<dl v-if="item.type === 0" class="layui-nav-child">',
        '<dd v-for="item in item.list">',
        '<a v-if="item.type === 1" href="javascript:;" :data-url="item.url"><i v-if="item.icon != null" :class="item.icon" :data-icon="item.icon"></i> <span>{{item.name}}</span></a>',
        '</dd>',
        '</dl>',
        '<a v-if="item.type === 1" href="javascript:;" :data-url="item.url"><i v-if="item.icon != null" :class="item.icon" :data-icon="item.icon"></i> <span>{{item.name}}</span></a>',
        '</li>'
    ].join('')
});
// メニューアイテム
Vue.component('menuItem', menuItem);
menuLoading = true;

var vm = new Vue({
    el: '#layui_layout',
    data: {
        menuList: {},
        password: '',
        newPassword: '',
        navTitle: "top page"
    },
    methods: {
        getMenuList: function () {
            $.getJSON(apiUrl + "sysMenu/getMenuTree", function (r) {
                vm.menuList = r.data;
            });
        },
    },
    created: function () {
        this.getMenuList();
        // this.getUser();
    },
    updated: function () {
        if ($("#sc-side .layui-nav-item>a").length == 0 || !menuLoading) {
            return;
        }
        isquery = false;
        layui.config({
            base: 'js/',
        }).use(['navtab', 'layer'], function () {
            window.jQuery = window.$ = layui.jquery;
            window.layer = layui.layer;
            var element = layui.element();
            var navtab = layui.navtab({
                elem: '.sc-tab-box',
                closed: false
            });
            $('#sc-nav-side').children('ul').find('li').each(function () {
                var $this = $(this);
                if ($this.find('dl').length > 0) {
                    var $dd = $this.find('dd').each(function () {
                        $(this).on('click', function () {
                            var $a = $(this).children('a');
                            var href = $a.data('url');
                            var icon = $a.children('i:first').data('icon');
                            var title = $a.children('span').text();
                            var data = {
                                href: href,
                                icon: icon,
                                title: title
                            }
                            navtab.tabAdd(data);
                        });
                    });
                } else {
                    $this.on('click', function () {
                        var $a = $(this).children('a');
                        var href = $a.data('url');
                        var icon = $a.children('i:first').data('icon');
                        var title = $a.children('span').text();
                        var data = {
                            href: href,
                            icon: icon,
                            title: title
                        }
                        navtab.tabAdd(data);
                    });
                }
            });
            $('.sc-side-menu').click(function () {
                var sideWidth = $('#sc-side').width();
                if (sideWidth === 200) {
                    $('#sc-body').animate({
                        left: '0'
                    });
                    $('#sc-footer').animate({
                        left: '0'
                    });
                    $('#sc-side').animate({
                        width: '0'
                    });
                } else {
                    $('#sc-body').animate({
                        left: '200px'
                    });
                    $('#sc-footer').animate({
                        left: '200px'
                    });
                    $('#sc-side').animate({
                        width: '200px'
                    });
                }
            });
        });
    }
});