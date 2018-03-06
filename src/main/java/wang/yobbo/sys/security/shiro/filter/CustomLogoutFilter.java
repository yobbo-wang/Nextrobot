package wang.yobbo.sys.security.shiro.filter;

import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
/**
 * 用户登出过滤器
 */
public class CustomLogoutFilter extends LogoutFilter {
    private static final Logger LOG = LoggerFactory.getLogger(CustomLogoutFilter.class);
    private SessionDAO sessionDAO;
    private String redirectUrl;
    private String ssoRedirectUrl;

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        return super.preHandle(request, response);
    }

    public SessionDAO getSessionDAO() {
        return sessionDAO;
    }

    public void setSessionDAO(SessionDAO sessionDAO) {
        this.sessionDAO = sessionDAO;
    }

    @Override
    public String getRedirectUrl() {
        return redirectUrl;
    }

    @Override
    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public String getSsoRedirectUrl() {
        return ssoRedirectUrl;
    }

    public void setSsoRedirectUrl(String ssoRedirectUrl) {
        this.ssoRedirectUrl = ssoRedirectUrl;
    }
}
