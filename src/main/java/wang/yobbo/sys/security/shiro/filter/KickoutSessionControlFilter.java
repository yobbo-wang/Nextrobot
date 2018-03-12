package wang.yobbo.sys.security.shiro.filter;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import wang.yobbo.common.appengine.cache.NextRobotCacheManager;
import wang.yobbo.common.appengine.plugin.NtCacheConstants;
import wang.yobbo.sys.security.ShiroUser;

import javax.inject.Inject;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;
import java.util.Deque;
import java.util.LinkedList;

/**
 * 提出用户过滤器
 */
public class KickoutSessionControlFilter extends AccessControlFilter {
    private static final Logger LOG = LoggerFactory.getLogger(CustomLogoutFilter.class);
    @Inject private NextRobotCacheManager nextRobotCacheManager;
    private SessionDAO sessionDAO;
    private SessionManager sessionManager;
    private boolean kickoutAfter = false; //false: 默认踢出已登录的用户
    private int maxSession = 0; //同用户最大用户数
    private String kickoutUrl;  //踢出后地址

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        Subject subject = getSubject(request, response);
        if (!subject.isAuthenticated() && !subject.isRemembered()) {
            return true;
        }

        Session session = subject.getSession();
        String username = ((ShiroUser) subject.getPrincipal()).getUserAccount();
        Serializable sessionId = session.getId();

        Deque<Serializable> deque = (Deque<Serializable>) this.nextRobotCacheManager.get(NtCacheConstants.CACHE_SHIRO_KICKOUT_SESSION,username,LinkedList.class);
        if(deque == null) {
            deque = new LinkedList<Serializable>();
        }

        if(!deque.contains(sessionId) && session.getAttribute("kickout") == null) {
            deque.push(sessionId);
        }
        this.nextRobotCacheManager.put(NtCacheConstants.CACHE_SHIRO_KICKOUT_SESSION,username,deque);
        // 如果登录数已超过规定数量，从最前面开始踢出
        while(deque.size() > maxSession) {
            Serializable kickoutSessionId = null;
            if(kickoutAfter) { //如果踢出后者
                kickoutSessionId = deque.removeFirst();
            } else { //否则踢出前者
                kickoutSessionId = deque.removeLast();
            }

            try{
                Session kickoutSession = sessionManager.getSession(new DefaultSessionKey(kickoutSessionId));
                deque.remove(kickoutSession.getId() + "");
                this.nextRobotCacheManager.put(NtCacheConstants.CACHE_SHIRO_KICKOUT_SESSION,username,deque);
                if(kickoutSession != null) {
                    kickoutSession.setAttribute("kickout", true);
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }

        if (session.getAttribute("kickout") != null) {
            try{
                subject.logout();
                deque.remove(subject.getSession().getId()+"");
                this.nextRobotCacheManager.put("shiroKickoutSession",username,deque);

            }catch(Exception e){
                e.printStackTrace();
            }
            saveRequest(request);
            WebUtils.issueRedirect(request, response, kickoutUrl);
            return false;
        }
        return true;
    }

    public SessionDAO getSessionDAO() {
        return sessionDAO;
    }

    public void setSessionDAO(SessionDAO sessionDAO) {
        this.sessionDAO = sessionDAO;
    }

    public SessionManager getSessionManager() {
        return sessionManager;
    }

    public void setSessionManager(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    public boolean isKickoutAfter() {
        return kickoutAfter;
    }

    public void setKickoutAfter(boolean kickoutAfter) {
        this.kickoutAfter = kickoutAfter;
    }

    public int getMaxSession() {
        return maxSession;
    }

    public void setMaxSession(int maxSession) {
        this.maxSession = maxSession;
    }

    public String getKickoutUrl() {
        return kickoutUrl;
    }

    public void setKickoutUrl(String kickoutUrl) {
        this.kickoutUrl = kickoutUrl;
    }
}
