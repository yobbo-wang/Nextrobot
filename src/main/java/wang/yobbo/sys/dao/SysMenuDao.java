package wang.yobbo.sys.dao;

import org.springframework.data.domain.Page;
import wang.yobbo.common.appengine.entity.Searchable;
import wang.yobbo.sys.entity.NextRobotSysMenu;

import java.util.List;

public interface SysMenuDao {
    List<NextRobotSysMenu> findByPId(String pid);

    NextRobotSysMenu findById(String id);

    NextRobotSysMenu save(NextRobotSysMenu sysMenu);

    int delete(String id);

    Page<NextRobotSysMenu> getPage(Searchable searchable);

    Long getCount(Searchable searchable);
}
