package jp.co.xq.base.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 文字列とリスト変換ツール
 *
 * @author t
 */
public class StringListUtil {

    /**
     * カンマ区切り文字列をListに変換
     *
     * @param strs カンマ区切り文字列をListに変換
     * @param <W>
     * @return
     */
    public static <W> List<W> toList(String strs) {
        if (strs == null) {
            return new ArrayList<W>();
        }
        List<W> result = new ArrayList<>();
        String[] strAry = strs.split(",");
        for (int i = 0; i < strAry.length; i++) {
            result.add((W) strAry[i]);
        }
        return result;
    }
}
