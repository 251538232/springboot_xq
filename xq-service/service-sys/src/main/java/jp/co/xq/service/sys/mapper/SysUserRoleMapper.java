package jp.co.xq.service.sys.mapper;

import java.util.List;
import jp.co.xq.service.sys.model.SysUserRole;
import jp.co.xq.service.sys.model.SysUserRoleExample;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SysUserRoleMapper {
    long countByExample(SysUserRoleExample example);

    int deleteByExample(SysUserRoleExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SysUserRole record);

    int insertSelective(SysUserRole record);

    List<SysUserRole> selectByExample(SysUserRoleExample example);

    SysUserRole selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SysUserRole record, @Param("example") SysUserRoleExample example);

    int updateByExample(@Param("record") SysUserRole record, @Param("example") SysUserRoleExample example);

    int updateByPrimaryKeySelective(SysUserRole record);

    int updateByPrimaryKey(SysUserRole record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user_role
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int batchInsert(@Param("list") List<SysUserRole> list);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user_role
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int batchInsertSelective(@Param("list") List<SysUserRole> list, @Param("selective") SysUserRole.Column... selective);
}
