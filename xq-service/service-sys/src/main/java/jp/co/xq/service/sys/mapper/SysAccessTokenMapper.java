package jp.co.xq.service.sys.mapper;

import java.util.List;
import jp.co.xq.service.sys.model.SysAccessToken;
import jp.co.xq.service.sys.model.SysAccessTokenExample;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SysAccessTokenMapper {
    long countByExample(SysAccessTokenExample example);

    int deleteByExample(SysAccessTokenExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SysAccessToken record);

    int insertSelective(SysAccessToken record);

    List<SysAccessToken> selectByExample(SysAccessTokenExample example);

    SysAccessToken selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SysAccessToken record, @Param("example") SysAccessTokenExample example);

    int updateByExample(@Param("record") SysAccessToken record, @Param("example") SysAccessTokenExample example);

    int updateByPrimaryKeySelective(SysAccessToken record);

    int updateByPrimaryKey(SysAccessToken record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_access_token
     *
     * @mbg.generated
            * @project https://github.com/itfsw/mybatis-generator-plugin
            */
            int batchInsert(@Param("list") List<SysAccessToken> list);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_access_token
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int batchInsertSelective(@Param("list") List<SysAccessToken> list, @Param("selective") SysAccessToken.Column... selective);
}
