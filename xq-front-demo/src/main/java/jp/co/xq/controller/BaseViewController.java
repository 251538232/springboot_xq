package jp.co.xq.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * ここは@Controllerを使う SpringMVCのロジック
 *
 * @author t
 */
@Controller
public class BaseViewController {

    @RequestMapping("/module/{sub}/{url}.html")
    public String sys(@PathVariable("sub") String sub, @PathVariable("url") String url) {
        return "module/" + sub + "/" + url;
    }

    @RequestMapping("/{url}.html")
    public String url(@PathVariable("url") String url) {
        return url;
    }
//    NativeQueryImpl query = (NativeQueryImpl) QueryUtil
//        .createNativeQuery(_entityManager, this.getSQLBuilder());
//    query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
    @RequestMapping("")
    public String url() {
        return "login";
    }

}
