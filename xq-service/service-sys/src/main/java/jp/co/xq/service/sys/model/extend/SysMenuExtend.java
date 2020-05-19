package jp.co.xq.service.sys.model.extend;

import jp.co.xq.service.sys.model.SysMenu;

import java.util.List;

/**
 * システムメニュー親子関係Model
 *
 * @author tian w
 */
public class SysMenuExtend extends SysMenu {

    /**
     * 子メニューリスト
     */
    private List list;

    /**
     * 親メニュー名称
     */
    private List parentName;

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }

    public List getParentName() {
        return parentName;
    }

    public void setParentName(List parentName) {
        this.parentName = parentName;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("SysMenuExtend{");
        sb.append("list=").append(list);
        sb.append(", parentName=").append(parentName);
        sb.append('}');
        return sb.toString();
    }
}