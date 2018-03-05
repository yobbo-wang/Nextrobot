package wang.yobbo.sys.dao;

import wang.yobbo.sys.entity.NextRobotSysMenuEntity;

public interface NextRobotSysMenuTableDao {
    /**
     * 保存或更新菜单实体
     * @param sysMenuTable
     * @return
     */
    NextRobotSysMenuEntity addEntity(NextRobotSysMenuEntity sysMenuTable);

    int deleteEntity(String id);

    NextRobotSysMenuEntity findSysMenuTableById(String id);
}
