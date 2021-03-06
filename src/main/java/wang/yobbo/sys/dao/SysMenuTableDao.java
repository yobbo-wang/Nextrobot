package wang.yobbo.sys.dao;

import wang.yobbo.sys.entity.SysMenuEntity;

import java.util.List;

public interface SysMenuTableDao {
    /**
     * 保存或更新菜单实体
     * @param sysMenuTable
     * @return
     */
    SysMenuEntity addEntity(SysMenuEntity sysMenuTable) throws Exception;

    int deleteEntity(String id);

    SysMenuEntity findSysMenuTableById(String id);

    List<SysMenuEntity> getEntitys();
}
