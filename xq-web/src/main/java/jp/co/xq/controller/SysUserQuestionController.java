package jp.co.xq.controller;

import jp.co.xq.base.common.BaseResult;
import jp.co.xq.base.utils.ConditionUtils;
import jp.co.xq.base.utils.PageUtils;
import jp.co.xq.controller.base.BaseUserController;
import jp.co.xq.service.sys.model.SysUserQuestion;
import jp.co.xq.service.sys.model.SysUserQuestionExample;
import jp.co.xq.service.sys.model.extend.SysUserExtend;
import jp.co.xq.service.sys.service.SysUserQuestionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * sysUserQuestionインターフェース
 *
 * @author sci 2018/9/4.
 */
@RestController
@RequestMapping("/sysUserQuestion")
@Api(value = "sysUserQuestionコントローラー", description = "sysUserQuestion管理")
public class SysUserQuestionController extends BaseUserController {

    private static final Logger logger = LoggerFactory.getLogger(SysUserQuestionController.class);

    @Autowired
    SysUserQuestionService sysUserQuestionService;

    @ApiOperation(value = "SysUserQuestion情報一覧", httpMethod = "GET")
    @RequiresPermissions("sysuserquestion:list")
    @RequestMapping(value = "/list", method = {RequestMethod.GET})
    public Object list(@RequestParam Map conditionMap) throws Exception {

        ConditionUtils condition = new ConditionUtils<>(new SysUserQuestionExample());
        // 条件からデータを取得する
        SysUserQuestionExample sysUserQuestionExample = (SysUserQuestionExample) condition.createExampleByMap(conditionMap);
        SysUserExtend sysUserExtend = getSysUserExtend();
        // システムユーザーの場合 全件見れる
        if (!sysUserExtend.getRoleIdList().contains(1L)) {
            sysUserQuestionExample.getOredCriteria().get(0).andUserIdEqualTo(sysUserExtend.getId());
        }
        List dataList = sysUserQuestionService.selectByExample(sysUserQuestionExample);
        // 改ページ処理しない
        sysUserQuestionExample.setLimit(null);
        PageUtils pageData = new PageUtils(dataList, sysUserQuestionService.countByExample(sysUserQuestionExample), conditionMap);
        return BaseResult.ok(pageData);
    }

    @ApiOperation(value = "詳細データ取得", httpMethod = "GET")
    @RequiresPermissions("sysuserquestion:detail")
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public Object detail(@PathVariable("id") Long id) {
        SysUserQuestion sysUserQuestion = sysUserQuestionService.selectByPrimaryKey(id);
        return BaseResult.ok(sysUserQuestion);
    }

    @ApiOperation(value = "新規", httpMethod = "POST")
    @RequiresPermissions("sysuserquestion:create")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Object create(@RequestBody SysUserQuestion sysUserQuestion) {
        sysUserQuestion.setAnswerContent(null);
        sysUserQuestion.setAnswerUserId(null);
        int count = sysUserQuestionService.insertSelective(sysUserQuestion);
        return BaseResult.ok(count);
    }

    @ApiOperation(value = "更新", httpMethod = "POST")
    @RequiresPermissions("sysuserquestion:update")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Object update(@RequestBody SysUserQuestion sysUserQuestion) {
        int count = sysUserQuestionService.updateByPrimaryKeySelective(sysUserQuestion);
        return BaseResult.ok(count);
    }

    @ApiOperation(value = "更新", httpMethod = "POST")
    @RequiresPermissions("sysuserquestion:answer")
    @RequestMapping(value = "/answer", method = RequestMethod.POST)
    public Object answer(@RequestBody SysUserQuestion sysUserQuestion) {
        sysUserQuestion.setAnswerUserId(getSysUser().getId());
        // 回答済み状態
        sysUserQuestion.setStatus(1);
        sysUserQuestion.setUpdateTime(new Date());
        int count = sysUserQuestionService.updateByPrimaryKeySelective(sysUserQuestion);
        return BaseResult.ok(count);
    }

    @ApiOperation(value = "削除（複数IDが存在する場合、カンマ区切り）ロジック削除処理", httpMethod = "GET")
    @RequiresPermissions("sysuserquestion:delete")
    @RequestMapping(value = "/delete/{ids}", method = RequestMethod.GET)
    public Object delete(@PathVariable("ids") String ids) {
        if (StringUtils.isBlank(ids)) {
            return 0;
        }
        String[] idArray = ids.split(",");
        List<SysUserQuestion> sysUserQuestionList = new ArrayList<>();
        for (String idStr : idArray) {
            if (StringUtils.isBlank(idStr)) {
                continue;
            }
            SysUserQuestion sysUserQuestion = new SysUserQuestion();
            sysUserQuestion.setStatus(2);
            sysUserQuestion.setId(Long.parseLong(idStr));
            sysUserQuestion.setUpdateTime(new Date());
            sysUserQuestionList.add(sysUserQuestion);
        }
        int count = sysUserQuestionService.updateByPrimaryKeySelectivePatch(sysUserQuestionList);
        return BaseResult.ok(count);
    }
}