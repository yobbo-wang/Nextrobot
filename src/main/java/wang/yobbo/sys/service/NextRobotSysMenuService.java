package wang.yobbo.sys.service;

import org.springframework.data.domain.Page;
import wang.yobbo.common.appengine.entity.Searchable;
import wang.yobbo.sys.entity.NextRobotBusinessTemplate;
import wang.yobbo.sys.entity.NextRobotEntityProperty;
import wang.yobbo.sys.entity.NextRobotSysMenu;
import wang.yobbo.sys.entity.NextRobotSysMenuEntity;

import java.util.List;

public interface NextRobotSysMenuService {
    Page<NextRobotSysMenu> getPage(Searchable searchable);

    Long getCount(Searchable searchable);

    List<NextRobotSysMenu> findByPId(String pid);

    NextRobotSysMenu findById(String id);

    NextRobotSysMenu save(NextRobotSysMenu sysMenu);

    int delete(String id);

    NextRobotSysMenuEntity addEntity(NextRobotSysMenuEntity sysMenuTable);

    int deleteEntity(String id);

    NextRobotSysMenuEntity findSysMenuTableById(String id);

    boolean createBusinessCode(NextRobotSysMenuEntity nextRobotSysMenuTable, String entityMode, List<NextRobotEntityProperty> nextRobotEntityProperties) throws Exception;

    List<NextRobotEntityProperty> saveEntityProperty(List<NextRobotEntityProperty> nextRobotEntityProperties);

    NextRobotBusinessTemplate saveBusinessTemplate(NextRobotBusinessTemplate nextRobotBusinessTemplate);

    NextRobotBusinessTemplate findTemplate(String id);
}
