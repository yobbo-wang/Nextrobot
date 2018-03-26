package wang.yobbo.sys.dao.Impl;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import wang.yobbo.common.appengine.dao.Impl.BaseDaoImpl;
import wang.yobbo.common.appengine.entity.Searchable;
import wang.yobbo.sys.dao.SysMenuDao;
import wang.yobbo.sys.entity.SysMenu;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class SysMenuDaoImpl extends BaseDaoImpl<SysMenu, String> implements SysMenuDao {
    public List<SysMenu> findByPId(String pid) {
        String hql = (pid != null && pid.length() > 0) ?
                "select sysMenu from SysMenu sysMenu where sysMenu.parentId = :parentId order by orderNumber asc" :
                "select sysMenu from SysMenu sysMenu where sysMenu.parentId is null order by orderNumber asc";
        List<SysMenu> list = null;
        if(pid != null && pid.length() > 0){
            Map<String, Object> params = new HashMap<>();
            params.put("parentId", pid);
            list = this.findByHQL(hql, params, SysMenu.class);
        }else{
            list = this.findByHQL(hql, null,SysMenu.class);
        }
        return list;
    }

    public SysMenu findById(String id) {
        return super.findById(id);
    }

    public SysMenu save(SysMenu sysMenu) throws Exception {
        return super.save(sysMenu);
    }

    public int delete(String id) {
        return super.delete(id);
    }

    public Page<SysMenu> getPage(Searchable searchable) {
        return super.findPage(searchable);
    }

    public Long getCount(Searchable searchable) {
        return super.count(searchable);
    }
}
