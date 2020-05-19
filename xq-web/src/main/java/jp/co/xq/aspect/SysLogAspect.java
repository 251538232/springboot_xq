package jp.co.xq.aspect;

import com.alibaba.fastjson.JSON;
import jp.co.xq.base.common.BaseResult;
import jp.co.xq.base.utils.IPUtils;
import jp.co.xq.base.utils.RequestUtils;
import jp.co.xq.service.sys.model.SysLog;
import jp.co.xq.service.sys.model.SysUser;
import jp.co.xq.service.sys.service.SysLogService;
import jp.co.xq.service.sys.service.SysUserService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * ログアスペクト処理
 *SysLogAspect
 * @author tian
 */
@Aspect
@Component
public class SysLogAspect {

    private long startTime;

    private long endTime;

    private static final Logger LOGGER = LoggerFactory.getLogger(SysLogAspect.class);

    private static final int PARAM_MAX_LENGTH = 1000;

    @Autowired
    SysUserService sysUserService;

    @Autowired
    SysLogService sysLogService;

    @Before("execution(* jp.co.xq.controller..*.*(..))")
    public void before(JoinPoint joinPoint) {
        LOGGER.debug("before");
        startTime = System.currentTimeMillis();
    }

    @After("execution(* jp.co.xq.controller..*.*(..))")
    public void after(JoinPoint joinPoint) {
        LOGGER.debug("after");
    }

    @Around("execution(* jp.co.xq.controller..*.*(..))")
    public Object around(ProceedingJoinPoint pjp) {
        LOGGER.debug("around");

        MethodSignature signature = (MethodSignature) pjp.getSignature();
        SysLog sysLog = new SysLog();
        String className = pjp.getTarget().getClass().getName();
        String methodName = signature.getName();
        sysLog.setRequestMethod(className + "." + methodName + "()");
        // リクエストパラメータ
        Object result = null;
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
        HttpServletRequest request = servletRequestAttributes.getRequest();
        try {
            result = pjp.proceed();
            endTime = System.currentTimeMillis();
            LOGGER.debug(request.getMethod() + "spend time (ms)：{}  request result:{}", endTime - startTime, result);
        } catch (Throwable t) {
            t.printStackTrace();
            return BaseResult.error(t.getMessage(), t.getLocalizedMessage());
        }
        sysLog.setIpAddress(IPUtils.getIpAddr(request));
        Object[] args = pjp.getArgs();
        if (args.length > 0) {
            if (args[0] instanceof MultipartFile) {
                sysLog.setRequestParams(((MultipartFile) args[0]).getOriginalFilename());
            } else {
                if (args[0].toString().length() <= PARAM_MAX_LENGTH) {
                    sysLog.setRequestParams(JSON.toJSONString(args[0]));
                }
            }
        }
        // ユーザー情報を取得
        String token = RequestUtils.getAccessToken(request);
        if (token != null) {
            SysUser sysUser = sysUserService.getSysUserByAccessToken(token);
            if (sysUser != null) {
                sysLog.setUsername(sysUser.getUsername());
            }
        }
        sysLog.setOperation(request.getMethod());
        sysLog.setExecuteTime(endTime - startTime);
        sysLog.setCreateTime(new Date());
        // システムログをＤＢに登録する
        sysLogService.insertSelective(sysLog);
        return result;
    }

}
