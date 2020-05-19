package jp.co.xq.controller;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jp.co.xq.base.common.BaseResult;
import jp.co.xq.base.utils.ConditionUtils;
import jp.co.xq.base.utils.PageUtils;
import jp.co.xq.controller.base.BaseUserController;
import jp.co.xq.service.sys.model.*;
import jp.co.xq.service.sys.model.extend.SysRoleExtend;
import jp.co.xq.service.sys.service.SysRoleMenuService;
import jp.co.xq.service.sys.service.SysRoleService;
import jp.co.xq.service.sys.service.SysUserRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * sysRoleインターフェース
 *
 * @author tian w 2018/8/3.
 */
@RestController
@RequestMapping("/sysRole")
@Api(value = "sysRoleコントローラー", description = "sysRole管理")
public class SysRoleController extends BaseUserController {

    private static final Logger logger = LoggerFactory.getLogger(SysRoleController.class);

    @Autowired
    SysRoleService sysRoleService;

    @Autowired
    SysUserRoleService sysUserRoleService;

    @Autowired
    SysRoleMenuService sysRoleMenuService;


    @ApiOperation(value = "ユーザーIDよりSysRole情報一覧", httpMethod = "GET")
    @RequestMapping(value = "/listByUserId", method = {RequestMethod.GET})
    public Object list(@RequestParam Long userId) throws Exception {
        SysUserRoleExample sysUserRoleExample = new SysUserRoleExample();
        sysUserRoleExample.createCriteria().andUserIdEqualTo(userId);
        List<SysUserRole> sysUserRoles = sysUserRoleService.selectByExample(sysUserRoleExample);
        List<Long> roleIds = new ArrayList<>();
        if (!sysUserRoles.isEmpty()) {
            roleIds = sysUserRoles.stream().map(SysUserRole::getRoleId).collect(Collectors.toList());
        }

        SysRoleExample sysRoleExample = new SysRoleExample();
        sysRoleExample.createCriteria().andIdIn(roleIds);
        List<SysRole> sysRoles = sysRoleService.selectByExample(sysRoleExample);
        return BaseResult.ok(sysRoles.stream().map(SysRole::getId).collect(Collectors.toList()));
    }

    @ApiOperation(value = "SysRole情報一覧", httpMethod = "GET")
    @RequestMapping(value = "/list", method = {RequestMethod.GET})
    public Object list(@RequestParam Map conditionMap) throws Exception {

        ConditionUtils condition = new ConditionUtils<>(new SysRoleExample());
        // 条件からデータを取得する
        SysRoleExample sysRoleExample = (SysRoleExample) condition.createExampleByMap(conditionMap);
        List dataList = sysRoleService.selectByExample(sysRoleExample);
        // 改ページ処理しない
        sysRoleExample.setLimit(null);
        PageUtils pageData = new PageUtils(dataList, sysRoleService.countByExample(sysRoleExample), conditionMap);
        return BaseResult.ok(pageData);
    }

    @ApiOperation(value = "詳細データ(対応メニューＩＤリストも含む)取得", httpMethod = "GET")
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public Object detail(@PathVariable("id") Long id) {
        SysRole sysRole = sysRoleService.selectByPrimaryKey(id);
        // クラスオブジェクト転換
        SysRoleExtend sysRoleExtend = JSONObject.parseObject(JSONObject.toJSONString(sysRole), SysRoleExtend.class);

        // ユーザーRoleのメニューＩＤ
        SysRoleMenuExample sysRoleMenuExample = new SysRoleMenuExample();
        sysRoleMenuExample.createCriteria().andRoleIdEqualTo(id);
        List<SysRoleMenu> sysRoleMenuList = sysRoleMenuService.selectByExample(sysRoleMenuExample);
        sysRoleExtend.setMenuIdList(sysRoleMenuList.stream().map(SysRoleMenu::getMenuId).collect(Collectors.toList()));
        return BaseResult.ok(sysRoleExtend);
    }

    @ApiOperation(value = "新規", httpMethod = "POST")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Object create(@RequestBody SysRoleExtend sysRoleExtend) {
        sysRoleExtend.setCreateUserId(getSysUser().getId());
        sysRoleService.createOrUpdate(sysRoleExtend);
        return BaseResult.ok();
    }

    @ApiOperation(value = "更新", httpMethod = "POST")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Object update(@RequestBody SysRoleExtend sysRoleExtend) {
        sysRoleExtend.setCreateUserId(getSysUser().getId());
        sysRoleService.createOrUpdate(sysRoleExtend);
        return BaseResult.ok();
    }

    @ApiOperation(value = "削除（複数IDが存在する場合、カンマ区切り）", httpMethod = "GET")
//  @RequiresPermissions("sysrole:delete")
    @RequestMapping(value = "/delete/{ids}", method = RequestMethod.GET)
    public Object delete(@PathVariable("ids") String ids) {
        int count = sysRoleService.deleteByPrimaryKeys(ids);
        return BaseResult.ok(count);
    }
}