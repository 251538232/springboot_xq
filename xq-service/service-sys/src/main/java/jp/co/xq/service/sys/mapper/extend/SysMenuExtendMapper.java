package jp.co.xq.service.sys.mapper.extend;

import jp.co.xq.service.sys.model.extend.SysMenuExtend;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysMenuExtendMapper {
    /**
     * ユーザーのメニューを取得する
     *
     * @param userId システムユーザーＩＤ
     * @return メニューのＩＤを取得する
     */
    List<Long> getMenuIdsByUserId(Long userId);

    /**
     * ユーザーのメニューリストを取得する
     *
     * @param userId システムユーザーＩＤ
     * @return メニューのＩＤを取得する
     */
    List<SysMenuExtend> getMenuListByUserId(@Param("userId") Long userId);
}
