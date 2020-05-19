package jp.co.xq.controller.base;

import io.swagger.annotations.ApiOperation;
import jp.co.xq.base.common.BaseConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.LocaleResolver;

import java.util.Locale;

import static java.lang.Thread.sleep;

/**
 * ここは@Controllerを使う SpringMVCのロジック
 *
 * @author t
 */
@Slf4j
@Controller
public class BaseViewController extends BaseController {

    @Autowired
    LocaleResolver localeResolver;
//
//    @Autowired
//    RabbitmqSender rabbitmqSender;

    @ResponseBody
    @ApiOperation(value = "画面表示", httpMethod = "GET")
    @RequestMapping("/setLocale")
    public void url(String lang) {
        setLocale(lang);
    }

    @ApiOperation(value = "test", httpMethod = "GET")
    @RequestMapping("/testException")
    public String testException() throws Exception {
        throw new Exception("testException");
    }

//    @ApiOperation(value = "ディフォルト画面表示", httpMethod = "GET")
//    @RequestMapping("/mq")
//    @ResponseBody
//    public WebAsyncTask<String> mq(String name, String data) {
//        // 模拟开启一个异步任务，超时时间为10s
//        WebAsyncTask<String> asyncTask = new WebAsyncTask<>(8 * 1000L, () -> {
//            // 任务处理时间5s，不超时
//            sleep(1 * 1000L);
//            throw new Exception(ERROR_MESSAGE);
//        });
//
//        // 任务执行完成时调用该方法
//        asyncTask.onCompletion(() -> System.out.println("任务执行完成"));
//        System.out.println("继续处理其他事情");
//        return asyncTask;
//    }


    @ApiOperation(value = "ディフォルト画面表示", httpMethod = "GET")
    @RequestMapping("/mq1")
    @ResponseBody
    public String mq1(String name, String data) {
        try {
            sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "task/task";

    }

    /**
     * Local情報設定処理
     *
     * @param lang Local情報
     */
    private void setLocale(String lang) {
        if (lang == null) {
            return;
        }
        if (BaseConstants.LOCAL_CHINA.equals(lang)) {
            localeResolver.setLocale(getHttpServletRequest(), getHttpServletResponse(), Locale.CHINA);
        } else if (BaseConstants.LOCAL_ENGLISH.equals(lang)) {
            localeResolver.setLocale(getHttpServletRequest(), getHttpServletResponse(), Locale.US);
        } else {
            localeResolver.setLocale(getHttpServletRequest(), getHttpServletResponse(), Locale.JAPAN);
        }
    }
}
