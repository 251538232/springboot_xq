package jp.co.xq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * アプリケーション起動
 *
 * @author tian
 */
@ComponentScan("jp.co.xq")
@SpringBootApplication
@EnableTransactionManagement
@EnableSwagger2
@EnableScheduling
public class XqApplication {

    public static void main(String[] args) {
        SpringApplication.run(XqApplication.class, args);
    }

}
