package wang.yobbo.sys.dao;

import wang.yobbo.sys.entity.NextRobotSysMenu;

import java.util.List;

public interface SysMenuDao {
    List<NextRobotSysMenu> findByPId(String pid);

    NextRobotSysMenu findById(String id);

    NextRobotSysMenu save(NextRobotSysMenu sysMenu);

    int delete(String id);
}
