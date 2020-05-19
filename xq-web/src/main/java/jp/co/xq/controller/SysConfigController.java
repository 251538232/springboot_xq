package jp.co.xq.controller;

import jp.co.xq.controller.base.BaseController;
import jp.co.xq.base.common.BaseResult;
import jp.co.xq.base.utils.ConditionUtils;
import jp.co.xq.base.utils.PageUtils;
import jp.co.xq.service.sys.model.SysConfig;
import jp.co.xq.service.sys.model.SysConfigExample;
import jp.co.xq.service.sys.service.SysConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * sysConfigインターフェース
 *
 * @author tian w 2018/6/28.
 */
@RestController
@RequestMapping("/sysConfig")
@Api(value = "sysConfigコントローラー", description = "sysConfig管理")
public class SysConfigController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(SysConfigController.class);

    @Autowired
    SysConfigService sysConfigService;

    @ApiOperation(value = "SysConfig情報一覧", httpMethod = "GET")
    @RequestMapping(value = "/list", method = {RequestMethod.GET})
    public Object list(@RequestParam Map conditionMap) throws Exception {

        ConditionUtils condition = new ConditionUtils<>(new SysConfigExample());
        // 条件からデータを取得する
        SysConfigExample sysConfigExample = (SysConfigExample) condition.createExampleByMap(conditionMap);
        List dataList = sysConfigService.selectByExample(sysConfigExample);
        // 外ページ処理しない
        sysConfigExample.setLimit(null);
        PageUtils pageData = new PageUtils(dataList, sysConfigService.countByExample(sysConfigExample), conditionMap);
        return BaseResult.ok(pageData);
    }

    @ApiOperation(value = "詳細データ取得", httpMethod = "GET")
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public Object detail(@PathVariable("id") Long id) {
        SysConfig sysConfig = sysConfigService.selectByPrimaryKey(id);
        return BaseResult.ok(sysConfig);
    }

    @ApiOperation(value = "新規", httpMethod = "POST")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Object create(@RequestBody SysConfig sysConfig) {
        int count = sysConfigService.insertSelective(sysConfig);
        return BaseResult.ok(count);
    }

    @ApiOperation(value = "更新", httpMethod = "POST")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Object update(@RequestBody SysConfig sysConfig) {
        int count = sysConfigService.updateByPrimaryKey(sysConfig);
        return BaseResult.ok(count);
    }

    @ApiOperation(value = "削除（複数IDが存在する場合、カンマ区切り）", httpMethod = "GET")
    @RequestMapping(value = "/delete/{ids}", method = RequestMethod.GET)
    public Object delete(@PathVariable("ids") String ids) {
        int count = sysConfigService.deleteByPrimaryKeys(ids);
        return BaseResult.ok(count);
    }


    /**
     * タイプを条件にして、システムコンフィグを取得（ドロップリスト用）
     * 性能アップのため、キャッシュにから取る処理追加
     *
     * @param type システムコンフィグタイプ
     * @return システムコンフィグリスト
     */
    @ApiOperation(value = "タイプを条件にして、システムコンフィグを取得", httpMethod = "GET")
    @RequestMapping(value = "/getByType", method = {RequestMethod.GET})
    public Object getByType(@RequestParam String type) {
        SysConfigExample configExample = new SysConfigExample();
        // 該当タイプ、かつ設定が有効
        configExample.createCriteria()
                .andTypeEqualTo(type)
                .andDisableEqualTo(0);
        List<SysConfig> sysConfigList = sysConfigService.selectByExample(configExample);
        return BaseResult.ok(sysConfigList);
    }
}