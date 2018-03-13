package wang.yobbo.sys.security.realm;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import wang.yobbo.common.appengine.cache.NextRobotCacheManager;
import wang.yobbo.sys.entity.SysRole;
import wang.yobbo.sys.entity.SysUser;
import wang.yobbo.sys.security.ShiroUser;
import wang.yobbo.sys.service.SysUserService;

import java.util.*;

/**
 * 自定义Realm 放入用户以及角色和权限
 * @author
 *
 */
public class CustomAuthoringRealm extends AuthorizingRealm{
    private static final Logger LOG = LoggerFactory.getLogger(CustomAuthoringRealm.class);
    @Autowired private SysUserService sysUserService;
    @Autowired private NextRobotCacheManager nextRobotCacheManager;

    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        ShiroUser shiroUser = (ShiroUser) principalCollection.getPrimaryPrincipal();
        String userId = shiroUser.getUserID();

        List<SysRole> roles = (List<SysRole>) nextRobotCacheManager.get("userRecordCache", userId, SysRole.class);
        Set<String> roleNameSet = new HashSet<String>();
        Set<String> permissionSet = new HashSet<String>();
        for (SysRole role : roles) {
            roleNameSet.add(role.getId() + "-" + role.getRoleName());
            Map map = (Map) nextRobotCacheManager.get("roleRecordCache", role.getId(), HashMap.class);
            permissionSet.addAll(map.keySet());
        }

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.setRoles(roleNameSet);
        authorizationInfo.setStringPermissions(permissionSet);
        return authorizationInfo;
    }

    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken upToken = (UsernamePasswordToken) authenticationToken;
        String username = upToken.getUsername().trim();
        String password = new String();
        if (upToken.getPassword() != null) {
            password = new String(upToken.getPassword());
        }
        SimpleAuthenticationInfo result = null;
        SysUser nextRobotSysUser = null;
        try{
            nextRobotSysUser = this.sysUserService.login(username, password);
            if(nextRobotSysUser == null) throw new RuntimeException("没有用户.");
            result = new SimpleAuthenticationInfo(nextRobotSysUser, password.toCharArray(),
                    getName());
            return result;
        }catch (Exception e){
            LOG.error("登录错误：{}", e.getMessage());
            throw new AuthenticationException("user.unknown.error");
        }
    }
}
