package jp.co.xq.config;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;


/**
 * キャプチャーコンフィグ
 */
@Configuration
public class KaptchaConfig {

    //      <props>
//　　            <!-- ＢＯＲＤＥＲ設定 -->
//            　　<prop key="kaptcha.border">no</prop>
//            　　<!-- ＢＯＲＤＥＲカーラー -->
//            　　<prop key="kaptcha.border.color">105,179,90</prop>
//            　　<!-- Ｆｏｎｔ -->
//            　　<prop key="kaptcha.textproducer.font.color">black</prop>
//            　　<!-- 広さ -->
//            　　<prop key="kaptcha.image.width">100</prop>
//            　　<!-- 高さ -->
//            　　<prop key="kaptcha.image.height">50</prop>
//            　　<!-- 文字の大きさ -->
//            　　<prop key="kaptcha.textproducer.font.size">30</prop>
//            　　<!-- 文字数 -->
//            　　<prop key="kaptcha.textproducer.char.length">4</prop>
//            　　<!-- 文字のスタイル -->
//            　　<prop key="kaptcha.textproducer.font.names">明朝</prop>
//            　　<prop key="kaptcha.noise.impl">com.google.code.kaptcha.impl.NoNoise</prop>
//            　　<prop key="kaptcha.textproducer.char.string">0123456789abcdefghijklmnopqrstuvwxyz</prop>
//      </props>
    @Bean
    public DefaultKaptcha producer() {
        Properties properties = new Properties();
        properties.put("kaptcha.border", "no");
        properties.put("kaptcha.textproducer.font.color", "black");
        properties.put("kaptcha.textproducer.char.space", "5");
        properties.put("kaptcha.textproducer.char.length", "4");
        Config config = new Config(properties);
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }
}
