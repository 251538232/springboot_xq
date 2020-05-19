package jp.co.xq.controller;

import jp.co.xq.controller.base.BaseController;
import jp.co.xq.base.common.BaseResult;
import jp.co.xq.base.utils.ConditionUtils;
import jp.co.xq.base.utils.PageUtils;
import jp.co.xq.service.sys.model.SysAccessToken;
import jp.co.xq.service.sys.model.SysAccessTokenExample;
import jp.co.xq.service.sys.service.SysAccessTokenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * sysAccessTokenインターフェース
 * @author tian w 2018/6/28.
 */
@RestController
@RequestMapping("/sysAccessToken")
@Api(value = "sysAccessTokenコントローラー", description = "sysAccessToken管理")
public class SysAccessTokenController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(SysAccessTokenController.class);

    @Autowired
    SysAccessTokenService sysAccessTokenService;

    @ApiOperation(value = "SysAccessToken情報一覧", httpMethod = "GET")
    @RequestMapping(value = "/list", method = {RequestMethod.GET})
    public Object list(@RequestParam Map conditionMap) throws Exception {

        ConditionUtils condition = new ConditionUtils<>(new SysAccessTokenExample());
        // 条件からデータを取得する
        SysAccessTokenExample sysAccessTokenExample = (SysAccessTokenExample) condition.createExampleByMap(conditionMap);
        List dataList = sysAccessTokenService.selectByExample(sysAccessTokenExample);
        // 外ページ処理しない
        sysAccessTokenExample.setLimit(null);
        PageUtils pageData = new PageUtils(dataList, sysAccessTokenService.countByExample(sysAccessTokenExample), conditionMap);
        return BaseResult.ok(pageData);
    }

    @ApiOperation(value = "詳細データ取得", httpMethod = "GET")
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public Object detail(@PathVariable("id") Long id) {
        SysAccessToken sysAccessToken = sysAccessTokenService.selectByPrimaryKey(id);
        return BaseResult.ok(sysAccessToken);
    }

    @ApiOperation(value = "新規", httpMethod = "POST")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Object create(@RequestBody SysAccessToken sysAccessToken) {
        int count = sysAccessTokenService.insertSelective(sysAccessToken);
        return BaseResult.ok(count);
    }

    @ApiOperation(value = "更新", httpMethod = "POST")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Object update(@RequestBody SysAccessToken sysAccessToken) {
        int count = sysAccessTokenService.updateByPrimaryKey(sysAccessToken);
        return BaseResult.ok(count);
    }

    @ApiOperation(value = "削除（複数IDが存在する場合、カンマ区切り）", httpMethod = "GET")
    @RequestMapping(value = "/delete/{ids}", method = RequestMethod.GET)
    public Object delete(@PathVariable("ids") String ids) {
        int count = sysAccessTokenService.deleteByPrimaryKeys(ids);
        return BaseResult.ok(count);
    }
}