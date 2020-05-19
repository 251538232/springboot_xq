package jp.co.xq.service.sys.service;

import jp.co.xq.service.base.common.BaseService;
import jp.co.xq.service.sys.model.SysMenu;
import jp.co.xq.service.sys.model.SysMenuExample;
import jp.co.xq.service.sys.model.extend.SysMenuExtend;

import java.util.List;

/**
 * SysMenuService interface
 *
 * @author tian w 2018/6/28.
 */
public interface SysMenuService extends BaseService<SysMenu, SysMenuExample> {
    /**
     * ユーザーのメニューリストツリー式データ作成
     *
     * @param userId システムユーザーＩＤ
     * @return メニューリストツリー式リスト
     */
    List<SysMenuExtend> treeMenuListByUserId(Long userId);

    /**
     * ユーザーのメニューリストを取得する
     *
     * @param userId システムユーザーＩＤ
     * @return メニューリストを取得する
     */
    List<SysMenuExtend> getMenuListByUserId(Long userId);

}