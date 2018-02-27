package wang.yobbo.sys.service;

import wang.yobbo.sys.entity.NextRobotSysMenu;
import wang.yobbo.sys.entity.NextRobotSysMenuTable;

import java.util.List;

public interface SysMenuService {
    List<NextRobotSysMenu> findByPId(String pid);

    NextRobotSysMenu findById(String id);

    NextRobotSysMenu save(NextRobotSysMenu sysMenu);

    int delete(String id);

    NextRobotSysMenuTable addEntity(NextRobotSysMenuTable sysMenuTable);

    int deleteEntity(String id);

    NextRobotSysMenuTable findSysMenuTableById(String id);

    boolean createBusinessCode(NextRobotSysMenuTable nextRobotSysMenuTable,String entityMode,String entityRow) throws Exception;

}
