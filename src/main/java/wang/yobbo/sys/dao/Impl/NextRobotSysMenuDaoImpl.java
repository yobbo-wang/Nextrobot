package wang.yobbo.sys.dao.Impl;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import wang.yobbo.common.appengine.dao.Impl.BaseDaoImpl;
import wang.yobbo.common.appengine.entity.Searchable;
import wang.yobbo.sys.dao.NextRobotSysMenuDao;
import wang.yobbo.sys.entity.NextRobotSysMenu;

import java.util.List;

@Component
public class NextRobotSysMenuDaoImpl extends BaseDaoImpl<NextRobotSysMenu, String> implements NextRobotSysMenuDao {
    public List<NextRobotSysMenu> findByPId(String pid) {
        String hql = (pid != null && pid.length() > 0) ?
                "select nextRobotSysMenu from NextRobotSysMenu nextRobotSysMenu where nextRobotSysMenu.parentId = ?1" :
                "select nextRobotSysMenu from NextRobotSysMenu nextRobotSysMenu where nextRobotSysMenu.parentId is null";
        List<NextRobotSysMenu> list = null;
        if(pid != null && pid.length() > 0){
            list = this.findByHQL(hql, NextRobotSysMenu.class, pid);
        }else{
            list = this.findByHQL(hql, NextRobotSysMenu.class);
        }
        return list;
    }

    public NextRobotSysMenu findById(String id) {
        return super.findById(id);
    }

    public NextRobotSysMenu save(NextRobotSysMenu sysMenu) {
        return super.save(sysMenu);
    }

    public int delete(String id) {
        return super.delete(id);
    }

    public Page<NextRobotSysMenu> getPage(Searchable searchable) {
        return super.findPage(searchable);
    }

    public Long getCount(Searchable searchable) {
        return super.count(searchable);
    }
}
