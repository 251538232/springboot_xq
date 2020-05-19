package jp.co.xq.auth;


import org.apache.shiro.authc.AuthenticationToken;

/**
 * access token
 *
 * @author t
 */
public class AccessToken implements AuthenticationToken {
    private String accessToken;

    public AccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public String getPrincipal() {
        return accessToken;
    }

    @Override
    public Object getCredentials() {
        return accessToken;
    }
}
