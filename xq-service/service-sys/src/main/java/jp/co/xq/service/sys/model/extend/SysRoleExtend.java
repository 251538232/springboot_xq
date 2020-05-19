package jp.co.xq.service.sys.model.extend;

import jp.co.xq.service.sys.model.SysRole;

import java.util.List;

/**
 * 役柄に拡張Model
 *
 * @author tian w
 */
public class SysRoleExtend extends SysRole {

    /**
     * 役柄のメニューリスト
     */
    private List<Long> menuIdList;


    public List<Long> getMenuIdList() {
        return menuIdList;
    }

    public void setMenuIdList(List<Long> menuIdList) {
        this.menuIdList = menuIdList;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("SysRoleExtend{");
        sb.append("menuIdList=").append(menuIdList);
        sb.append('}');
        return sb.toString();
    }
}