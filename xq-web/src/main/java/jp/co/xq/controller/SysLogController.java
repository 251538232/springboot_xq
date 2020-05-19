package jp.co.xq.controller;

import jp.co.xq.controller.base.BaseController;
import jp.co.xq.base.common.BaseResult;
import jp.co.xq.base.utils.ConditionUtils;
import jp.co.xq.base.utils.PageUtils;
import jp.co.xq.service.sys.model.SysLog;
import jp.co.xq.service.sys.model.SysLogExample;
import jp.co.xq.service.sys.service.SysLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * sysLogインターフェース
 * @author tian w 2018/6/28.
 */
@RestController
@RequestMapping("/sysLog")
@Api(value = "sysLogコントローラー", description = "sysLog管理")
public class SysLogController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(SysLogController.class);

    @Autowired
    SysLogService sysLogService;

    @ApiOperation(value = "SysLog情報一覧", httpMethod = "GET")
    @RequestMapping(value = "/list", method = {RequestMethod.GET})
    public Object list(@RequestParam Map conditionMap) throws Exception {

        ConditionUtils condition = new ConditionUtils<>(new SysLogExample());
        // 条件からデータを取得する
        SysLogExample sysLogExample = (SysLogExample) condition.createExampleByMap(conditionMap);
        List dataList = sysLogService.selectByExample(sysLogExample);
        // 外ページ処理しない
        sysLogExample.setLimit(null);
        PageUtils pageData = new PageUtils(dataList, sysLogService.countByExample(sysLogExample), conditionMap);
        return BaseResult.ok(pageData);
    }

    @ApiOperation(value = "詳細データ取得", httpMethod = "GET")
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public Object detail(@PathVariable("id") Long id) {
        SysLog sysLog = sysLogService.selectByPrimaryKey(id);
        return BaseResult.ok(sysLog);
    }

    @ApiOperation(value = "新規", httpMethod = "POST")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Object create(@RequestBody SysLog sysLog) {
        int count = sysLogService.insertSelective(sysLog);
        return BaseResult.ok(count);
    }

    @ApiOperation(value = "更新", httpMethod = "POST")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Object update(@RequestBody SysLog sysLog) {
        int count = sysLogService.updateByPrimaryKey(sysLog);
        return BaseResult.ok(count);
    }

    @ApiOperation(value = "削除（複数IDが存在する場合、カンマ区切り）", httpMethod = "GET")
    @RequestMapping(value = "/delete/{ids}", method = RequestMethod.GET)
    public Object delete(@PathVariable("ids") String ids) {
        int count = sysLogService.deleteByPrimaryKeys(ids);
        return BaseResult.ok(count);
    }
}