package jp.co.xq.service.sys.service.impl;

import jp.co.xq.service.base.common.BaseServiceImpl;
import jp.co.xq.service.sys.mapper.SysMenuMapper;
import jp.co.xq.service.sys.mapper.extend.SysMenuExtendMapper;
import jp.co.xq.service.sys.model.SysMenu;
import jp.co.xq.service.sys.model.SysMenuExample;
import jp.co.xq.service.sys.model.extend.SysMenuExtend;
import jp.co.xq.service.sys.service.SysMenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * SysMenuService処理
 *
 * @author tian w 2018/6/28.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SysMenuServiceImpl extends BaseServiceImpl<SysMenuMapper, SysMenu, SysMenuExample> implements SysMenuService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SysMenuServiceImpl.class);


    @Autowired
    private SysMenuExtendMapper sysMenuExtendMapper;

    /**
     * ユーザーシステムメニューツリーを構築
     *
     * @param userId システムユーザーＩＤ
     * @return システムメニューツリー
     */
    @Override
    public List<SysMenuExtend> treeMenuListByUserId(Long userId) {
        List<SysMenuExtend> menuList = getMenuListByUserId(userId);
        return buildByRecursive(menuList);
    }

    /**
     * ユーザーのメニューリストを取得する
     *
     * @param userId システムユーザーＩＤ
     * @return メニューリスト
     */
    @Override
    public List<SysMenuExtend> getMenuListByUserId(Long userId) {
        return sysMenuExtendMapper.getMenuListByUserId(userId);
    }

    /**
     * 再帰処理でツリーを構築
     *
     * @param list 　メニューリスト
     * @return
     */
    private List<SysMenuExtend> buildByRecursive(List<SysMenuExtend> list) {
        List<SysMenuExtend> trees = new ArrayList<>();
        for (SysMenuExtend sysMenuExtend : list) {
            // 一番トップメニュー
            if (sysMenuExtend.getParentId() == 0) {
                trees.add(findChildren(sysMenuExtend, list));
            }
        }
        return trees;
    }

    /**
     * 子供メニューを取得する
     *
     * @param sysMenuExtend 親メニュー
     * @param extendList    システムメニューリスト
     * @return
     */
    private SysMenuExtend findChildren(SysMenuExtend sysMenuExtend, List<SysMenuExtend> extendList) {
        for (SysMenuExtend item : extendList) {
            if (sysMenuExtend.getId().equals(item.getParentId())) {
                if (sysMenuExtend.getList() == null) {
                    sysMenuExtend.setList(new ArrayList<SysMenuExtend>());
                }
                sysMenuExtend.getList().add(findChildren(item, extendList));
            }
        }
        return sysMenuExtend;
    }
}