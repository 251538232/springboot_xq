package jp.co.xq.base.common;

import java.util.HashMap;


/**
 * 共通結果
 *
 * @author T
 */
public class BaseResult extends HashMap {

    /**
     * 処理結果コード
     */
    static final String CODE = "code";

    /**
     * メッセージ内容
     */
    static final String MESSAGE = "message";

    /**
     * 戻り値データ
     */
    static final String DATA = "data";

    /**
     * コンストラクタ
     *
     * @param code
     * @param message
     * @param data
     */
    public BaseResult(int code, String message, Object data) {
        put(CODE, code);
        put(MESSAGE, message);
        if (data == null) {
            put(DATA, new HashMap<>());
        } else {
            put(DATA, data);
        }
    }

    /**
     * 正常結果 メッセージ固定で設定
     *
     * @param data
     * @return
     */
    public static BaseResult ok(String message, Object data) {
        return new BaseResult(BaseConstants.RESULT_CODE_OK, message, data);
    }

    /**
     * 正常結果 メッセージ固定で設定
     *
     * @param data
     * @return
     */
    public static BaseResult ok(Object data) {
        return new BaseResult(BaseConstants.RESULT_CODE_OK, "success", data);
    }

    public static BaseResult ok() {
        return ok(null);
    }

    public static BaseResult error(String message) {
        return error(message, null);
    }

    public static BaseResult error(String message, Object data) {
        return new BaseResult(BaseConstants.RESULT_CODE_ERROR, message, data);
    }

    public static BaseResult error(int code, String message, Object data) {
        return new BaseResult(code, message, data);
    }
}
