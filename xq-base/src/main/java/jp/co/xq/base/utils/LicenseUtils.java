package jp.co.xq.base.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.Cipher;
import java.io.*;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class LicenseUtils {

    public static final String KEY_ALGORITHM = "RSA";
    public static final String CIPHER_ALGORITHM = "RSA/ECB/PKCS1Padding";
    public static final String PUBLIC_KEY_FILE = "public_key.pom";
    public static final String PRIVATE_KEY_FILE = "private_key.pom";
    /**
     * RSA keys size
     */
    public static final int KEY_SIZE = 1024;
    public static final String PLAIN_TEXT = "2018-08-22";

    public static void main(String[] args) throws Exception {
        generateKeyBytes();

        // encodeBase64String
        PublicKey publicKey = restorePublicKey(Hex.decodeHex(readFile(PUBLIC_KEY_FILE).toCharArray()));
        System.out.println("publicKey: " + publicKey);

        byte[] encodedText = RSAEncode(publicKey, PLAIN_TEXT.getBytes());
        writeFile("license", Base64.encodeBase64String(encodedText));

        // RSADecode
        char[] keyChars = readFile(PRIVATE_KEY_FILE).toCharArray();
        PrivateKey privateKey = restorePrivateKey(Hex.decodeHex(keyChars));
        System.out.println(System.currentTimeMillis());
        String licenseTx = readFile("license");
        System.out.println(RSADecode(privateKey, Base64.decodeBase64(licenseTx)));
        System.out.println(System.currentTimeMillis());
    }

    private static void writeFile(String fileName, String content) {
        try {
            File file = new File(fileName);
            OutputStream outputStream = new FileOutputStream(file);
            outputStream.write(content.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String readFile(String fileName) {
        try {
            InputStream inputStream = new FileInputStream(fileName);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String readd = "";
            StringBuffer stringBuffer = new StringBuffer();
            while ((readd = bufferedReader.readLine()) != null) {
                stringBuffer.append(readd);
            }
            inputStream.close();
            return stringBuffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * generateKeyBytes
     *
     * @return
     */
    public static void generateKeyBytes() {

        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORITHM);
            keyPairGenerator.initialize(KEY_SIZE);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
            writeFile(PUBLIC_KEY_FILE, Hex.encodeHexString(publicKey.getEncoded()));
            writeFile(PRIVATE_KEY_FILE, Hex.encodeHexString(privateKey.getEncoded()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * restorePublicKey
     *
     * @param keyBytes
     * @return
     */
    public static PublicKey restorePublicKey(byte[] keyBytes) {
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);

        try {
            KeyFactory factory = KeyFactory.getInstance(KEY_ALGORITHM);
            PublicKey publicKey = factory.generatePublic(x509EncodedKeySpec);
            return publicKey;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * restorePrivateKey
     *
     * @param keyBytes
     * @return
     */
    public static PrivateKey restorePrivateKey(byte[] keyBytes) {
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(
                keyBytes);
        try {
            KeyFactory factory = KeyFactory.getInstance(KEY_ALGORITHM);
            PrivateKey privateKey = factory.generatePrivate(pkcs8EncodedKeySpec);
            return privateKey;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Encode
     *
     * @param key
     * @param plainText
     * @return
     */
    public static byte[] RSAEncode(PublicKey key, byte[] plainText) {

        try {
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return cipher.doFinal(plainText);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * Decode
     *
     * @param key
     * @param encodedText
     * @return
     */
    public static String RSADecode(PrivateKey key, byte[] encodedText) {

        try {
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key);
            return new String(cipher.doFinal(encodedText));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }
}
