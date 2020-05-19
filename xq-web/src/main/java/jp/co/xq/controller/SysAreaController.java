package jp.co.xq.controller;

import jp.co.xq.controller.base.BaseController;
import jp.co.xq.base.common.BaseResult;
import jp.co.xq.base.utils.ConditionUtils;
import jp.co.xq.base.utils.PageUtils;
import jp.co.xq.service.sys.model.SysArea;
import jp.co.xq.service.sys.model.SysAreaExample;
import jp.co.xq.service.sys.service.SysAreaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * sysAreaインターフェース
 * @author tian w 2018/6/28.
 */
@RestController
@RequestMapping("/sysArea")
@Api(value = "sysAreaコントローラー", description = "sysArea管理")
public class SysAreaController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(SysAreaController.class);

    @Autowired
    SysAreaService sysAreaService;

    @ApiOperation(value = "SysArea情報一覧", httpMethod = "GET")
    @RequestMapping(value = "/list", method = {RequestMethod.GET})
    public Object list(@RequestParam Map conditionMap) throws Exception {

        ConditionUtils condition = new ConditionUtils<>(new SysAreaExample());
        // 条件からデータを取得する
        SysAreaExample sysAreaExample = (SysAreaExample) condition.createExampleByMap(conditionMap);
        List dataList = sysAreaService.selectByExample(sysAreaExample);
        // 外ページ処理しない
        sysAreaExample.setLimit(null);
        PageUtils pageData = new PageUtils(dataList, sysAreaService.countByExample(sysAreaExample), conditionMap);
        return BaseResult.ok(pageData);
    }

    @ApiOperation(value = "詳細データ取得", httpMethod = "GET")
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public Object detail(@PathVariable("id") Long id) {
        SysArea sysArea = sysAreaService.selectByPrimaryKey(id);
        return BaseResult.ok(sysArea);
    }

    @ApiOperation(value = "新規", httpMethod = "POST")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Object create(@RequestBody SysArea sysArea) {
        int count = sysAreaService.insertSelective(sysArea);
        return BaseResult.ok(count);
    }

    @ApiOperation(value = "更新", httpMethod = "POST")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Object update(@RequestBody SysArea sysArea) {
        int count = sysAreaService.updateByPrimaryKey(sysArea);
        return BaseResult.ok(count);
    }

    @ApiOperation(value = "削除（複数IDが存在する場合、カンマ区切り）", httpMethod = "GET")
    @RequestMapping(value = "/delete/{ids}", method = RequestMethod.GET)
    public Object delete(@PathVariable("ids") String ids) {
        int count = sysAreaService.deleteByPrimaryKeys(ids);
        return BaseResult.ok(count);
    }
}