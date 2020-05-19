package jp.co.xq.annotation;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.lang.annotation.*;


@Documented
@Target(ElementType.METHOD)
@Order(Ordered.HIGHEST_PRECEDENCE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestLimit {

    /**
     * 単位時間(s)
     * interval default 1s
     */
    long interval() default 1;

    /**
     * 一定時間内リクエスト回数
     * accepted request count.
     */
    int count() default Integer.MAX_VALUE;
}