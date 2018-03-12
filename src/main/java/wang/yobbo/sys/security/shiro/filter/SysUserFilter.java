package wang.yobbo.sys.security.shiro.filter;

import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import wang.yobbo.common.appengine.plugin.NtConstants;
import wang.yobbo.sys.entity.NextRobotSysUser;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

public class SysUserFilter extends AccessControlFilter {
    private static final Logger LOG = LoggerFactory.getLogger(CustomLogoutFilter.class);
    private String userDisabledUrl;
    private String userNotfoundUrl;
    private String userUnknownErrorUrl;

    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        NextRobotSysUser user = (NextRobotSysUser) request.getAttribute(NtConstants.CURRENT_USER);
        if (user == null) {
            return true;
        }
        if (Boolean.TRUE.equals(user.getDeleted()) ||  "enabled".equals(user.getStatus())) {
            getSubject(request, response).logout();
            saveRequestAndRedirectToLogin(request, response);
            return false;
        }
        return true;
    }

    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        getSubject(request, response).logout();
        saveRequestAndRedirectToLogin(request, response);
        return true;
    }

    @Override
    protected void redirectToLogin(ServletRequest request,
                                   ServletResponse response) throws IOException {
        NextRobotSysUser user = (NextRobotSysUser) request.getAttribute(NtConstants.CURRENT_USER);
        String url = null;
        if (Boolean.TRUE.equals(user.getDeleted())) {
            url = getUserNotfoundUrl();
        } else if ("disabled".equals(user.getStatus())) {
            url = getUserDisabledUrl();
        } else {
            url = getUserUnknownErrorUrl();
        }
        WebUtils.issueRedirect(request, response, url);
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
