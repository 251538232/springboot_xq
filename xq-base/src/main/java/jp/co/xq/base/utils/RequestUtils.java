package jp.co.xq.base.utils;

import jp.co.xq.base.common.BaseConstants;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * requestツール
 *
 * @author T
 */
public class RequestUtils {

    static final int PORT_80 = 80;
    static final int PORT_443 = 443;

    static final String PROTOCOL_HTTP = "http";
    static final String PROTOCOL_HTTPS = "https";

    /**
     * リクエストのbasePathを取得
     *
     * @param request
     * @return
     */
    public static String getBasePath(HttpServletRequest request) {
        StringBuffer basePath = new StringBuffer();
        String scheme = request.getScheme();
        String domain = request.getServerName();
        int port = request.getServerPort();
        basePath.append(scheme);
        basePath.append("://");
        basePath.append(domain);
        if (PROTOCOL_HTTP.equalsIgnoreCase(scheme) && PORT_80 != port) {
            basePath.append(":").append(String.valueOf(port));
        } else if (PROTOCOL_HTTPS.equalsIgnoreCase(scheme) && port != PORT_443) {
            basePath.append(":").append(String.valueOf(port));
        }
        return basePath.toString();
    }

    /**
     * リクエストからtoken取得処理
     *
     * @param request
     * @return ipアドレス
     */
    public static String getAccessToken(HttpServletRequest request) {
        String token = request.getHeader(BaseConstants.HTTP_HEADER_ACCESS_TOKEN);
        if (StringUtils.isEmpty(token)) {
            token = request.getParameter(BaseConstants.HTTP_HEADER_ACCESS_TOKEN);
        }
        return token;
    }

    /**
     * 本物のIPアドレスを取得getRemoteAddr
     *
     * @param request
     * @return ipアドレス
     */
    public static String getIpAddr(HttpServletRequest request) {
        // CDNのIP
        String ip = request.getHeader("Cdn-Src-Ip");
        String unknown = "unknown";
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            // CDNのIP
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            // PROXYサーバーのIP
            ip = request.getHeader("X-Forwarded-For");
        }
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            // PROXYサーバーのIP
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            // PROXYサーバーのIP
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            // 本物のIPアドレス
            ip = request.getRemoteAddr();
        }
        return ip;
    }

}
