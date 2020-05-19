package jp.co.xq.service.sys.service.impl;

import jp.co.xq.base.common.BaseConstants;
import jp.co.xq.service.base.common.BaseServiceImpl;
import jp.co.xq.service.base.utils.TokenGenerator;
import jp.co.xq.service.sys.mapper.SysAccessTokenMapper;
import jp.co.xq.service.sys.model.SysAccessToken;
import jp.co.xq.service.sys.model.SysAccessTokenExample;
import jp.co.xq.service.sys.service.SysAccessTokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * SysAccessTokenService処理
 *
 * @author tian w 2018/6/20.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SysAccessTokenServiceImpl extends BaseServiceImpl<SysAccessTokenMapper, SysAccessToken, SysAccessTokenExample> implements SysAccessTokenService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SysAccessTokenServiceImpl.class);

    @Autowired
    SysAccessTokenMapper sysAccessTokenMapper;

    /**
     * アクセスTokenを取得する
     *
     * @param sysUserId システムユーザーＩＤ
     * @return Token作成結果
     */
    @Override
    public Map createAccessToken(Long sysUserId) {
        // 作成時間
        Date createTime = new Date();
        // 有効期限
        Date expireTime = new Date(createTime.getTime() + BaseConstants.ACCESS_EXPIRE_TIME);

        // 認証Token存在チェック
        SysAccessTokenExample accessTokenExample = new SysAccessTokenExample();
        accessTokenExample.createCriteria().andUserIdEqualTo(sysUserId);
        SysAccessToken accessToken = selectOneByExample(accessTokenExample);
        if (accessToken == null) {
            // 認証Token
            String token = TokenGenerator.generate();

            accessToken = new SysAccessToken();
            // ユーザーＩＤ設定
            accessToken.setUserId(sysUserId);
            // 認証Tokenを取得する
            accessToken.setAccessToken(token);
            // 作成時間
            accessToken.setCreateTime(createTime);
            // 有効期限
            accessToken.setExpireTime(expireTime);
            // 更新時間
            accessToken.setUpdateTime(createTime);
            // Token新規登録
            sysAccessTokenMapper.insertSelective(accessToken);
        } else {
            // 有効期間を更新する
            accessToken.setUpdateTime(createTime);
            accessToken.setExpireTime(expireTime);
            // データ更新処理
            sysAccessTokenMapper.updateByPrimaryKey(accessToken);
        }
        Map<String, Object> result = new HashMap<>(2);
        result.put("accessToken", accessToken.getAccessToken());
        result.put("expireTime", accessToken.getExpireTime());
        return result;
    }
}