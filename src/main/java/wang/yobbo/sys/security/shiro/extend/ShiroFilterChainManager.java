package wang.yobbo.sys.security.shiro.extend;

import org.apache.shiro.config.ConfigurationException;
import org.apache.shiro.util.StringUtils;
import org.apache.shiro.web.filter.PathConfigProcessor;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.NamedFilterList;
import org.apache.shiro.web.filter.mgt.SimpleNamedFilterList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wang.yobbo.common.appengine.cache.NextRobotCacheManager;
import wang.yobbo.common.appengine.plugin.NtCacheConstants;

import javax.annotation.PostConstruct;
import javax.servlet.Filter;
import java.util.*;

@Service
@Transactional
public class ShiroFilterChainManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShiroFilterChainManager.class);

    private static final String REGEX="\\{.*?\\}";
    //private static final Pattern PATTERN=Pattern.compile(REGEX);
    private Map<String, NamedFilterList> defaultFilterChains;
    @Autowired private DefaultFilterChainManager defaultFilterChainManager;
    @Autowired private NextRobotCacheManager nextRobotCacheManager;

    @PostConstruct
    public void init() {
        defaultFilterChains = new LinkedHashMap<String, NamedFilterList>(defaultFilterChainManager.getFilterChains());
        LOGGER.info("defaultFilterChains:{}", defaultFilterChains);
        initFilterChains();
    }

    public void initFilterChains() {
        List<UrlAccessResource> urlAccessResources = new ArrayList<UrlAccessResource>();
        initFilterChains(urlAccessResources);
    }

    public void initFilterChains(List<UrlAccessResource> urlAccessResources) {
        // 保存url和过滤器的关系
        Map<String,String> filterChains = new LinkedHashMap<String,String>();
        // 需要通过角色进行过滤的Url保存url和角色的关系
        Map<String,String> urlAndRoles = new LinkedHashMap<String,String>();
        if (defaultFilterChainManager != null) {
            defaultFilterChainManager.getFilterChains().clear();
            defaultFilterChainManager.getFilterChains().putAll(defaultFilterChains);
        }
        // 1、首先删除以前老的filter chain并注册默认的
        if(defaultFilterChains != null && !defaultFilterChains.isEmpty()){
            Set<Map.Entry<String, NamedFilterList> > entrySet = defaultFilterChains.entrySet();
            for (Map.Entry<String, NamedFilterList> entry : entrySet) {
                filterChains.put(entry.getKey(),entry.getValue().getName());
            }
        }

        if (!urlAccessResources.isEmpty()) {
            for (UrlAccessResource urlAccessResource : urlAccessResources) {
                String url = urlAccessResource.getUrl();
                LOGGER.debug("roles:{},permissions:{}", urlAccessResource.getRoles(), urlAccessResource.getPermissions());
                if (StringUtils.hasText(url)) {
                    // 注册roles filter
                    if (!urlAccessResource.getRoles().isEmpty()) {
                        String strUrl = replaceUrl(url);
                        String roleNames = listToString(urlAccessResource.getRoles());
                        addUrlPermissions(strUrl, roleNames);
                        filterChains.put(strUrl, "anyRole");
                        urlAndRoles.put(strUrl, roleNames);
                    }
                }
            }
        }

        this.nextRobotCacheManager.put(NtCacheConstants.CACHE_FILTER_CHAINMANAGER, "filterChains", filterChains);
        this.nextRobotCacheManager.put(NtCacheConstants.CACHE_URL_ROLE,"urlAndRoles",urlAndRoles);
        LOGGER.info("filterChain:{}", defaultFilterChainManager.getFilterChains());
    }

    private String listToString(Collection<String> elements) {
        StringBuilder allRoles = new StringBuilder();
        for (String element : elements) {
            allRoles.append(element).append(",");
        }
        return allRoles.substring(0, allRoles.length() - 1);
    }

    /**
     * 把url地址中的/{XXX}替换为/**
     * @param url
     * @return
     */
    private String replaceUrl(String url){
        return url.replaceAll(REGEX, "**");
    }

    /**
     * 将URL和角色权限进行绑定
     * @param url
     * @param roleNames
     */
    public void addUrlPermissions(String url,String roleNames){
        defaultFilterChainManager.addToChain(replaceUrl(url), "anyRole", roleNames);
    }

    /**
     * 初始化andRole
     * @param chainName
     */
    public void initAnyRoleFilter(String chainName) {
        if(nextRobotCacheManager != null){
            Map<String,String> urlAndRoles= (Map<String, String>) nextRobotCacheManager.get(NtCacheConstants.CACHE_URL_ROLE,"urlAndRoles",LinkedHashMap.class);
            if(urlAndRoles!=null && urlAndRoles.containsKey(chainName)){
                String roleNames=urlAndRoles.get(chainName);

                Filter filter = this.defaultFilterChainManager.getFilter("anyRole");
                if(filter == null) {
                    throw new IllegalArgumentException("There is no filter with name \'anyRole\' to apply to chain [" + chainName + "] in the pool of available Filters.  Ensure a " + "filter with that name/path has first been registered with the addFilter method(s).");
                } else {

                    if(filter instanceof PathConfigProcessor) {
                        ((PathConfigProcessor)filter).processPathConfig(chainName, roleNames);
                    } else if(StringUtils.hasText(roleNames)) {
                        String msg = "chainSpecificFilterConfig was specified, but the underlying Filter instance is not an \'instanceof\' " + PathConfigProcessor.class.getName() + ".  This is required if the filter is to accept " + "chain-specific configuration.";
                        throw new ConfigurationException(msg);
                    }
                    NamedFilterList chain = new SimpleNamedFilterList(chainName);
                    this.defaultFilterChainManager.getFilterChains().put(chainName,chain);
                    chain.add(filter);
                }
            }
        }

    }

}
