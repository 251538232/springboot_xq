package jp.co.xq.utils;

import org.apache.commons.codec.binary.Hex;
import org.springframework.util.Base64Utils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
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
//    generateKeyBytes();

        // encodeBase64String
        PublicKey publicKey = restorePublicKey(Hex.decodeHex(readFile(PUBLIC_KEY_FILE).toCharArray()));
//    System.out.println("publicKey: " + publicKey);

//    byte[] encodedText = RSAEncode(publicKey, PLAIN_TEXT.getBytes());
//    writeFile("license", Base64.encodeBase64String(encodedText));

        // RSADecode
        char[] keyChars = readFile(PRIVATE_KEY_FILE).toCharArray();
        PrivateKey privateKey = restorePrivateKey(Hex.decodeHex(keyChars));
//    System.out.println(System.currentTimeMillis());
        String licenseTx = readFile("license");
//    System.out.println(RSADecode(privateKey, Base64.decodeBase64(licenseTx)));


        String text = "作者：知乎用户\n" +
                "来源：知乎\n" +
                "著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。\n" +
                "\n" +
                "没看过电视剧， 直接听的歌， 或许感受会有不同；\n" +
                "不想单谈一句， 看整首的歌词吧红豆生南国, \n" +
                "是很遥远的事情. \n" +
                "相思算什么, \n" +
                "早无人在意. \n" +
                "醉卧不夜城, \n" +
                "处处霓虹. \n" +
                "酒杯中好一片滥滥风情. \n" +
                "最肯忘却古人诗, \n" +
                "最不屑一顾是相思. \n" +
                "守着爱怕人笑, \n" +
                "还怕人看清. \n" +
                "春又来看红豆开, \n" +
                "竟不见有情人去采, \n" +
                "烟花拥着风流真情不在. 很短， 就这么几句。红豆生南国, \n" +
                "是很遥远的事情.首先开篇即否定“红豆生南国”的说法， 采用的语气并不强硬， 让人感到的不是真的否定， 而是体现出一种对这种说法快要丧失信心的感觉. 后一句:\n" +
                "相思算什么， \n" +
                "早无人在意；这大概是自嘲吧， 我虽相思， 却无人在意（我的）相思（指相思的那个人吧）， 不仅如此， 环视四周， 还有人如我这般么？ 原来大家都是不在意（相思这回事）的呀.\n" +
                "写的看似是对无人在意相思的感慨， 然而若不是正在饱受相思之苦的人， 又会注意到这些么？醉卧不夜城, \n" +
                "处处霓虹. \n" +
                "酒杯中好一片滥滥风情. \n" +
                "这句是写思而不得， 不被理解， 独守苦闷之后的失望， 自甘堕落，放纵. 不多解释了， 很多人都有过类似的感觉吧. 既然不能得到， 就失去些什么好了. 看这句感叹中， “滥滥风情” 显然是贬， 这里看出， 对那些“醉卧不夜城”的讽刺： 原来你们所谓的爱， 不过如此， 呵呵.\n" +
                "或许还包含着些许气愤吧。最肯忘却古人诗, \n" +
                "最不屑一顾是相思. \n" +
                "这句写法同前， 但是有多种理解。 我的理解是这样的： （我希望自己）也忘却那些古人诗， 和别人一样， 做到对相思不屑一顾；\n" +
                "我觉得这是到了整首歌的感情的至高点。 前面对相思的极度失望一直是在控制中流露的， 到了这里， 终于忍不住要宣泄一下。 正是因为无人理解， 极度失望， 悲伤中带着愤懑， 说出了这样的话。 就像很多人爱而不得， 屡为情伤。 会说出（或想）： 算了， 累了， 不再爱了， 不如多心疼一下自己吧。      这样想的时候其实并不甘心， 只是因为看不到任何希望之后的失落罢了. 之后呢？ 还不是继续受伤？ 因为要做到“最不屑一顾是相思”， 对于\"我”这种人来说，太难了吧。守着爱怕人笑, \n" +
                "还怕人看清. \n" +
                "这句直接写出自己苦闷的原因： 怕人笑， 怕被看清， 所以不可能被别人理解； 独自守护这样一份深藏在黑夜中的爱， 若结局不圆满， 又怎能不是大写的绝望？春又来看红豆开, \n" +
                "竟不见有情人去采, \n" +
                "烟花拥着风流真情不在. \n" +
                "这句是对前面的一个回应， 前面说到相思无人在意， 这里得到事实的验证；这一事实再次让“我”失望， 并有暗指世上罕有有情人之意， 凸显自己因格格不入而不被理解（事实上， 怕人看清， 又希望被理解， 这也是”我“心里的一个矛盾吧）\n" +
                "最后一句， 承接上文”酒杯中好一片滥滥风情. “ 烟花风流之间， 怕是早已失去了那份真情吧。\n" +
                "然这里可以看出”我“对于 真情的珍惜， 与其他人形成对比。 而细细体会， 真的”真情不在“吗？\n" +
                "我自己的体会更倾向于还是相思不得极度失望中因满腹哀怨说出的气话.   试问如若真的没有真情， 又如何能抒发出这些真真切切的因受相思之苦而生的情感呢？抱歉说的不够简练， 但我觉得只看那一句谈显然是不够的.一直很喜欢这首歌，单曲循环了两个小时之后， 才写下了上面那些.-------------------------------------2017.06.19 补充------------------------------------------本来没必要补充的， 也不知道当时写答案的时候为什么就忘记一个一直很想说的地方了。就是这个：     “最肯忘却古人诗”  ——>  \"最肯忘却故人事\"。当时空耳听出来的， 我还记得发现这个的时候还激动了好久~  啧啧称赞呀。也不知是作词的人，是有意，还是无意为之吧。 总之两种读法的意境， 我都很喜欢。        听到的明明是相同的读音， 却因不同经历， 不同心境， 脑海中浮现的词不一样， 大概也算是件很有意思的事吧， 意象由心生。";
        text = text + text + text + text;
        long s = System.currentTimeMillis();
        System.out.println(s);
        String encipher = encipher(text, publicKey);
        System.out.println("加密：" + (System.currentTimeMillis() - s));

        s = System.currentTimeMillis();
        String decipher = decipher(encipher, privateKey);
        System.err.println(decipher);

        System.out.println(text.equals(decipher));
        System.out.println("解密：" + (System.currentTimeMillis() - s));
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
     * 使用工钥加密
     *
     * @param content         待加密内容
     * @param publicKeyBase64 公钥 base64 编码
     * @return 经过 base64 编码后的字符串
     */
    public static String encipher(String content, Key publicKeyBase64) {
        return encipher(content, publicKeyBase64, KEY_SIZE / 8 - 11);
    }

    /**
     * 分段加密
     *
     * @param ciphertext  密文
     * @param key         加密秘钥
     * @param segmentSize 分段大小，<=0 不分段
     * @return
     */
    public static String encipher(String ciphertext, Key key, int segmentSize) {
        try { // 用公钥加密
            // Cipher负责完成加密或解密工作，基于RSA
            byte[] srcBytes = ciphertext.getBytes();
            // 根据公钥，对Cipher对象进行初始化
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] resultBytes = null;
            //分段加密
            if (segmentSize > 0) {
                resultBytes = cipherDoFinal(cipher, srcBytes, segmentSize);
            } else {
                resultBytes = cipher.doFinal(srcBytes);
            }
            String base64Str = Base64Utils.encodeToString(resultBytes);
            return base64Str;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 分段大小
     *
     * @param cipher
     * @param srcBytes
     * @param segmentSize
     * @return
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws IOException
     */
    public static byte[] cipherDoFinal(Cipher cipher, byte[] srcBytes, int segmentSize) throws
            IllegalBlockSizeException, BadPaddingException, IOException {
        if (segmentSize <= 0) {
            throw new RuntimeException("分段大小必须大于0");
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int inputLen = srcBytes.length;
        int offSet = 0;
        byte[] cache;
        // 对数据分段解密
        int i = 0;
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > segmentSize) {
                cache = cipher.doFinal(srcBytes, offSet, segmentSize);
            } else {
                cache = cipher.doFinal(srcBytes, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * segmentSize;
        }
        byte[] data = out.toByteArray();
        out.close();
        return data;
    }

    /**
     * 使用私钥解密
     *
     * @param contentBase64    待加密内容, base64 编码
     * @param privateKeyBase64 私钥 base64 编码
     * @return
     * @segmentSize 分段大小
     */
    public static String decipher(String contentBase64, Key privateKeyBase64) {
        return decipher(contentBase64, privateKeyBase64, KEY_SIZE / 8);
    }

    /**
     * 分段解密
     *
     * @param contentBase64 密文
     * @param key           解密秘钥
     * @param segmentSize   分段大小（小于等于0不分段）
     * @return
     */
    public static String decipher(String contentBase64, Key key, int segmentSize) {
        try { // 用私钥解密
            byte[] srcBytes = Base64Utils.decodeFromString(contentBase64);
            // Cipher负责完成加密或解密工作，基于RSA
            Cipher deCipher = Cipher.getInstance("RSA");
            // 根据公钥，对Cipher对象进行初始化
            deCipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decBytes = null;
            //分段加密
            if (segmentSize > 0) {
                decBytes = cipherDoFinal(deCipher, srcBytes, segmentSize);
            } else {
                decBytes = deCipher.doFinal(srcBytes);
            }
            String decrytStr = new String(decBytes);
            return decrytStr;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
