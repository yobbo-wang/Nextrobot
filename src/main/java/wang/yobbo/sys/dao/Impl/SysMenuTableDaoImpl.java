package wang.yobbo.sys.dao.Impl;

import org.springframework.stereotype.Component;
import wang.yobbo.common.appengine.dao.Impl.BaseDaoImpl;
import wang.yobbo.sys.dao.SysMenuTableDao;
import wang.yobbo.sys.entity.NextRobotSysMenuTable;

@Component
public class SysMenuTableDaoImpl extends BaseDaoImpl<NextRobotSysMenuTable, String> implements SysMenuTableDao {

    public NextRobotSysMenuTable addEntity(NextRobotSysMenuTable sysMenuTable) {
        return super.saveOfEntity(sysMenuTable);
    }

    public int deleteEntity(String id) {
        return super.deleteById(id);
    }

    public NextRobotSysMenuTable findSysMenuTableById(String id) {
        return super.get(id);
    }
}
