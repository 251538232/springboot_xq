package jp.co.xq.auth;

import com.alibaba.fastjson.JSON;
import jp.co.xq.base.common.BaseConstants;
import jp.co.xq.base.common.BaseResult;
import jp.co.xq.base.utils.RequestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Shiro認証フィルター
 *
 * @author t
 */
public class ShiroAuthenticatingFilter extends AuthenticatingFilter {

    private final static Logger LOGGER = LoggerFactory.getLogger(ShiroAuthenticatingFilter.class);

    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) throws Exception {
        LOGGER.debug("createToken.");
        String token = RequestUtils.getAccessToken((HttpServletRequest) request);
        if (StringUtils.isEmpty(token)) {
            LOGGER.error("token is empty.");
            return null;
        }
        return new AccessToken(token);
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        LOGGER.debug("isAccessAllowed.");
        if (((HttpServletRequest) request).getMethod().equals(RequestMethod.OPTIONS.name())) {
            LOGGER.info("access allowed.");
            return true;
        }
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        LOGGER.debug("onAccessDenied.");
        // 認証Tokenを取得する
        String token = RequestUtils.getAccessToken((HttpServletRequest) request);
        if (StringUtils.isEmpty(token)) {
            HttpServletResponse httpResponse = createResponse(request, response);
            LOGGER.error("invalid token.");
            String json = JSON.toJSONString(BaseResult.error(HttpStatus.UNAUTHORIZED.value(), "invalid token", null));
            httpResponse.getWriter().print(json);
            return false;
        }

        return executeLogin(request, response);
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        LOGGER.debug("onLoginFailure.");
        HttpServletResponse httpResponse = createResponse(request, response);
        try {
            // 登録異常
            Throwable throwable = e.getCause() == null ? e : e.getCause();
            BaseResult result = BaseResult.error(HttpStatus.UNAUTHORIZED.value(), throwable.getMessage(), null);
            LOGGER.error("unauthorized.", throwable);
            String json = JSON.toJSONString(result);
            httpResponse.getWriter().print(json);
        } catch (IOException e1) {
            e1.printStackTrace();
            LOGGER.error(e.getMessage(), e1);
        }
        return false;
    }

    /**
     * レスポンス作成処理
     *
     * @param request
     * @param response
     * @return レスポンス
     */
    private HttpServletResponse createResponse(ServletRequest request, ServletResponse response) {
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setContentType("application/json;charset=utf-8");
        httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
        httpServletResponse.setHeader("Access-Control-Allow-Origin", ((HttpServletRequest) request).getHeader(BaseConstants.HTTP_HEADER_ORIGIN));
        return httpServletResponse;
    }

}
