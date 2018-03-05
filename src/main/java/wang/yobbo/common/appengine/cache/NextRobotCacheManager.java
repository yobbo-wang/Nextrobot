package wang.yobbo.common.appengine.cache;

/**
 * Created by xiaoyang on 2018/3/5.
 */
public interface NextRobotCacheManager {
    Object get(String var1, Object var2, Class var3);

    Object put(String var1, Object var2, Object var3);

    Object remove(String var1, Object var2);

    void clear(String var1);
}
