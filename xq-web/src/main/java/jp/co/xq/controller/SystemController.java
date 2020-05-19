package jp.co.xq.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jp.co.xq.base.common.BaseResult;
import jp.co.xq.controller.base.BaseController;
import org.apache.shiro.crypto.hash.Sha1Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * ユーザー登録処理
 *
 * @author tian
 */
@RestController
@RequestMapping("/")
@Api(value = "システム管理者専用コントローラー", description = "システム管理者専用コントローラー")
public class SystemController extends BaseController {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * キャッシュクリア
     *
     * @return 処理結果
     */
    @ResponseBody
    @ApiOperation(value = "キャッシュクリア", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/redis/flushDB", method = {RequestMethod.GET})
    public Object flushDB() {
        redisTemplate.execute((RedisCallback) connection -> {
            connection.flushDb();
            return null;
        });

        return BaseResult.ok("redis flushDB success");
    }


    /**
     * システム時間
     * @return 処理結果
     */
    @ResponseBody
    @ApiOperation(value = "システム時間", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/sysTime", method = {RequestMethod.GET})
    public Object getSystemTime() {
        Date now = new Date();
        return BaseResult.ok(now.getTime());
    }

    public static void main(String[] args) {
        System.out.println(new Sha1Hash("admin", "show me the money"));
    }
}
