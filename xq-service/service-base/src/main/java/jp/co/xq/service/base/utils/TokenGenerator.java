package jp.co.xq.service.base.utils;

import java.util.UUID;

/**
 * 認証Token作成処理
 *
 * @author t
 */
public class TokenGenerator {

    /**
     * HEX code 16
     */
    private static final char[] HEX_CODE = "0123456789abcdef".toCharArray();

    /**
     * 認証Token生成処理
     *
     * @return
     */
    public static String generate() {
        String uuid = UUID.randomUUID().toString();
        System.out.println(uuid);
        return uuid;
        // 認証Token暗号化する
//        try {
//            MessageDigest algorithm = MessageDigest.getInstance("MD5");
//            algorithm.reset();
//            algorithm.update(uuid.getBytes());
//
//            byte[] digest = algorithm.digest();
//            if (digest == null) {
//                return null;
//            }
//            StringBuilder stringBuilder = new StringBuilder(digest.length * 2);
//            for (byte b : digest) {
//                stringBuilder.append(HEX_CODE[(b >> 4) & 0xF]);
//                stringBuilder.append(HEX_CODE[(b & 0xF)]);
//            }
//
//            return stringBuilder.toString();
//        } catch (Exception e) {
//            throw new AppException("認証Token作成失敗", e);
//        }
    }
}
