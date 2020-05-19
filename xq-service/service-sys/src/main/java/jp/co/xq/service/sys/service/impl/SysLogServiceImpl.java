package jp.co.xq.service.sys.service.impl;

import jp.co.xq.service.base.common.BaseServiceImpl;
import jp.co.xq.service.sys.mapper.SysLogMapper;
import jp.co.xq.service.sys.model.SysLog;
import jp.co.xq.service.sys.model.SysLogExample;
import jp.co.xq.service.sys.service.SysLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * SysLogService処理
 * @author tian w 2018/6/28.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SysLogServiceImpl extends BaseServiceImpl<SysLogMapper, SysLog, SysLogExample> implements SysLogService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SysLogServiceImpl.class);

    @Autowired
    SysLogMapper sysLogMapper;

}