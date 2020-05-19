package jp.co.xq.service.sys.mapper.extend;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * システムユーザー拡張処理Mapper
 * 自動生成部分と分ける
 *
 * @author t
 */
@Mapper
public interface SysUserExtendMapper {

    /**
     * ユーザーＩＤからユーザー権限を取得する
     *
     * @param userId システムユーザーＩＤ
     * @return 権限文字列リスト
     */
    List<String> getPermissionsByUserId(Long userId);

}
