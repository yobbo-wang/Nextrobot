package wang.yobbo.sys.dao.Impl;

import org.springframework.stereotype.Component;
import wang.yobbo.common.appengine.BaseDaoManager;
import wang.yobbo.common.appengine.dao.Impl.BaseDaoImpl;
import wang.yobbo.sys.dao.SysMenuDao;
import wang.yobbo.sys.entity.NextRobotSysMenu;

import javax.persistence.Query;
import java.util.List;

@Component
public class SysMenuDaoImpl extends BaseDaoImpl<NextRobotSysMenu, String> implements SysMenuDao{
    public List<NextRobotSysMenu> findByPId(String pid) {
        BaseDaoManager baseDaoManager = super.getBaseDaoManager();
        String msql = (pid != null && pid.length() > 0) ?
                "select nextRobotSysMenu from NextRobotSysMenu nextRobotSysMenu where nextRobotSysMenu.parentId=:pid" :
                "select nextRobotSysMenu from NextRobotSysMenu nextRobotSysMenu where nextRobotSysMenu.parentId is null";
        Query query = baseDaoManager.getEntityManager().createQuery(msql);
        if((pid != null && pid.length() > 0)){
            query.setParameter("pid", pid);
        }
        List<NextRobotSysMenu> list = query.getResultList();
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
}
