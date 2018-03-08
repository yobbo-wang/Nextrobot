package wang.yobbo.common.appengine.cache;

/**
 * Created by xiaoyang on 2018/3/5.
 */
public interface NextRobotCacheManager {
    Object get(String cacheName, Object key, Class clazz);

    Object put(String cacheName, Object key, Object value);

    Object remove(String cacheName, Object key);

    void clear(String var1);
}
