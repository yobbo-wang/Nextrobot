package wang.yobbo.sys.dao.Impl;

import org.springframework.stereotype.Component;
import wang.yobbo.common.appengine.dao.Impl.BaseDaoImpl;
import wang.yobbo.sys.dao.NextRobotSysMenuTableDao;
import wang.yobbo.sys.entity.NextRobotSysMenuEntity;

@Component
public class NextRobotSysMenuTableDaoImpl extends BaseDaoImpl<NextRobotSysMenuEntity, String> implements NextRobotSysMenuTableDao {

    public NextRobotSysMenuEntity addEntity(NextRobotSysMenuEntity sysMenuTable) {
        return super.save(sysMenuTable);
    }

    public int deleteEntity(String id) {
        return super.delete(id);
    }

    public NextRobotSysMenuEntity findSysMenuTableById(String id) {
        return super.findById(id);
    }

}
