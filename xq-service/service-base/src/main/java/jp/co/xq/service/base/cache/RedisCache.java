package jp.co.xq.service.base.cache;

import jp.co.xq.base.utils.SerializeUtils;
import jp.co.xq.service.base.utils.SpringContextUtils;
import org.apache.ibatis.cache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Redis キャッシング処理
 *
 * @author tian w
 */
public class RedisCache implements Cache {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisCache.class);

    private RedisTemplate<Object, Object> redisTemplate;

    /**
     * Redis Id
     **/
    private String id;

    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    /**
     * コンストラクタ
     *
     * @param id
     */
    public RedisCache(String id) {
        if (id == null) {
            throw new IllegalArgumentException("キャッシュをインスタンスをするため、ＩＤが必要です");
        }
        this.id = id;
    }


    @Override
    public ReadWriteLock getReadWriteLock() {
        return readWriteLock;
    }

    @Override
    public String getId() {
        return id;
    }

    /**
     * Redisにデータを保存する
     *
     * @param key
     * @param value
     */
    @Override
    public void putObject(final Object key, final Object value) {
        LOGGER.debug("データをキャッシュに保存する");
        getRedisTemplate();
        redisTemplate.execute((RedisCallback) connection -> {
            connection.hSet(this.id.getBytes(), key.toString().getBytes(), SerializeUtils.serialize(value));
            return null;
        });
    }


    /**
     * KEYよりRedisからデータを取得する
     *
     * @param key
     * @return
     */
    @Override
    public Object getObject(Object key) {
        long startTime = System.currentTimeMillis();
        getRedisTemplate();
        Object result = redisTemplate.execute((RedisCallback) connection -> {
            return SerializeUtils.unserialize(connection.hGet(this.id.getBytes(), key.toString().getBytes()));
        });
        LOGGER.debug("キャッシュデータ取得 KEY : " + key + " result: " + result + " spend time : " + (System.currentTimeMillis() - startTime));
        return result;
    }

    /**
     * Redisからキャッシュをクリアする
     *
     * @param key
     * @return
     */
    @Override
    public Object removeObject(Object key) {
        getRedisTemplate();
        Object result = redisTemplate.execute((RedisCallback) connection -> {
            return connection.hDel(this.id.getBytes(), key.toString().getBytes());
        });
        LOGGER.debug("キャッシュクリア　KEY " + key + " result: " + result);
        return result;
    }

    /**
     * キャッシュクリア処理
     */
    @Override
    public void clear() {
        getRedisTemplate();
        Object result = redisTemplate.execute((RedisCallback) connection -> {
            return connection.del(this.id.getBytes());
        });
        LOGGER.debug(getId() + "  キャッシュをクリアする : " + result);
    }

    /**
     * ＤＢ全体クリア処理
     */
    public void flushDb() {
        getRedisTemplate();
        redisTemplate.execute((RedisCallback) connection -> {
            connection.flushDb();
            return null;
        });
        LOGGER.debug("すべてのキャッシュをクリアする");
    }

    @Override
    public int getSize() {
        getRedisTemplate();
        Long size = redisTemplate.execute((RedisCallback<Long>) connection -> {
            return connection.dbSize();
        });
        return size.intValue();
    }

    private RedisTemplate getRedisTemplate() {
        if (redisTemplate == null) {
            redisTemplate = SpringContextUtils.getBean("redisTemplate", RedisTemplate.class);
        }
        return redisTemplate;
    }
}