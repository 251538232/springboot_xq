package jp.co.xq.service.base.common;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Method;
import java.util.List;

/**
 * BaseServiceImpl
 *
 * @author T
 */
public abstract class BaseServiceImpl<Mapper, Record, Example> implements BaseService<Record, Example> {

    @Autowired
    public Mapper mapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        try {
            Method countByExample = mapper.getClass().getDeclaredMethod("deleteByPrimaryKey", id.getClass());
            Object result = countByExample.invoke(mapper, id);
            return Integer.parseInt(String.valueOf(result));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public long countByExample(Example example) {
        try {
            Method countByExample = mapper.getClass().getDeclaredMethod("countByExample", example.getClass());
            Object result = countByExample.invoke(mapper, example);
            return Long.parseLong(String.valueOf(result));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int deleteByExample(Example example) {
        try {

            Method deleteByExample = mapper.getClass().getDeclaredMethod("deleteByExample", example.getClass());
            Object result = deleteByExample.invoke(mapper, example);
            return Integer.parseInt(String.valueOf(result));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public int insert(Record record) {
        try {

            Method insert = mapper.getClass().getDeclaredMethod("insert", record.getClass());
            Object result = insert.invoke(mapper, record);
            return Integer.parseInt(String.valueOf(result));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public int insertSelective(Record record) {
        try {
            Method insertSelective = mapper.getClass().getDeclaredMethod("insertSelective", record.getClass());
            Object result = insertSelective.invoke(mapper, record);
            return Integer.parseInt(String.valueOf(result));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    /**
     * 有効カーラムのみ新規登録
     * 一つのトランザクションで複数登録処理を実行する
     * 【全件同時処理（一つのＳＱＬ文で）よりやや遅いですが、高性能が必要ではない業務で使っても良い】
     *
     * @param recordList データリスト
     * @return
     */
    @Override
    public int insertSelectivePatch(List<Record> recordList) {
        int count = 0;
        for (int i = 0; i < recordList.size(); i++) {
            int result = insertSelective(recordList.get(i));
            count += result;
        }
        return count;
    }

    @Override
    public List<Record> selectByExample(Example example) {
        try {

            Method selectByExample = mapper.getClass().getDeclaredMethod("selectByExample", example.getClass());
            Object result = selectByExample.invoke(mapper, example);
            return (List<Record>) result;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Record> selectByExampleWithBLOBs(Example example) {
        try {
            Method selectByExample = mapper.getClass().getDeclaredMethod("selectByExampleWithBLOBs", example.getClass());
            Object result = selectByExample.invoke(mapper, example);
            return (List<Record>) result;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Record selectByPrimaryKey(Long id) {
        try {
            Method selectByPrimaryKey = mapper.getClass().getDeclaredMethod("selectByPrimaryKey", id.getClass());
            Object result = selectByPrimaryKey.invoke(mapper, id);
            return (Record) result;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Record selectOneByExample(Example example) {
        try {
            Method selectByExample = mapper.getClass().getDeclaredMethod("selectByExample", example.getClass());
            Object result = selectByExample.invoke(mapper, example);
            if (result != null) {
                List<Record> records = (List<Record>) result;
                if (records.size() > 0) {
                    return records.get(0);
                }
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public int updateByExampleSelective(@Param("record") Record record, @Param("example") Example example) {
        try {
            Method updateByExampleSelective = mapper.getClass().getDeclaredMethod("updateByExampleSelective", record.getClass(), example.getClass());
            Object result = updateByExampleSelective.invoke(mapper, record, example);
            return Integer.parseInt(String.valueOf(result));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public int updateByExample(@Param("record") Record record, @Param("example") Example example) {
        try {
            Method updateByExample = mapper.getClass().getDeclaredMethod("updateByExample", record.getClass(), example.getClass());
            Object result = updateByExample.invoke(mapper, record, example);
            return Integer.parseInt(String.valueOf(result));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public int updateByPrimaryKeySelective(Record record) {
        try {
            Method updateByPrimaryKeySelective = mapper.getClass().getDeclaredMethod("updateByPrimaryKeySelective", record.getClass());
            Object result = updateByPrimaryKeySelective.invoke(mapper, record);
            return Integer.parseInt(String.valueOf(result));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public int updateByPrimaryKey(Record record) {
        try {
            Method updateByPrimaryKey = mapper.getClass().getDeclaredMethod("updateByPrimaryKey", record.getClass());
            Object result = updateByPrimaryKey.invoke(mapper, record);
            return Integer.parseInt(String.valueOf(result));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    /**
     * プライベートキーを条件にして有効なカラムデータ更新処理
     * 一つのトランザクションで複数登録処理を実行する
     * 【全件同時処理（一つのＳＱＬ文で）よりやや遅いですが、高性能が必要ではない業務で使っても良い】
     *
     * @param recordList データリスト
     * @return
     */
    @Override
    public int updateByPrimaryKeySelectivePatch(List<Record> recordList) {
        int count = 0;
        for (int i = 0; i < recordList.size(); i++) {
            int result = updateByPrimaryKeySelective(recordList.get(i));
            count += result;
        }
        return count;
    }

    @Override
    public int deleteByPrimaryKeys(String ids) {
        try {
            if (StringUtils.isBlank(ids)) {
                return 0;
            }
            String[] idArray = ids.split(",");
            int count = 0;
            for (String idStr : idArray) {
                if (StringUtils.isBlank(idStr)) {
                    continue;
                }
                Long id = Long.parseLong(idStr);
                Method deleteByPrimaryKey = mapper.getClass().getDeclaredMethod("deleteByPrimaryKey", id.getClass());
                Object result = deleteByPrimaryKey.invoke(mapper, id);
                count += Integer.parseInt(String.valueOf(result));
            }
            return count;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }


    /**
     * データリストをＤＢに追加処理
     *
     * @param list
     * @return
     */
    @Override
    public int batchInsert(List<Record> list) {
        try {
            Method batchInsert = mapper.getClass().getDeclaredMethod("batchInsert", List.class);
            Object result = batchInsert.invoke(mapper, list);
            return Integer.parseInt(String.valueOf(result));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * データリストをＤＢに追加処理
     *
     * @param list
     * @return
     */
    @Override
    public int batchInsertSelective(List<Record> list, Enum... enums) {
        try {
            Method[] methods = mapper.getClass().getDeclaredMethods();
            for (Method method : methods) {
                if (!"batchInsertSelective".equals(method.getName())) {
                    continue;
                }
                Object result = method.invoke(mapper, list, null);
                return Integer.parseInt(String.valueOf(result));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}