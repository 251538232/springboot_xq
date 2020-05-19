package jp.co.xq.service.sys.service.impl;

import jp.co.xq.service.base.common.BaseServiceImpl;
import jp.co.xq.service.sys.mapper.SysAreaMapper;
import jp.co.xq.service.sys.model.SysArea;
import jp.co.xq.service.sys.model.SysAreaExample;
import jp.co.xq.service.sys.service.SysAreaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * SysAreaService処理
 * @author tian w 2018/6/28.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SysAreaServiceImpl extends BaseServiceImpl<SysAreaMapper, SysArea, SysAreaExample> implements SysAreaService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SysAreaServiceImpl.class);

    @Autowired
    SysAreaMapper sysAreaMapper;

}