package wang.yobbo.sys.dao.Impl;

import org.springframework.stereotype.Component;
import wang.yobbo.common.appengine.dao.Impl.BaseDaoImpl;
import wang.yobbo.sys.dao.SysMenuTableDao;
import wang.yobbo.sys.entity.NextRobotSysMenuEntity;

@Component
public class SysMenuTableDaoImpl extends BaseDaoImpl<NextRobotSysMenuEntity, String> implements SysMenuTableDao {

    public NextRobotSysMenuEntity addEntity(NextRobotSysMenuEntity sysMenuTable) {
        return super.save(sysMenuTable);
    }

    public int deleteEntity(String id) {
        return super.delete(id);
    }

    public NextRobotSysMenuEntity findSysMenuTableById(String id) {
        return super.get(id);
    }

}
