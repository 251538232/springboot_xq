layui.define(['element'], function(exports){
    var  element = layui.element(),
        $ = layui.jquery,
        layer = parent.layer === undefined ? layui.layer : parent.layer,
        module_name = 'navtab',
        globalTabIdIndex = 0,
        ScTab = function(){
            this.config ={
                elem: undefined,
                closed: true
            };
        };
    var ELEM = {};

    ScTab.prototype.set = function(options){
        var _this = this;
        $.extend(true, _this.config, options);
        return _this;
    };

    ScTab.prototype.init  = function(){
        var _this = this;
        var _config = _this.config;
        if(typeof(_config.elem) !== 'string' && typeof(_config.elem) !== 'object') {
            layer.alert('Tab Error.');
        }
        var $container;
        if(typeof(_config.elem) === 'string') {
            $container = $('' + _config.elem + '');
            //console.log($container);
        }
        if(typeof(_config.elem) === 'object') {
            $container = _config.elem;
        }
        if($container.length === 0) {
            layer.alert('Tab Error');
        }
        var filter = $container.attr('lay-filter');
        if(filter === undefined || filter === '') {
            layer.alert('Tab Error');
        }
        _config.elem = $container;
        ELEM.titleBox = $container.children('ul.layui-tab-title');
        ELEM.contentBox = $container.children('div.layui-tab-content');
        ELEM.tabFilter = filter;
        return _this;
    };

    ScTab.prototype.exists = function(title){
        var _this = ELEM.titleBox === undefined ? this.init() : this,
            tabIndex = -1;
        ELEM.titleBox.find('li').each(function(i, e) {
            var $em = $(this).children('em');
            if($em.text() === title) {
                tabIndex = i;
            };
        });
        return tabIndex;
    };

    ScTab.prototype.tabAdd = function(data){
        var _this = this;
        var tabIndex = _this.exists(data.title);
        // 若不存在
        if(tabIndex === -1){
            globalTabIdIndex++;
            var content = '<iframe src="' + data.href + '" data-id="' + globalTabIdIndex + '" class="sc-iframe"></iframe>';
            var title = '';
            // 若icon有定义
            if(data.icon !== undefined){
                if(data.icon.indexOf('fa-') !== -1) {
                    title += '<i class="' + data.icon + '"></i>';
                } else {
                    title += '<i class="layui-icon ">' + data.icon + '</i>';
                }
            }
            title += '<em>' + data.title + '</em>';
            if(_this.config.closed) {
                title += '<i class="layui-icon layui-unselect layui-tab-close" data-id="' + globalTabIdIndex + '">&#x1006;</i>';
            }
            element.tabAdd(ELEM.tabFilter, {
                title: title,
                content: content
            });
            ELEM.contentBox.find('iframe[data-id=' + globalTabIdIndex + ']').each(function() {
                $(this).height(ELEM.contentBox.height());
            });
            if(_this.config.closed) {
                ELEM.titleBox.find('li').children('i.layui-tab-close[data-id=' + globalTabIdIndex + ']').on('click', function() {
                    element.tabDelete(ELEM.tabFilter, $(this).parent('li').index()).init();
                });
            };
            element.tabChange(ELEM.tabFilter, ELEM.titleBox.find('li').length - 1);
        }else {
            element.tabChange(ELEM.tabFilter, tabIndex);
        }
    };
    var navtab = new ScTab();
    exports(module_name, function(options) {
        return navtab.set(options);
    });
});