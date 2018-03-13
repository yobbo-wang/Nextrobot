package wang.yobbo.sys.service;

import org.springframework.data.domain.Page;
import wang.yobbo.common.appengine.entity.Searchable;
import wang.yobbo.sys.entity.BusinessTemplate;
import wang.yobbo.sys.entity.EntityProperty;
import wang.yobbo.sys.entity.SysMenu;
import wang.yobbo.sys.entity.SysMenuEntity;

import java.util.List;

public interface SysMenuService {
    Page<SysMenu> getPage(Searchable searchable);

    Long getCount(Searchable searchable);

    List<SysMenu> findByPId(String pid);

    SysMenu findById(String id);

    SysMenu save(SysMenu sysMenu) throws Exception;

    int delete(String id);

    SysMenuEntity addEntity(SysMenuEntity sysMenuTable) throws Exception;

    int deleteEntity(String id);

    SysMenuEntity findSysMenuTableById(String id);

    boolean createBusinessCode(SysMenuEntity nextRobotSysMenuTable, String entityMode, List<EntityProperty> nextRobotEntityProperties) throws Exception;

    List<EntityProperty> saveEntityProperty(List<EntityProperty> nextRobotEntityProperties) throws Exception;

    BusinessTemplate saveBusinessTemplate(BusinessTemplate nextRobotBusinessTemplate) throws Exception;

    BusinessTemplate findTemplate(String id);

    List<BusinessTemplate> findTemplateAll();
}
