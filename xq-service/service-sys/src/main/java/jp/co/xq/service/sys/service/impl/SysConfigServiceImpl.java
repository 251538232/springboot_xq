package jp.co.xq.service.sys.service.impl;

import jp.co.xq.service.base.common.BaseServiceImpl;
import jp.co.xq.service.sys.mapper.SysConfigMapper;
import jp.co.xq.service.sys.model.SysConfig;
import jp.co.xq.service.sys.model.SysConfigExample;
import jp.co.xq.service.sys.service.SysConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * SysConfigService処理
 * @author tian w 2018/6/28.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SysConfigServiceImpl extends BaseServiceImpl<SysConfigMapper, SysConfig, SysConfigExample> implements SysConfigService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SysConfigServiceImpl.class);

    @Autowired
    SysConfigMapper sysConfigMapper;

}