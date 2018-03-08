package wang.yobbo.common.appengine.cache;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.cache.CacheManager;
import org.springframework.cache.Cache;

import java.util.*;

/**
 * Created by xiaoyang on 2018/3/5.
 * 缓存实现类
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

    public Object get(String cacheName, Object key, Class clazz) {
        Object value = null;
        Cache springCache = this.getSpringCache(cacheName);
        if (springCache != null) {
            Cache.ValueWrapper vwr = springCache.get(key);
            if (vwr != null && vwr.get() != null) {
                value = vwr.get();
            }
        } else {
            org.apache.shiro.cache.Cache shiroCache = this.getShiroCache(cacheName);
            if (shiroCache != null) {
                value = shiroCache.get(key);
            } else if (!this.defaultCacheName.equals(cacheName)) {
                value = this.get(this.defaultCacheName, key, clazz);
            }
        }

        if (value != null) {
            if (value instanceof Collection) {
                Iterator var7;
                Object object;
                if (Collection.class.isAssignableFrom(clazz)) {
                    Collection result = null;

                    try {
                        result = (Collection)clazz.newInstance();
                        var7 = ((Collection)value).iterator();

                        while(var7.hasNext()) {
                            object = var7.next();
                            result.add(object);
                        }
                    } catch (InstantiationException var9) {
                        var9.printStackTrace();
                    } catch (IllegalAccessException var10) {
                        var10.printStackTrace();
                    }

                    return result;
                }

                List result = new ArrayList();
                var7 = ((Collection)value).iterator();

                while(var7.hasNext()) {
                    object = var7.next();
                    if (object instanceof JSONObject) {
                        if (!Map.class.isAssignableFrom(clazz)) {
                            result.add(JSONObject.toJavaObject((JSONObject)object, clazz));
                        }
                    } else {
                        result.add(object);
                    }
                }
                return result;
            }

            if (value instanceof JSONObject && !Map.class.isAssignableFrom(clazz)) {
                return JSONObject.toJavaObject((JSONObject)value, clazz);
            }
        }
        return value;
    }

    // 新增缓存
    public Object put(String cacheName, Object key, Object value) {
        Cache springCache = this.getSpringCache(cacheName);
        if (springCache != null) {
            springCache.put(key, value);
        } else {
            org.apache.shiro.cache.Cache shiroCache = this.getShiroCache(cacheName);
            if (shiroCache != null) {
                shiroCache.put(key, value);
            } else if (!this.defaultCacheName.equals(cacheName)) {
                this.put(this.defaultCacheName, key, value);
            }
        }
        return value;
    }

    // 清除指定缓存
    public Object remove(String cacheName, Object key) {
        Object previous = this.get(cacheName, key, Object.class);
        Cache springCache = this.getSpringCache(cacheName);
        if (springCache != null) {
            springCache.evict(key);
        } else {
            org.apache.shiro.cache.Cache shiroCache = this.getShiroCache(cacheName);
            if (shiroCache != null) {
                shiroCache.remove(key);
            } else if (!this.defaultCacheName.equals(cacheName)) {
                this.remove(this.defaultCacheName, key);
            }
        }
        return previous;
    }

    public void clear(String cacheName) {
        Cache springCache = this.getSpringCache(cacheName);
        if (springCache != null) {
            springCache.clear();
        } else {
            org.apache.shiro.cache.Cache shiroCache = this.getShiroCache(cacheName);
            if (shiroCache != null) {
                shiroCache.clear();
            } else if (!this.defaultCacheName.equals(cacheName)) {
                this.clear(this.defaultCacheName);
            }
        }
    }

    private org.apache.shiro.cache.Cache getShiroCache(String cacheName) {
        if (this.shiroCacheManager != null) {
            org.apache.shiro.cache.Cache shiroCache = this.shiroCacheManager.getCache(cacheName);
            return shiroCache;
        } else {
            return null;
        }
    }

    private Cache getSpringCache(String cacheName) {
        return this.springCacheManager != null ? this.springCacheManager.getCache(cacheName) : null;
    }

    public void setShiroCacheManager(CacheManager shiroCacheManager) {
        this.shiroCacheManager = shiroCacheManager;
    }

    public void setSpringCacheManager(org.springframework.cache.CacheManager springCacheManager) {
        this.springCacheManager = springCacheManager;
    }

    public org.springframework.cache.CacheManager getSpringCacheManager() {
        return this.springCacheManager;
    }
}
