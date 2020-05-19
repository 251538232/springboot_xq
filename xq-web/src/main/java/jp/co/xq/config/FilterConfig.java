package jp.co.xq.config;

import jp.co.xq.auth.ShiroAuthenticatingFilter;
import jp.co.xq.xss.XssFilter;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author tian
 */
@Configuration
public class FilterConfig {

    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(securityManager);

        // oauth認証
        Map<String, Filter> filters = new HashMap<>();
        filters.put("oauth2", new ShiroAuthenticatingFilter());
        shiroFilter.setFilters(filters);

        Map<String, String> filterMap = new LinkedHashMap<>();
//        // swagger
//        filterMap.put("/v2/api-docs", "anon");
//        filterMap.put("/webjars/**", "anon");
//        filterMap.put("/swagger/**", "anon");
//        filterMap.put("/swagger-ui.html", "anon");
//        filterMap.put("/swagger-resources/**", "anon");
//        // alibaba druid
//        filterMap.put("/druid/**", "anon");
//
//        /**
//         * その他API個別設定
//         */
//        filterMap.put("/login", "anon");
//        filterMap.put("/logout", "anon");
//        filterMap.put("/actuator/**", "anon");
//        // 打刻
//        filterMap.put("/daily/punchByUser**", "anon");
//        filterMap.put("/test**", "anon");
//        filterMap.put("/**", "oauth2");


        // 開発用で、すべてのＡＰＩが見れるように
        filterMap.put("/**", "anon");
        shiroFilter.setFilterChainDefinitionMap(filterMap);

        return shiroFilter;
    }

    @Bean
    public FilterRegistrationBean xssFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setDispatcherTypes(DispatcherType.REQUEST);
        registration.setFilter(new XssFilter());
        registration.addUrlPatterns("/*");
        registration.setName("xssFilter");
        registration.setOrder(Integer.MAX_VALUE);
        return registration;
    }
}
