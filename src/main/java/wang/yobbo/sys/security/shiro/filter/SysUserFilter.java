package wang.yobbo.sys.security.shiro.filter;

import org.apache.shiro.web.filter.AccessControlFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class SysUserFilter extends AccessControlFilter {
    private static final Logger LOG = LoggerFactory.getLogger(CustomLogoutFilter.class);
    private String userDisabledUrl;
    private String userNotfoundUrl;
    private String userUnknownErrorUrl;

    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        return false;
    }

    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        return false;
    }

    public String getUserDisabledUrl() {
        return userDisabledUrl;
    }

    public void setUserDisabledUrl(String userDisabledUrl) {
        this.userDisabledUrl = userDisabledUrl;
    }

    public String getUserNotfoundUrl() {
        return userNotfoundUrl;
    }

    public void setUserNotfoundUrl(String userNotfoundUrl) {
        this.userNotfoundUrl = userNotfoundUrl;
    }

    public String getUserUnknownErrorUrl() {
        return userUnknownErrorUrl;
    }

    public void setUserUnknownErrorUrl(String userUnknownErrorUrl) {
        this.userUnknownErrorUrl = userUnknownErrorUrl;
    }
}
