package jp.co.xq.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jp.co.xq.base.common.BaseResult;
import jp.co.xq.base.utils.ConditionUtils;
import jp.co.xq.base.utils.PageUtils;
import jp.co.xq.controller.base.BaseUserController;
import jp.co.xq.service.sys.model.SysUser;
import jp.co.xq.service.sys.model.SysUserExample;
import jp.co.xq.service.sys.model.extend.SysUserExtend;
import jp.co.xq.service.sys.service.SysUserService;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Sha1Hash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * sysUserインターフェース
 *
 * @author tian w 2018/6/28.
 */
@RestController
@RequestMapping("/sysUser")
@Api(value = "システムユーザーコントローラー", description = "システムユーザー管理")
public class SysUserController extends BaseUserController {

  private static final Logger LOGGER = LoggerFactory.getLogger(SysUserController.class);

  @Autowired
  SysUserService sysUserService;

  @ApiOperation(value = "SysUser情報一覧", httpMethod = "GET")
  @RequestMapping(value = "/list", method = {RequestMethod.GET})
  @RequiresPermissions("sysuser:list")
  public Object list(@RequestParam Map conditionMap) throws Exception {

    ConditionUtils condition = new ConditionUtils<>(new SysUserExample());
    // 条件からデータを取得する
    SysUserExample sysUserExample = (SysUserExample) condition.createExampleByMap(conditionMap);
    List<SysUser> dataList = sysUserService.selectByExample(sysUserExample);
    for (int i = 0; i < dataList.size(); i++) {
      dataList.get(i).setPassword(null);
      dataList.get(i).setSalt(null);
    }
    // 外ページ処理しない
    sysUserExample.setLimit(null);
    PageUtils pageData = new PageUtils(dataList, sysUserService.countByExample(sysUserExample), conditionMap);
    return BaseResult.ok(pageData);
  }

  @ApiOperation(value = "詳細データ取得", httpMethod = "GET")
  @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
  @RequiresPermissions("sysuser:detail")
  public Object detail(@PathVariable("id") Long id) {
    SysUser sysUser = sysUserService.selectByPrimaryKey(id);
    return BaseResult.ok(sysUser);
  }

  @ApiOperation(value = "新規", httpMethod = "POST")
  @RequestMapping(value = "/create", method = RequestMethod.POST)
  @RequiresPermissions("sysuser:create")
  public Object create(@RequestBody SysUserExtend sysUserExtend) {
    // 新規ユーザーの場合　パスワードを暗号化して保存する
    String salt = RandomStringUtils.randomAlphanumeric(20);
    // salt設定
    sysUserExtend.setSalt(salt);
    // 明文パスワードを暗号化して保存
    sysUserExtend.setPassword(new Sha1Hash(sysUserExtend.getPassword(), sysUserExtend.getSalt()).toHex());
    sysUserExtend.setCreateUserId(getSysUser().getId());
    int count = sysUserService.createOrUpdate(sysUserExtend);
    return BaseResult.ok(count);
  }

  /**
   * システムユーザー情報更新
   *
   * @param sysUserExtend システムユーザー拡張
   * @return 更新件数
   */
  @ApiOperation(value = "更新", httpMethod = "POST")
  @RequestMapping(value = "/update", method = RequestMethod.POST)
  @RequiresPermissions("sysuser:update")
  public Object update(@RequestBody SysUserExtend sysUserExtend) {
    // パスワード更新しない
    sysUserExtend.setPassword(null);
    sysUserExtend.setSalt(null);
    sysUserExtend.setUpdateTime(new Date());
    int count = sysUserService.createOrUpdate(sysUserExtend);
    return BaseResult.ok(count);
  }

  /**
   * ユーザーパスワード変更
   *
   * @param sysUser システムユーザー
   * @return 更新件数
   */
  @ApiOperation(value = "パスワード変更", httpMethod = "POST")
  @RequestMapping(value = "/changePassword", method = RequestMethod.POST)
  @RequiresPermissions("sysuser:changepassword")
  public Object changePassword(@RequestBody SysUser sysUser) {
    String salt = RandomStringUtils.randomAlphanumeric(20);
    // salt設定
    sysUser.setSalt(salt);
    // 明文パスワードを暗号化して保存
    sysUser.setPassword(new Sha1Hash(sysUser.getPassword(), sysUser.getSalt()).toHex());
    sysUser.setUpdateTime(new Date());
    if (StringUtils.isEmpty(sysUser.getId())) {
      sysUser.setId(getSysUser().getId());
    }
    int count = sysUserService.updateByPrimaryKeySelective(sysUser);
    return BaseResult.ok(count);
  }

  @ApiOperation(value = "削除（複数IDが存在する場合、カンマ区切り）", httpMethod = "GET")
  @RequestMapping(value = "/delete/{ids}", method = RequestMethod.GET)
  @RequiresPermissions("sysuser:delete")
  public Object delete(@PathVariable("ids") String ids) {
    int count = sysUserService.deleteByPrimaryKeys(ids);
    return BaseResult.ok(count);
  }
}