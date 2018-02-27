package wang.yobbo.sys.dao;

import wang.yobbo.sys.entity.NextRobotSysMenuTable;

public interface SysMenuTableDao {
    /**
     * 保存或更新菜单实体
     * @param sysMenuTable
     * @return
     */
    NextRobotSysMenuTable addEntity(NextRobotSysMenuTable sysMenuTable);

    int deleteEntity(String id);

    NextRobotSysMenuTable findSysMenuTableById(String id);
}
