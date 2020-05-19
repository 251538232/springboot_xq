package jp.co.xq.service.base.common;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * BaseServiceインターフェース
 *
 * @author T
 */
public interface BaseService<Record, Example> {

    /**
     * Exampleを条件として、カウント処理を行う
     *
     * @param example
     * @return
     */
    long countByExample(Example example);

    /**
     * Exampleを条件として、データ削除を行う
     *
     * @param example
     * @return
     */
    int deleteByExample(Example example);

    /**
     * idをベースにして、データを削除する
     *
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Long id);

    /**
     * Example条件をベースにしてデータリスト取得処理
     *
     * @param example
     * @return
     */
    List<Record> selectByExample(Example example);

    /**
     * Example条件をベースにしてデータリスト取得処理
     *
     * @param example
     * @return
     */
    List<Record> selectByExampleWithBLOBs(Example example);

    /**
     * 条件をベースにしてデータ取得処理
     *
     * @param id
     * @return
     */
    Record selectByPrimaryKey(Long id);

    /**
     * 条件をベースにしてデータ取得処理
     *
     * @param example
     * @return
     */
    Record selectOneByExample(Example example);

    /**
     * Exampleを条件としてデータ更新処理
     *
     * @param record
     * @param example
     * @return
     */
    int updateByExampleSelective(@Param("record") Record record, @Param("example") Example example);

    /**
     * Exampleを条件にしてデータ更新処理
     *
     * @param record
     * @param example
     * @return
     */
    int updateByExample(@Param("record") Record record, @Param("example") Example example);

    /**
     * プライベートキーを条件にしてデータ更新処理
     *
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(Record record);

    /**
     * プライベートキーを条件にして有効なカラムデータ更新処理
     *
     * @param recordList データリスト
     * @return
     */
    int updateByPrimaryKeySelectivePatch(List<Record> recordList);

    /**
     * データ登録処理
     *
     * @param record
     * @return
     */
    int insert(Record record);

    /**
     * 有効カーラムのみ新規登録
     *
     * @param record
     * @return
     */
    int insertSelective(Record record);

    /**
     * 有効カーラムのみ新規登録
     *
     * @param recordList データリスト
     * @return
     */
    int insertSelectivePatch(List<Record> recordList);

    /**
     * プライベートキーをベースにしてデータ更新する
     *
     * @param record
     * @return
     */
    int updateByPrimaryKey(Record record);

    /**
     * プライベートキーをベースにしてデータ削除する
     *
     * @param ids 複数IDの場合コンマ区切り
     * @return
     */
    int deleteByPrimaryKeys(String ids);

    /**
     * データリストをＤＢに追加処理
     *
     * @param list
     * @return
     */
    int batchInsert(List<Record> list);

    /**
     * データリストをＤＢに追加処理
     *
     * @param list
     * @return
     */
    int batchInsertSelective(List<Record> list, Enum... enums);
}