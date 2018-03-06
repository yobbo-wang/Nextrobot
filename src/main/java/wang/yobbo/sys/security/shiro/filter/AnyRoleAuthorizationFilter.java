package wang.yobbo.sys.security.shiro.filter;

import org.apache.shiro.web.filter.authz.RolesAuthorizationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * 角色授权过滤器，Shiro自带的角色授权过滤器无法满足需求（即{@link RolesAuthorizationFilter}验证的是用户拥有的所有角色，却没有用户拥有任意一个角色。），
 * 因此重写Shiro的{@link RolesAuthorizationFilter}。
 *
 * @author yangjian
 */
public class AnyRoleAuthorizationFilter extends RolesAuthorizationFilter {

    @Override
    public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws IOException {
        return super.isAccessAllowed(request, response, mappedValue);
    }
}
