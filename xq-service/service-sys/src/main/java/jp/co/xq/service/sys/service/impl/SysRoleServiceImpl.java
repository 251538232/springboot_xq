package jp.co.xq.service.sys.service.impl;

import jp.co.xq.service.base.common.BaseServiceImpl;
import jp.co.xq.service.sys.mapper.SysRoleMapper;
import jp.co.xq.service.sys.mapper.SysRoleMenuMapper;
import jp.co.xq.service.sys.model.SysRole;
import jp.co.xq.service.sys.model.SysRoleExample;
import jp.co.xq.service.sys.model.SysRoleMenu;
import jp.co.xq.service.sys.model.SysRoleMenuExample;
import jp.co.xq.service.sys.model.extend.SysRoleExtend;
import jp.co.xq.service.sys.service.SysRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * SysRoleService処理
 *
 * @author tian w 2018/6/28.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SysRoleServiceImpl extends BaseServiceImpl<SysRoleMapper, SysRole, SysRoleExample> implements SysRoleService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SysRoleServiceImpl.class);

    @Autowired
    SysRoleMapper sysRoleMapper;

    @Autowired
    SysRoleMenuMapper sysRoleMenuMapper;

    @Override
    public void createOrUpdate(SysRoleExtend sysRoleExtend) {
        Date now = new Date();
        SysRole role = sysRoleExtend;
        role.setUpdateTime(now);
        // ＩＤがＮＵＬＬの場合新規追加
        if (role.getId() != null && sysRoleExtend.getId() != 0) {
            // 更新する
            sysRoleMapper.updateByPrimaryKeySelective(role);
        } else {
            role.setCreateTime(now);
            sysRoleMapper.insertSelective(role);
        }
        Long roleId = role.getId();
        /********************************************************/
        // メニューデータを保存する
        /********************************************************/
        SysRoleMenuExample sysRoleMenuExample = new SysRoleMenuExample();
        sysRoleMenuExample.createCriteria().andRoleIdEqualTo(role.getId());
        sysRoleMenuMapper.deleteByExample(sysRoleMenuExample);
        List<Long> menuIdList = sysRoleExtend.getMenuIdList();
        List<SysRoleMenu> sysRoleMenuList = new ArrayList<>();
        for (int i = 0; i < menuIdList.size(); i++) {
            SysRoleMenu sysRoleMenu = new SysRoleMenu();
            sysRoleMenu.setMenuId(menuIdList.get(i));
            sysRoleMenu.setRoleId(roleId);
            sysRoleMenuList.add(sysRoleMenu);
        }
        if (sysRoleMenuList.size() > 0) {
            sysRoleMenuMapper.batchInsert(sysRoleMenuList);
        }
    }
}