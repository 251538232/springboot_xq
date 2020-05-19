package jp.co.xq.controller.base;

import com.alibaba.fastjson.JSONObject;
import jp.co.xq.service.sys.model.SysUser;
import jp.co.xq.service.sys.model.SysUserRole;
import jp.co.xq.service.sys.model.SysUserRoleExample;
import jp.co.xq.service.sys.model.extend.SysUserExtend;
import jp.co.xq.service.sys.service.SysUserRoleService;
import jp.co.xq.service.sys.service.SysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

/**
 * ユーザー基本情報取得するControllerベース処理
 *
 * @author t
 */
public abstract class BaseUserController extends BaseController {

    private final static Logger LOGGER = LoggerFactory.getLogger(BaseUserController.class);

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysUserRoleService sysUserRoleService;

    /**
     * システムユーザー情報を取得
     *
     * @return システムユーザー情報
     */
    protected SysUser getSysUser() {
        return sysUserService.getSysUserByAccessToken(getAccessToken());
    }

    /**
     * システムユーザー情報を取得
     *
     * @return システムユーザー情報
     */
    protected SysUserExtend getSysUserExtend() {
        SysUser sysUser = getSysUser();
        SysUserExtend sysUserExtend = JSONObject.parseObject(JSONObject.toJSONString(sysUser), SysUserExtend.class);
        SysUserRoleExample sysUserRoleExample = new SysUserRoleExample();
        sysUserRoleExample.createCriteria().andUserIdEqualTo(sysUser.getId());

        List<SysUserRole> sysUserRoleList = sysUserRoleService.selectByExample(sysUserRoleExample);
        sysUserExtend.setRoleIdList(sysUserRoleList.stream().map(SysUserRole::getRoleId).collect(Collectors.toList()));
        return sysUserExtend;
    }
}
