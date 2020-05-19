package jp.co.xq.controller.base;

import jp.co.xq.base.common.BaseResult;
import jp.co.xq.base.exception.AppException;
import jp.co.xq.base.utils.RequestUtils;
import org.apache.shiro.authz.UnauthenticatedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Locale;

/**
 * Controllerベース処理
 *
 * @author t
 */
public abstract class BaseController {

    private final static Logger LOGGER = LoggerFactory.getLogger(BaseController.class);

    @Autowired
    private MessageSource messageSource;

    /**
     * 異常処理
     *
     * @param request
     * @param response
     * @param exception
     */
    @ResponseBody
    @ExceptionHandler
    public Object exceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception exception) {
        LOGGER.error("異常情報：", exception);
        // 権限なし
        if (exception instanceof UnauthenticatedException) {
            return BaseResult.error(getMessage("shiro.error.nopermissions"), null);
        }
        // その他異常
        return BaseResult.error(exception.getMessage(), exception.getLocalizedMessage());
    }

    public HttpServletRequest getHttpServletRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    public HttpServletResponse getHttpServletResponse() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
    }

    public String getAccessToken() {
        return RequestUtils.getAccessToken(getHttpServletRequest());
    }

    public ServletContext getServletContext() {
        return ContextLoader.getCurrentWebApplicationContext().getServletContext();
    }

    public HttpSession getSession() {
        return getHttpServletRequest().getSession();
    }

    public Object getSessionAttribute(String key) {
        return getSession().getAttribute(key);
    }

    /**
     * 画面キャプチャがある場合、Sessionからキャプチャを取得する
     * 【サーバー複数ある場合、Session保存処理をやめて、Redisに保存するように修正が必要です】
     * TODO
     *
     * @param key
     * @return 認証コード
     */
    public String getKaptcha(String key) {
        Object kaptcha = getSessionAttribute(key);
        if (kaptcha == null) {
            throw new AppException("認証コード無効");
        }
        getSession().removeAttribute(key);
        return kaptcha.toString();
    }

    /**
     * 多言語対応
     *
     * @param key
     * @return
     */
    public String getMessage(String key) {
        return getMessage(key, new String[]{});
    }

    /**
     * 多言語対応
     *
     * @param key
     * @param params
     * @return
     */
    public String getMessage(String key, String[] params) {
        String message = "";
        try {
            Locale locale = LocaleContextHolder.getLocale();
            message = messageSource.getMessage(key, params, locale);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return message;
    }
}
