package jp.co.xq.service.sys.service.impl;

import jp.co.xq.service.base.common.BaseServiceImpl;
import jp.co.xq.service.sys.mapper.SysUserRoleMapper;
import jp.co.xq.service.sys.model.SysUserRole;
import jp.co.xq.service.sys.model.SysUserRoleExample;
import jp.co.xq.service.sys.service.SysUserRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * SysUserRoleService処理
 * @author tian w 2018/6/28.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SysUserRoleServiceImpl extends BaseServiceImpl<SysUserRoleMapper, SysUserRole, SysUserRoleExample> implements SysUserRoleService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SysUserRoleServiceImpl.class);

    @Autowired
    SysUserRoleMapper sysUserRoleMapper;

}