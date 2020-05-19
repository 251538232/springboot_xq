package jp.co.xq.base.common;

import java.io.File;

/**
 * コンスタント定数定義
 *
 * @author T
 */
public class BaseConstants {

    /**
     * システムメニュータイプ
     */
    public enum MenuType {

        CATALOG(0),

        MENU(1),

        BUTTON(2);

        private int value;

        MenuType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public static final String LOCAL_CHINA = "zh_CN";
    public static final String LOCAL_ENGLISH = "en_US";
    public static final String LOCAL_JAPAN = "ja_JP";

    public static final String EXCEPTION_MESSAGE = "システム異常が発生しました。";

    public static final String EXCEPTION_MESSAGE_DUPLICATE = "一意制約エラー発生しました。";

    /**
     * 正常コード
     */
    public static final int RESULT_CODE_OK = 0;

    /**
     * 異常コード
     */
    public static final int RESULT_CODE_ERROR = 500;


    public static final String HTTP_HEADER_ACCESS_TOKEN = "access_token";
    public static final String HTTP_HEADER_ORIGIN = "Origin";

    /**
     * ユーザー状態 0:ブロック状態 1:正常 2:制限
     */
    public static final int SYS_USER_STATUS_LOCK = 0;
    public static final int SYS_USER_STATUS_OK = 1;
    public static final int SYS_USER_STATUS_LIMIT = 2;

    /**
     * ACCESS_TOKEN 有効期限(7days) 単位(ms)
     */
    public static final int ACCESS_EXPIRE_TIME = 7 * 24 * 3600 * 1000;

    /**
     * time punch type 出勤/退勤
     */
    public static final String PUNCH_TIME_TYPE_START = "start";
    public static final String PUNCH_TIME_TYPE_END = "end";


    /**
     * システムメニュータイプ
     */

    public static final Integer SYSMENU_TYPE_CATEGORY = 0;
    public static final Integer SYSMENU_TYPE_MENU = 1;
    public static final Integer SYSMENU_TYPE_BUTTON = 2;

    /**
     * 承認状態
     * 0：承認まち　1:承認済み 2:差し戻し 3:キャンセル
     */
    public static final Integer APPLY_STATUS_WAIT = 0;
    public static final Integer APPLY_STATUS_ADMITED = 1;
    public static final Integer APPLY_STATUS_REMAND = 2;
    public static final Integer APPLY_STATUS_CANCEL = 3;

    /**
     * RedisKey
     */
    public static final String REDIS_KEY_EMPLOYEES_PERSONAL = "employees_personal:";

    /**
     * get
     * 給料ファイルのデフォルトフォルダ
     */
    public static String SALARY_FILES_ROOT = "C:\\xq\\給料";

    /**
     * 給料ファイルの更新時間を記録するファイル
     */
    public static final String SALARYFILESINFO = "salaryfilesinfo.properties";

    public static final String APP_ROOT = System.getProperty("user.dir");

    public static final String APP_TEMP = APP_ROOT + File.separator + "temp";

    public static final String DOT = ".";
    /**
     * 平均労働日数
     */
    public static final double WORK_DAYS_PER_MONTH = 21.5;

    public static final int SALARY_FILE_KEEP_MONTHS = 3;

}
