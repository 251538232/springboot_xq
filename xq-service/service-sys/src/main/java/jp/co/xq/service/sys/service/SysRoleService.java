package jp.co.xq.service.sys.service;

import jp.co.xq.service.base.common.BaseService;
import jp.co.xq.service.sys.model.SysRole;
import jp.co.xq.service.sys.model.SysRoleExample;
import jp.co.xq.service.sys.model.extend.SysRoleExtend;

/**
 * SysRoleService interface
 *
 * @author tian w 2018/6/28.
 */
public interface SysRoleService extends BaseService<SysRole, SysRoleExample> {

    /**
     * Role作成 or 更新処理
     *
     * @param sysRoleExtend
     */
    void createOrUpdate(SysRoleExtend sysRoleExtend);
}