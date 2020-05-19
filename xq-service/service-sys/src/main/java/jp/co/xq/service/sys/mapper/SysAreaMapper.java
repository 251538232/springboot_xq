package jp.co.xq.service.sys.mapper;

import java.util.List;
import jp.co.xq.service.sys.model.SysArea;
import jp.co.xq.service.sys.model.SysAreaExample;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SysAreaMapper {
    long countByExample(SysAreaExample example);

    int deleteByExample(SysAreaExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SysArea record);

    int insertSelective(SysArea record);

    List<SysArea> selectByExample(SysAreaExample example);

    SysArea selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SysArea record, @Param("example") SysAreaExample example);

    int updateByExample(@Param("record") SysArea record, @Param("example") SysAreaExample example);

    int updateByPrimaryKeySelective(SysArea record);

    int updateByPrimaryKey(SysArea record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_area
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int batchInsert(@Param("list") List<SysArea> list);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_area
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int batchInsertSelective(@Param("list") List<SysArea> list, @Param("selective") SysArea.Column... selective);
}
