package wang.yobbo.sys.dao;

import org.springframework.data.domain.Page;
import wang.yobbo.common.appengine.entity.Searchable;
import wang.yobbo.sys.entity.SysMenu;

import java.util.List;

public interface SysMenuDao {
    List<SysMenu> findByPId(String pid);

    SysMenu findById(String id);

    SysMenu save(SysMenu sysMenu) throws Exception;

    int delete(String id);

    Page<SysMenu> getPage(Searchable searchable);

    Long getCount(Searchable searchable);
}
