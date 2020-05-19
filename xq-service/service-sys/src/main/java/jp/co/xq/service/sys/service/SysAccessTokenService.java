package jp.co.xq.service.sys.service;

import jp.co.xq.service.base.common.BaseService;
import jp.co.xq.service.sys.model.SysAccessToken;
import jp.co.xq.service.sys.model.SysAccessTokenExample;

import java.util.Map;

/**
 * SysAccessTokenService interface
 *
 * @author tian w 2018/6/20.
 */
public interface SysAccessTokenService extends BaseService<SysAccessToken, SysAccessTokenExample> {

    /**
     * アクセスTokenを取得する
     *
     * @param sysUserId システムユーザーＩＤ
     * @return Token作成結果
     */
    Map createAccessToken(Long sysUserId);
}