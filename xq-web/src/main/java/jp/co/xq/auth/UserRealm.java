package jp.co.xq.auth;

import jp.co.xq.base.common.BaseConstants;
import jp.co.xq.service.sys.model.SysAccessToken;
import jp.co.xq.service.sys.model.SysUser;
import jp.co.xq.service.sys.service.SysUserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * 認証処理
 *
 * @author t
 */
@Component
public class UserRealm extends AuthorizingRealm {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private SysUserService sysUserService;

    // TODO
    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof AccessToken;
    }

    /**
     * ユーザーModelより、認証情報を取得する
     *
     * @param principals
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SysUser sysUser = (SysUser) principals.getPrimaryPrincipal();
        LOGGER.debug("doGetAuthenticationInfo : " + sysUser.toString());
        // ユーザー権限を取得
        Set<String> permsSet = sysUserService.getPermsSet(sysUser.getId());

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.setStringPermissions(permsSet);
        return authorizationInfo;
    }

    /**
     * 認証Tokenより、認証情報を取得する
     *
     * @param token
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String accessToken = (String) token.getPrincipal();
        LOGGER.debug("doGetAuthenticationInfo : " + accessToken);

        // tokenよりユーザー情報を取得する todo キャッシュ処理追加必要
        SysAccessToken sysAccessToken = sysUserService.getSysAccessToken(accessToken);
        // token有効期限切れた
        if (sysAccessToken == null) {
            throw new IncorrectCredentialsException("invalid token.");
        }
        if (sysAccessToken.getExpireTime().getTime() < System.currentTimeMillis()) {
            throw new ExpiredCredentialsException("token expired.");
        }
        // ユーザー情報を取得
        SysUser sysUser = sysUserService.selectByPrimaryKey(sysAccessToken.getUserId());
        // アカウント状態判定
        switch (sysUser.getStatus()) {
            case BaseConstants.SYS_USER_STATUS_LOCK:
                LOGGER.info("ユーザー状態：locked user");
                throw new LockedAccountException();
            case BaseConstants.SYS_USER_STATUS_OK:
                LOGGER.info("ユーザー状態：ok");
                break;
            case BaseConstants.SYS_USER_STATUS_LIMIT:
                LOGGER.info("ユーザー状態：limited");
                break;
            default:
                break;
        }
        return new SimpleAuthenticationInfo(sysUser, accessToken, getName());
    }
}
