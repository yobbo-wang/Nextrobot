package wang.yobbo.sys.security;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.jetbrains.annotations.Nullable;

public final class CurrentUser {
    public CurrentUser() {
    }

    @Nullable
    public static ShiroUser getPrincipal() {
        Subject subject = getSubject();
        return subject != null ? (ShiroUser)subject.getPrincipal() : null;
    }

    public static String getUserAccount() {
        return getPrincipal().getUserAccount();
    }

    public static String getUserID() {
        return getPrincipal().getUserID();
    }

    public static String getTelePhone() {
        return getPrincipal().getTelePhone();
    }

    public static Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    public static PrincipalCollection getPrincipals() {
        return getSubject().getPrincipals();
    }

    public static SecurityManager getSecurityManager() {
        return SecurityUtils.getSecurityManager();
    }
}
