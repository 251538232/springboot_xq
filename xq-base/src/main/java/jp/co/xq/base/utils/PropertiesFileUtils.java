package jp.co.xq.base.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Properties読み込み処理
 *
 * @author T
 */
public class PropertiesFileUtils {

    // 複数のProperties同時に読み込みする際、キャッシュバックする
    private static HashMap<String, PropertiesFileUtils> configMap = new HashMap<String, PropertiesFileUtils>();
    // ファイルオープン時間のTimeout
    private Date loadTime = null;
    private ResourceBundle resourceBundle = null;

    // ディフォルトファイル名
    private static final String NAME = "config";
    private static final Integer TIME_OUT = 60 * 1000;

    // Singleton
    private PropertiesFileUtils(String name) {
        this.loadTime = new Date();
        this.resourceBundle = ResourceBundle.getBundle(name);
    }

    public static synchronized PropertiesFileUtils getInstance() {
        return getInstance(NAME);
    }

    public static synchronized PropertiesFileUtils getInstance(String name) {
        PropertiesFileUtils conf = configMap.get(name);
        if (null == conf) {
            conf = new PropertiesFileUtils(name);
            configMap.put(name, conf);
        }
        // 1分超えたかどうかの判断
        if ((System.currentTimeMillis() - conf.getLoadTime().getTime()) > TIME_OUT) {
            conf = new PropertiesFileUtils(name);
            configMap.put(name, conf);
        }
        return conf;
    }

    /**
     * Keyをベースにして値を取得する
     *
     * @param key key
     * @return Keyに当たる値 文字列
     */
    public String getString(String key) {
        try {
            return resourceBundle.getString(key);
        } catch (MissingResourceException e) {
            e.printStackTrace();
        }
        return "";
    }


    /**
     * Keyをベースにして値を取得する
     *
     * @param key key
     * @return Keyに当たる値 Integer
     */
    public Integer getInt(String key) {
        try {
            String value = resourceBundle.getString(key);
            return Integer.parseInt(value);
        } catch (MissingResourceException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Keyをベースにして値を取得する
     *
     * @param key key
     * @return Keyに当たる値 Boolean
     */
    public boolean getBool(String key) {
        try {
            String value = resourceBundle.getString(key);
            if ("true".equals(value)) {
                return true;
            }
        } catch (MissingResourceException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Date getLoadTime() {
        return loadTime;
    }

}
