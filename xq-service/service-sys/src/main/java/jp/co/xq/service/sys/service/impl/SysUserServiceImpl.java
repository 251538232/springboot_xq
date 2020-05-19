package jp.co.xq.service.sys.service.impl;

import jp.co.xq.service.base.common.BaseServiceImpl;
import jp.co.xq.service.sys.mapper.SysAccessTokenMapper;
import jp.co.xq.service.sys.mapper.SysUserMapper;
import jp.co.xq.service.sys.mapper.SysUserRoleMapper;
import jp.co.xq.service.sys.mapper.extend.SysUserExtendMapper;
import jp.co.xq.service.sys.model.*;
import jp.co.xq.service.sys.model.extend.SysUserExtend;
import jp.co.xq.service.sys.service.SysUserService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * SysUserService処理
 *
 * @author tian w 2018/6/28.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SysUserServiceImpl extends BaseServiceImpl<SysUserMapper, SysUser, SysUserExample> implements SysUserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SysUserServiceImpl.class);

    @Autowired
    private SysUserExtendMapper sysUserExtendMapper;

    @Autowired
    private SysAccessTokenMapper sysAccessTokenMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    /**
     * ユーザーＩＤから権限を取得する
     *
     * @param sysUserId ユーザーＩＤ
     * @return 権限SET
     */
    @Override
    public Set<String> getPermsSet(Long sysUserId) {
        // ユーザー権限
        List<String> permsList = sysUserExtendMapper.getPermissionsByUserId(sysUserId);
        Set<String> permsSet = new HashSet<>();
        for (String perms : permsList) {
            if (!StringUtils.isBlank(perms)) {
                permsSet.addAll(Arrays.asList(perms.trim().split(",")));
            }
        }
        return permsSet;
    }

    /**
     * 認証Token情報を取得する
     *
     * @param accessToken
     * @return アクセスToken情報
     */
    @Override
    public SysAccessToken getSysAccessToken(String accessToken) {
        SysAccessTokenExample condition = new SysAccessTokenExample();
        condition.createCriteria().andAccessTokenEqualTo(accessToken);
        List<SysAccessToken> accessTokenList = sysAccessTokenMapper.selectByExample(condition);

        if (accessTokenList != null && accessTokenList.size() != 0) {
            return accessTokenList.get(0);
        }
        return null;
    }

    /**
     * 認証Tokenより、ユーザー情報を取得する
     *
     * @param accessToken
     * @return ユーザー情報
     */
    @Override
    public SysUser getSysUserByAccessToken(String accessToken) {
        SysAccessToken sysAccessToken = getSysAccessToken(accessToken);
        if (sysAccessToken == null) {
            return null;
        }
        return sysUserMapper.selectByPrimaryKey(sysAccessToken.getUserId());
    }

    /**
     * システムユーザーデータ作成或いは更新
     *
     * @param sysUserExtend
     * @return
     */
    @Override
    public int createOrUpdate(SysUserExtend sysUserExtend) {
        // ユーザー情報をDBにデータ新規登録or 更新を行う
        SysUser sysUser = sysUserExtend;
        if (sysUserExtend.getId() != null) {
            sysUserMapper.updateByPrimaryKeySelective(sysUser);
            SysUserRoleExample sysUserRoleExample = new SysUserRoleExample();
            sysUserRoleExample.createCriteria().andUserIdEqualTo(sysUser.getId());
            // 既に存在するデータを削除
            sysUserRoleMapper.deleteByExample(sysUserRoleExample);
        } else {
            sysUserMapper.insertSelective(sysUser);
        }

        List<SysUserRole> sysUserRoleList = new ArrayList<>();
        List<Long> roleIdList = sysUserExtend.getRoleIdList();
        for (int i = 0; i < roleIdList.size(); i++) {
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setRoleId(roleIdList.get(i));
            sysUserRole.setUserId(sysUserExtend.getId());
            sysUserRoleList.add(sysUserRole);
        }
        sysUserRoleMapper.batchInsertSelective(sysUserRoleList);
        return 1;
    }


}