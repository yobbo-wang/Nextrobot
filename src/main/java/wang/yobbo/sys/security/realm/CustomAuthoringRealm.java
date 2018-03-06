package wang.yobbo.sys.security.realm;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import wang.yobbo.sys.entity.NextRobotSysUser;
import wang.yobbo.sys.service.SysUserService;

/**
 * 自定义Realm 放入用户以及角色和权限
 * @author
 *
 */
public class CustomAuthoringRealm extends AuthorizingRealm{
    private static final Logger LOG = LoggerFactory.getLogger(CustomAuthoringRealm.class);
    @Autowired private SysUserService sysUserService;

    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        principalCollection.getPrimaryPrincipal();  //获取用户session

        return null;
    }

    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken upToken = (UsernamePasswordToken) authenticationToken;
        String username = upToken.getUsername().trim();
        String password = new String();
        if (upToken.getPassword() != null) {
            password = new String(upToken.getPassword());
        }
        SimpleAuthenticationInfo result = null;
        NextRobotSysUser nextRobotSysUser = null;
        try{
            nextRobotSysUser = this.sysUserService.login(username, password);
            if(nextRobotSysUser == null) throw new RuntimeException();
            result = new SimpleAuthenticationInfo(nextRobotSysUser, password.toCharArray(),
                    getName());
            return result;
        }catch (Exception e){
            LOG.error("登录错误：{}", e.getMessage());
            throw new AuthenticationException("user.unknown.error");
        }
    }
}
