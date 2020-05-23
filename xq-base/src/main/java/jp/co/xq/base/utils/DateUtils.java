package jp.co.xq.base.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期处理
 */
public class DateUtils {

    /**
     * 時間フォーマット(yyyy-MM-dd)
     */
    public final static String YYYY_MM_DD = "yyyy-MM-dd";

    /**
     * 時間フォーマット(yyyy-MM-dd HH:mm:ss)
     */
    public final static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    /**
     * 時間フォーマット(yyyyMMddHHmmss)
     */
    public final static String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    /**
     * 時間フォーマット(yyyy-MM-dd 00:00:00)
     */
    public final static String YYYY_MM_DD_00_00_00 = "yyyy-MM-dd 00:00:00";

    public static String formatYMD000(Date date) {
        return format(date, YYYY_MM_DD_00_00_00);
    }

    public static String formatYMD(Date date) {
        return format(date, YYYY_MM_DD);
    }

    public static String formatYMDHMS(Date date) {
        return format(date, YYYY_MM_DD_HH_MM_SS);
    }

    public static String formatYMDHMSTrim(Date date) {
        return format(date, YYYYMMDDHHMMSS);
    }

    public static String format(Date date, String pattern) {
        if (date != null) {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            return df.format(date);
        }
        return null;
    }

    /**
     * 当日0時0分0秒のDate情報を取得
     *
     * @return 当日0時0分0秒
     */
    public static Date parse(String dateStr, String pattern) {
        try {
            if (dateStr != null) {
                SimpleDateFormat df = new SimpleDateFormat(pattern);
                return df.parse(dateStr);
            }
        } catch (Exception e) {
            e.printStackTrace();
    }
        return null;
    }

    /**
     * 当日0時0分0秒のDate情報を取得
     *
     * @return 当日0時0分0秒
     */
    public static Date nowYmd() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

  static Integer[] integers = new Integer[] { 0x4bd8, 0x4ae0, 0xa570, 0x54d5,
      0xd260, 0xd950, 0x5554, 0x56af, 0x9ad0, 0x55d2, 0x4ae0, 0xa5b6, 0xa4d0,
      0xd250, 0xd255, 0xb54f, 0xd6a0, 0xada2, 0x95b0, 0x4977, 0x497f, 0xa4b0,
      0xb4b5, 0x6a50, 0x6d40, 0xab54, 0x2b6f, 0x9570, 0x52f2, 0x4970, 0x6566,
      0xd4a0, 0xea50, 0x6a95, 0x5adf, 0x2b60, 0x86e3, 0x92ef, 0xc8d7, 0xc95f,
      0xd4a0, 0xd8a6, 0xb55f, 0x56a0, 0xa5b4, 0x25df, 0x92d0, 0xd2b2, 0xa950,
      0xb557, 0x6ca0, 0xb550, 0x5355, 0x4daf, 0xa5b0, 0x4573, 0x52bf, 0xa9a8,
      0xe950, 0x6aa0, 0xaea6, 0xab50, 0x4b60, 0xaae4, 0xa570, 0x5260, 0xf263,
      0xd950, 0x5b57, 0x56a0, 0x96d0, 0x4dd5, 0x4ad0, 0xa4d0, 0xd4d4, 0xd250,
      0xd558, 0xb540, 0xb6a0, 0x95a6, 0x95bf, 0x49b0, 0xa974, 0xa4b0, 0xb27a,
      0x6a50, 0x6d40, 0xaf46, 0xab60, 0x9570, 0x4af5, 0x4970, 0x64b0, 0x74a3,
      0xea50, 0x6b58, 0x5ac0, 0xab60, 0x96d5, 0x92e0, 0xc960, 0xd954, 0xd4a0,
      0xda50, 0x7552, 0x56a0, 0xabb7, 0x25d0, 0x92d0, 0xcab5, 0xa950, 0xb4a0,
      0xbaa4, 0xad50, 0x55d9, 0x4ba0, 0xa5b0, 0x5176, 0x52bf, 0xa930, 0x7954,
      0x6aa0, 0xad50, 0x5b52, 0x4b60, 0xa6e6, 0xa4e0, 0xd260, 0xea65, 0xd530,
      0x5aa0, 0x76a3, 0x96d0, 0x4afb, 0x4ad0, 0xa4d0, 0xd0b6, 0xd25f, 0xd520,
      0xdd45, 0xb5a0, 0x56d0, 0x55b2, 0x49b0, 0xa577, 0xa4b0, 0xaa50, 0xb255,
      0x6d2f, 0xada0, 0x4b63, 0x937f, 0x49f8, 0x4970, 0x64b0, 0x68a6, 0xea5f,
      0x6b20, 0xa6c4, 0xaaef, 0x92e0, 0xd2e3, 0xc960, 0xd557, 0xd4a0, 0xda50,
      0x5d55, 0x56a0, 0xa6d0, 0x55d4, 0x52d0, 0xa9b8, 0xa950, 0xb4a0, 0xb6a6,
      0xad50, 0x55a0, 0xaba4, 0xa5b0, 0x52b0, 0xb273, 0x6930, 0x7337, 0x6aa0,
      0xad50, 0x4b55, 0x4b6f, 0xa570, 0x54e4, 0xd260, 0xe968, 0xd520, 0xdaa0,
      0x6aa6, 0x56df, 0x4ae0, 0xa9d4, 0xa4d0, 0xd150, 0xf252, 0xd520 };

  public static void main(String[] args) {
    for (int i = 0; i < integers.length; i++) {
      System.out.print(" " + integers[i]);
    }
  }
}