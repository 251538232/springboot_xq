package jp.co.xq.aspect;

import jp.co.xq.annotation.RequestLimit;
import jp.co.xq.base.exception.AppException;
import jp.co.xq.base.utils.IPUtils;
import jp.co.xq.service.base.utils.SpringContextUtils;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * API リクエストの制限処理
 *
 * @author t
 */
public class RequestLimitAspect {

    RedisTemplate<String, Integer> redisTemplate;

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestLimitAspect.class);

    ScheduledExecutorService schedule = new ScheduledThreadPoolExecutor(5);

    /**
     * redisTemplate取得処理
     *
     * @return redisTemplate対象オブジェクト
     */
    private RedisTemplate getRedisTemplate() {
        if (redisTemplate == null) {
            redisTemplate = SpringContextUtils.getBean("redisTemplate", RedisTemplate.class);
        }
        return redisTemplate;
    }

    /**
     * redisを利用してリクエスト制限処理を行う
     *
     * @param limit 制限annotation
     * @throws AppException 異常処理
     */
    @Before("within(@org.springframework.web.bind.annotation.RestController *) && @annotation(limit)")
    public void requestLimit(RequestLimit limit) throws AppException {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String ipAddress = IPUtils.getIpAddr(request);
        String requestUrl = request.getRequestURL().toString();
        String key = requestUrl + "_" + ipAddress;
        // redisTemplate取得
        getRedisTemplate();

        if (!redisTemplate.hasKey(key)) {
            redisTemplate.opsForValue().set(key, 1);
        } else {
            redisTemplate.opsForValue().set(key, redisTemplate.opsForValue().get(key) + 1);
        }
        // 制限回数
        int count = redisTemplate.opsForValue().get(key);
        if (count > 0) {
            schedule.schedule(() -> redisTemplate.delete(key), limit.interval(), TimeUnit.SECONDS);
        }
        // 制限に超えた場合
        if (count > limit.count()) {
            LOGGER.info("ip: {}, request: {}, times: {}", ipAddress, requestUrl, limit.count());
            throw new AppException("リクエスト制限数に達したため、しばらくお待ちください。");
        }
    }
}  