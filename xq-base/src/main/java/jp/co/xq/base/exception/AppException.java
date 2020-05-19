package jp.co.xq.base.exception;


import jp.co.xq.base.common.BaseConstants;

/**
 * アプリケーション異常処理
 *
 * @author T
 */
public class AppException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private String message;
    private int code = BaseConstants.RESULT_CODE_ERROR;

    public AppException(String message) {
        super(message);
        this.message = message;
    }

    public AppException(String message, Throwable e) {
        super(message, e);
        this.message = message;
    }

    public AppException(String message, int code, Throwable e) {
        super(message, e);
        this.message = message;
        this.code = code;
    }

    public String getmessage() {
        return message;
    }

    public void setmessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }


}
