package jp.co.xq.service.sys.model.extend;

import jp.co.xq.service.sys.model.SysUser;

import java.util.List;

public class SysUserExtend extends SysUser {

    /**
     * ユーザーRoleIdリスト
     */
    private List<Long> roleIdList;

    public List<Long> getRoleIdList() {
        return roleIdList;
    }

    public void setRoleIdList(List<Long> roleIdList) {
        this.roleIdList = roleIdList;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("SysUserExtend{");
        sb.append("roleIdList=").append(roleIdList);
        sb.append('}');
        return sb.toString();
    }
}