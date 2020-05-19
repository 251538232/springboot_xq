package jp.co.xq.service.sys.service;

import jp.co.xq.service.base.common.BaseService;
import jp.co.xq.service.sys.model.SysAccessToken;
import jp.co.xq.service.sys.model.SysUser;
import jp.co.xq.service.sys.model.SysUserExample;
import jp.co.xq.service.sys.model.extend.SysUserExtend;

import java.util.Set;

/**
 * SysUserService interface
 *
 * @author tian w 2018/6/28.
 */
public interface SysUserService extends BaseService<SysUser, SysUserExample> {

    /**
     * ユーザーＩＤより権限を取得する
     *
     * @param sysUserId 　システムユーザーＩＤ
     * @return 権限SET
     */
    Set<String> getPermsSet(Long sysUserId);

    /**
     * アックセスTokenよりToken情報を取得する
     *
     * @param accessToken 認証トークン
     * @return アクセスToken情報
     */
    SysAccessToken getSysAccessToken(String accessToken);

    /**
     * アックセスTokenより システムユーザー情報を取得する
     *
     * @param accessToken 認証トークン
     * @return システムユーザー情報
     */
    SysUser getSysUserByAccessToken(String accessToken);

    /**
     * システムユーザーデータ作成或いは更新
     *
     * @param sysUserExtend
     * @return
     */
    int createOrUpdate(SysUserExtend sysUserExtend);
}