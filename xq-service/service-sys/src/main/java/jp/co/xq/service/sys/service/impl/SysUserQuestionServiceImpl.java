package jp.co.xq.service.sys.service.impl;

import jp.co.xq.service.base.common.BaseServiceImpl;
import jp.co.xq.service.sys.mapper.SysUserQuestionMapper;
import jp.co.xq.service.sys.model.SysUserQuestion;
import jp.co.xq.service.sys.model.SysUserQuestionExample;
import jp.co.xq.service.sys.service.SysUserQuestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * SysUserQuestionService処理
 *
 * @author sci 2018/9/4.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SysUserQuestionServiceImpl extends BaseServiceImpl<SysUserQuestionMapper, SysUserQuestion, SysUserQuestionExample> implements SysUserQuestionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SysUserQuestionServiceImpl.class);

    @Autowired
    SysUserQuestionMapper sysUserQuestionMapper;

}