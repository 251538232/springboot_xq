package jp.co.xq.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import jp.co.xq.annotation.RequestLimit;
import jp.co.xq.base.common.BaseResult;
import jp.co.xq.controller.base.BaseController;
import jp.co.xq.service.sys.model.SysUser;
import jp.co.xq.service.sys.model.SysUserExample;
import jp.co.xq.service.sys.service.SysAccessTokenService;
import jp.co.xq.service.sys.service.SysUserService;
import org.apache.shiro.crypto.hash.Sha1Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * ユーザー登録処理
 *
 * @author tian
 */
@RestController
@RequestMapping("/")
@Api(value = "認証コントローラー", description = "ユーザー認証管理")
public class LoginController extends BaseController {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysAccessTokenService sysAccessTokenService;


    /**
     * ユーザー名とパスワードで認証登録
     *
     * @param username ユーザー名
     * @param password パスワード
     * @return ログイン結果
     */
    @RequestLimit(count = 1, interval = 1)
    @ResponseBody
    @ApiOperation(value = "ユーザーログイン処理", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/login", params = {"username", "password"}, method = {RequestMethod.GET})
    public Object login(@ApiParam(value = "ユーザー名") String username, @ApiParam(value = "パスワード") String password) {

        SysUserExample condition = new SysUserExample();
        condition.createCriteria().andUsernameEqualTo(username);
        SysUser sysUser = sysUserService.selectOneByExample(condition);

        // ユーザーが存在しない場合
        if (sysUser == null) {
            return BaseResult.error(getMessage("login.error.nouser"));
        }

        // パスワードが不正の場合
        if (!new Sha1Hash(password, sysUser.getSalt()).toHex().equals(sysUser.getPassword())) {
            return BaseResult.error(getMessage("login.error.loginfailed"));
        }
        // ユーザーがロックした場合
        if (sysUser.getStatus() == 0) {
            return BaseResult.error(getMessage("login.error.lockeduser"));
        }

        // Token作成して、ＤＢに保存する
        Map mkResult = sysAccessTokenService.createAccessToken(sysUser.getId());

        // ユーザー個人情報を取得して、キャッシュに保存

        return BaseResult.ok(mkResult);
    }


    /**
     * ログアウト処理
     *
     * @return 処理結果
     */
    @ResponseBody
    @ApiOperation(value = "ユーザーログアウト処理", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/logout", method = {RequestMethod.GET})
    public Object logout() {
        // 何もしない、フロント側AccessTokenを削除?
        return BaseResult.ok();
    }

    public static void main(String[] args) {
        System.out.println(new Sha1Hash("admin", "show me the money"));
    }
}
