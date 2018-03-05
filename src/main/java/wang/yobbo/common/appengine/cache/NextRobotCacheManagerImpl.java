package wang.yobbo.common.appengine.cache;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.cache.CacheManager;

/**
 * Created by xiaoyang on 2018/3/5.
 */
public class NextRobotCacheManagerImpl implements NextRobotCacheManager{

    private CacheManager shiroCacheManager;
    private org.springframework.cache.CacheManager springCacheManager;
    private String defaultCacheName;

    public NextRobotCacheManagerImpl(String defaultCacheName) {
        if(StringUtils.isEmpty(defaultCacheName)) {
            throw new RuntimeException("必须提供一个默认的缓存");
        } else {
            this.defaultCacheName = defaultCacheName;
        }
    }

    public Object get(String var1, Object var2, Class var3) {
        return null;
    }

    public Object put(String var1, Object var2, Object var3) {
        return null;
    }

    public Object remove(String var1, Object var2) {
        return null;
    }

    public void clear(String var1) {

    }

    public void setShiroCacheManager(CacheManager shiroCacheManager) {
        this.shiroCacheManager = shiroCacheManager;
    }

    public void setSpringCacheManager(org.springframework.cache.CacheManager springCacheManager) {
        this.springCacheManager = springCacheManager;
    }
}
