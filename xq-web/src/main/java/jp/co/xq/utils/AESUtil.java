package jp.co.xq.utils;


import org.springframework.util.Base64Utils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @version V1.0
 * @desc AES 加密工具类
 */
public class AESUtil {

  private static final String KEY_ALGORITHM = "AES";

  /**
   * 默认的加密算法
   */
  private static final String DEFAULT_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";

  public static void main(String[] args) {
    String text = "作者：知乎用户\n" +
        "链接：https://www.zhihu.com/question/28002471/answer/95806661\n" +
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

    String encrypt = encrypt(text, "132465");
    System.out.println(encrypt);
    System.out.println(decrypt(encrypt, "132465"));
    System.out.println(decrypt("admin", "admin"));
  }

  /**
   * AES 加密操作
   *
   * @param content  待加密内容
   * @param password 加密密码
   * @return 返回Base64转码后的加密数据
   */
  public static String encrypt(String content, String password) {
    try {
      Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
      // 创建密码器
      byte[] byteContent = content.getBytes("utf-8");
      cipher.init(Cipher.ENCRYPT_MODE, getSecretKey(password));
      // 初始化为加密模式的密码器
      byte[] result = cipher.doFinal(byteContent);
      // 加密
      return Base64Utils.encodeToString(result);
      // 通过Base64转码返回
    } catch (Exception ex) {
      Logger.getLogger(AESUtil.class.getName()).log(Level.SEVERE, null, ex);
    }
    return null;
  }

  /**
   * AES 解密操作
   *
   * @param content
   * @param password
   * @return
   */
  public static String decrypt(String content, String password) {
    try {
      // 实例化
      Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
      // 使用密钥初始化，设置为解密模式
      cipher.init(Cipher.DECRYPT_MODE, getSecretKey(password));
      // 执行操作
      byte[] result = cipher.doFinal(Base64Utils.decodeFromString(content));
      return new String(result, "utf-8");
    } catch (Exception ex) {
      Logger.getLogger(AESUtil.class.getName()).log(Level.SEVERE, null, ex);
    }
    return null;
  }

  /**
   * 生成加密秘钥
   *
   * @return
   */
  private static SecretKeySpec getSecretKey(String password) {
    // 返回生成指定算法密钥生成器的 KeyGenerator 对象
    KeyGenerator kg = null;
    try {
      kg = KeyGenerator.getInstance(KEY_ALGORITHM);
      // AES 要求密钥长度为 256
      kg.init(256, new SecureRandom(password.getBytes()));
      // 生成一个密钥
      SecretKey secretKey = kg.generateKey();
      // 转换为AES专用密钥
      return new SecretKeySpec(secretKey.getEncoded(), KEY_ALGORITHM);
    } catch (NoSuchAlgorithmException ex) {
      Logger.getLogger(AESUtil.class.getName()).log(Level.SEVERE, null, ex);
    }
    return null;
  }
}