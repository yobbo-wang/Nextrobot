package wang.yobbo.sys.security.shiro.extend;

import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import wang.yobbo.common.appengine.cache.NextRobotCacheManager;
import wang.yobbo.common.appengine.plugin.NtCacheConstants;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

@Component
public class CustomPathMatchingFilterChainResolver extends PathMatchingFilterChainResolver {
    @Autowired private NextRobotCacheManager nextRobotCacheManager;
    @Autowired private ShiroFilterChainManager filterChainManager;
    private CustomDefaultFilterChainManager customDefaultFilterChainManager;

    public void setCustomDefaultFilterChainManager(CustomDefaultFilterChainManager customDefaultFilterChainManager) {
        this.customDefaultFilterChainManager = customDefaultFilterChainManager;
        setFilterChainManager(customDefaultFilterChainManager);
    }

    @Override
    public FilterChain getChain(ServletRequest request, ServletResponse response, FilterChain originalChain) {
        Map<String,String> filterChains = (Map<String,String>) nextRobotCacheManager.get(NtCacheConstants.CACHE_FILTER_CHAINMANAGER, "filterChains", LinkedHashMap.class);
        if(filterChains == null && filterChainManager!=null){
            filterChainManager.initFilterChains();
            filterChains = (Map<String,String>) nextRobotCacheManager.get(NtCacheConstants.CACHE_FILTER_CHAINMANAGER, "filterChains", LinkedHashMap.class);
        }
        if(filterChains != null){
            String requestURI = getPathWithinApplication(request);
            Set<Map.Entry<String,String>> entrtySet = filterChains.entrySet();
            for (Map.Entry<String,String> entry : entrtySet) {
                String pathPattern = entry.getKey();
                if(pathMatches(pathPattern, requestURI)){
                    return customDefaultFilterChainManager.proxy(originalChain, pathPattern);
                }
            }
        }
        return null;
    }

}
