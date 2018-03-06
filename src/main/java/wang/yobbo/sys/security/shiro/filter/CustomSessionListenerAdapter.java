package wang.yobbo.sys.security.shiro.filter;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListenerAdapter;

/**
 * Created by xiaoyang on 2017/12/30.
 *
 */
public class CustomSessionListenerAdapter extends SessionListenerAdapter {

    @Override
    public void onExpiration(Session session) {
        super.onExpiration(session);
    }
}
