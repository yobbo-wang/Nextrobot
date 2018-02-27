package wang.yobbo.common.security.shiro.extend;

import org.apache.shiro.config.Ini;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.web.config.IniFilterChainResolverFactory;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Filter;
import java.util.Map;

public class CustomDefaultFilterChainManager extends DefaultFilterChainManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomDefaultFilterChainManager.class);

    private String loginUrl;
    private String successUrl;
    private String unauthorizedUrl;
    private Map<String, String> filterChainDefinitionMap = null;

    /**
     * 设定自定义拦截器
     *
     * @param customFilters
     */
    public void setCustomFilters(Map<String, Filter> customFilters) {
        for (Map.Entry<String, Filter> entry : customFilters.entrySet()) {
            addFilter(entry.getKey(), entry.getValue(), false);
            LOGGER.info("filter name:{},value:{}", entry.getKey(), entry.getValue());
        }
    }

    /**
     * 设置默认的拦截器链
     *
     * @param filterChainDefinitions
     */
    public void setDefaultFilterChainDefinitions(String filterChainDefinitions) {
        LOGGER.info("filterChainDefinitions:{}", filterChainDefinitions);
        Ini ini = new Ini();
        ini.load(filterChainDefinitions);

        Ini.Section section = ini.getSection(IniFilterChainResolverFactory.URLS);

        if (CollectionUtils.isEmpty(section)) {
            section = ini.getSection(Ini.DEFAULT_SECTION_NAME);
        }
        setFilterChainDefinitionMap(section);
    }

    public String getLoginUrl() {
        return loginUrl;
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }

    public String getSuccessUrl() {
        return successUrl;
    }

    public void setSuccessUrl(String successUrl) {
        this.successUrl = successUrl;
    }

    public String getUnauthorizedUrl() {
        return unauthorizedUrl;
    }

    public void setUnauthorizedUrl(String unauthorizedUrl) {
        this.unauthorizedUrl = unauthorizedUrl;
    }

    public Map<String, String> getFilterChainDefinitionMap() {
        return filterChainDefinitionMap;
    }

    public void setFilterChainDefinitionMap(Map<String, String> filterChainDefinitionMap) {
        this.filterChainDefinitionMap = filterChainDefinitionMap;
    }
}
