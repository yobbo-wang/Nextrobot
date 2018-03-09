package wang.yobbo.sys.security.shiro.filter;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListenerAdapter;
import wang.yobbo.common.appengine.cache.NextRobotCacheManager;
import wang.yobbo.common.appengine.plugin.NtCacheConstants;
import wang.yobbo.common.appengine.plugin.NtConstants;

import javax.inject.Inject;
import java.io.Serializable;
import java.util.Deque;
import java.util.LinkedList;

/**
 * Created by xiaoyang on 2017/12/30.
 *
 */
public class CustomSessionListenerAdapter extends SessionListenerAdapter {
    @Inject
    private NextRobotCacheManager nextRobotCacheManager;
    @Override
    public void onExpiration(Session session) {
        try {
            String username = (String)session.getAttribute(NtConstants.SESSION_USERNAME);
            Deque<Serializable> deque = (Deque<Serializable>) this.nextRobotCacheManager.get(NtCacheConstants.CACHE_SHIRO_KICKOUT_SESSION,
                    username, LinkedList.class);
            if(deque != null){
                deque.remove(session.getId());
                this.nextRobotCacheManager.put(NtCacheConstants.CACHE_SHIRO_KICKOUT_SESSION, username, deque);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
