package wang.yobbo.sys.security.shiro.extend;

import java.util.HashSet;
import java.util.Set;

public class UrlAccessResource {
    private String url;
    private Set<String> roles = new HashSet<String>();
    private Set<String> permissions = new HashSet<String>();

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public Set<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<String> permissions) {
        this.permissions = permissions;
    }
}
