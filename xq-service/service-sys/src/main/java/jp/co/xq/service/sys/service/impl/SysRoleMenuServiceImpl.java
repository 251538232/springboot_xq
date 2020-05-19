package jp.co.xq.service.sys.service.impl;

import jp.co.xq.service.base.common.BaseServiceImpl;
import jp.co.xq.service.sys.mapper.SysRoleMenuMapper;
import jp.co.xq.service.sys.model.SysRoleMenu;
import jp.co.xq.service.sys.model.SysRoleMenuExample;
import jp.co.xq.service.sys.service.SysRoleMenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * SysRoleMenuService処理
 * @author tian w 2018/6/28.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SysRoleMenuServiceImpl extends BaseServiceImpl<SysRoleMenuMapper, SysRoleMenu, SysRoleMenuExample> implements SysRoleMenuService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SysRoleMenuServiceImpl.class);

    @Autowired
    SysRoleMenuMapper sysRoleMenuMapper;

}