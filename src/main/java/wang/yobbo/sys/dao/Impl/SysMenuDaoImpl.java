package wang.yobbo.sys.dao.Impl;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import wang.yobbo.common.appengine.dao.Impl.BaseDaoImpl;
import wang.yobbo.common.entity.Searchable;
import wang.yobbo.sys.dao.SysMenuDao;
import wang.yobbo.sys.entity.NextRobotSysMenu;

import java.util.List;

@Component
public class SysMenuDaoImpl extends BaseDaoImpl<NextRobotSysMenu, String> implements SysMenuDao{
    public List<NextRobotSysMenu> findByPId(String pid) {
        String hql = (pid != null && pid.length() > 0) ?
                "select nextRobotSysMenu from NextRobotSysMenu nextRobotSysMenu where nextRobotSysMenu.parentId=:pid" :
                "select nextRobotSysMenu from NextRobotSysMenu nextRobotSysMenu where nextRobotSysMenu.parentId is null";
        List<NextRobotSysMenu> list = null;
        if(pid != null && pid.length() > 0){
            list = this.queryByHQL(hql, NextRobotSysMenu.class, pid);
        }else{
            list = this.queryByHQL(hql, NextRobotSysMenu.class);
        }
        return list;
    }

    public NextRobotSysMenu findById(String id) {
        return super.get(id);
    }

    public NextRobotSysMenu save(NextRobotSysMenu sysMenu) {
        return super.saveOfEntity(sysMenu);
    }

    public int delete(String id) {
        return super.deleteById(id);
    }

    public Page<NextRobotSysMenu> getPage(Searchable searchable) {
        return super.find(searchable);
    }
}
