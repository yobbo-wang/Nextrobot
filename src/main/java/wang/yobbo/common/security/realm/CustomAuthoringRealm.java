package wang.yobbo.common.security.realm;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * Created by xiaoyang on 2017/12/30.
 *
 */
public class CustomAuthoringRealm extends AuthorizingRealm{
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        principalCollection.getPrimaryPrincipal();  //获取用户session

        return null;
    }

    /**
     * 实现shiro中登录方法
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken upToken = (UsernamePasswordToken) authenticationToken;
        String username = upToken.getUsername().trim();
        String password = new String();
        if (upToken.getPassword() != null) {
            password = new String(upToken.getPassword());
        }
        Object user = new Object();
        SimpleAuthenticationInfo result = new SimpleAuthenticationInfo(user, password.toCharArray(),
                getName());
        return result;
    }
}
