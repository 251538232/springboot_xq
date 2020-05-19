package jp.co.xq.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jp.co.xq.base.common.BaseConstants;
import jp.co.xq.base.common.BaseResult;
import jp.co.xq.base.exception.AppException;
import jp.co.xq.base.utils.ConditionUtils;
import jp.co.xq.base.utils.StringListUtil;
import jp.co.xq.controller.base.BaseUserController;
import jp.co.xq.service.sys.model.SysMenu;
import jp.co.xq.service.sys.model.SysMenuExample;
import jp.co.xq.service.sys.model.extend.SysMenuExtend;
import jp.co.xq.service.sys.service.SysMenuService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * sysMenuインターフェース
 *
 * @author tian w 2018/6/28.
 */
@RestController
@RequestMapping("/sysMenu")
@Api(value = "sysMenuコントローラー", description = "sysMenu管理")
public class SysMenuController extends BaseUserController {

    private static final Logger logger = LoggerFactory.getLogger(SysMenuController.class);

    @Autowired
    SysMenuService sysMenuService;

    @ApiOperation(value = "ユーザーメニュー取得", httpMethod = "GET")
    @RequestMapping(value = "/getMenuTree", method = RequestMethod.GET)
    public Object getMenuTree() {
        // メニューリストを取得する
        List<SysMenuExtend> treeList = sysMenuService.treeMenuListByUserId(getSysUser().getId());
        return BaseResult.ok(treeList);
    }

    /**
     * メニュー選択
     * （追加、削除用）
     *
     * @return
     */
    @ApiOperation(value = "システムメニュー選択", httpMethod = "GET")
    @ResponseBody
    @RequestMapping(value = "/select", method = {RequestMethod.GET})
    public BaseResult select() {
        SysMenuExample sysMenuExample = new SysMenuExample();
        // ボタンを除く
        sysMenuExample.createCriteria().andTypeNotEqualTo(2);
        List<SysMenu> menuList = sysMenuService.selectByExample(sysMenuExample);
        // トップメニュー
        SysMenu menuRoot = new SysMenu();
        menuRoot.setId(0L);
        menuRoot.setParentId(-1L);
        menuRoot.setName("メニュー一覧");
        menuList.add(menuRoot);

        return BaseResult.ok(menuList);
    }


    /**
     * すべてメニューを取得する
     *
     * @param conditionMap
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "SysMenu情報一覧", httpMethod = "GET")
    @RequestMapping(value = "/list", method = {RequestMethod.GET})
    public Object list(@RequestParam Map conditionMap) throws Exception {

        ConditionUtils condition = new ConditionUtils<>(new SysMenuExample());
        // 条件からデータを取得する
        SysMenuExample sysMenuExample = (SysMenuExample) condition.createExampleByMap(conditionMap);
        List dataList = sysMenuService.selectByExample(sysMenuExample);

        return BaseResult.ok(dataList);
    }

    @ApiOperation(value = "詳細データ取得", httpMethod = "GET")
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public Object detail(@PathVariable("id") Long id) {
        SysMenu sysMenu = sysMenuService.selectByPrimaryKey(id);
        return BaseResult.ok(sysMenu);
    }

    @ApiOperation(value = "新規", httpMethod = "POST")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Object create(@RequestBody SysMenu sysMenu) {
        checkMenu(sysMenu);
        int count = sysMenuService.insertSelective(sysMenu);
        return BaseResult.ok(count);
    }

    @ApiOperation(value = "更新", httpMethod = "POST")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Object update(@RequestBody SysMenu sysMenu) {
        checkMenu(sysMenu);
        int count = sysMenuService.updateByPrimaryKey(sysMenu);
        return BaseResult.ok(count);
    }

    @ApiOperation(value = "削除（複数IDが存在する場合、カンマ区切り）", httpMethod = "GET")
    @RequestMapping(value = "/delete/{ids}", method = RequestMethod.GET)
    public Object delete(@PathVariable("ids") String ids) {
        SysMenuExample sysMenuExample = new SysMenuExample();
        sysMenuExample.createCriteria().andParentIdIn(StringListUtil.toList(ids));
        List<SysMenu> menuList = sysMenuService.selectByExample(sysMenuExample);
        if (menuList.size() > 0) {
            return BaseResult.error("子メニュー、ボタンを事前に削除してください。");
        }

        int count = sysMenuService.deleteByPrimaryKeys(ids);
        return BaseResult.ok(count);
    }

    /**
     * メニュー情報チェック処理
     *
     * @param menu
     */
    private void checkMenu(SysMenu menu) {
        if (StringUtils.isBlank(menu.getName())) {
            throw new AppException("メニュー名称を入力してください。");
        }

        if (menu.getParentId() == null) {
            throw new AppException("親メニューを選択してください。");
        }

        if (menu.getType() == BaseConstants.MenuType.MENU.getValue()) {
            if (StringUtils.isBlank(menu.getUrl())) {
                throw new AppException("メニューURLを入力してください。");
            }
        }

        int parentType = BaseConstants.MenuType.CATALOG.getValue();
        if (menu.getParentId() != 0) {
            SysMenuExample sysMenuExample = new SysMenuExample();
            sysMenuExample.createCriteria().andIdEqualTo(menu.getParentId());
            SysMenu parentMenu = sysMenuService.selectOneByExample(sysMenuExample);
            parentType = parentMenu.getType();
        }

        if (menu.getType() == BaseConstants.MenuType.CATALOG.getValue() ||
                menu.getType() == BaseConstants.MenuType.MENU.getValue()) {
            if (parentType != BaseConstants.MenuType.CATALOG.getValue()) {
                throw new AppException("所属はカテゴリーではありません。");
            }
            return;
        }

        if (menu.getType() == BaseConstants.MenuType.BUTTON.getValue()) {
            if (parentType != BaseConstants.MenuType.MENU.getValue()) {
                throw new AppException("所属はメニューではありません");
            }
            return;
        }
        if (StringUtils.isEmpty(menu.getIcon())) {
            menu.setIcon("fa fa-file");
        }
    }
}