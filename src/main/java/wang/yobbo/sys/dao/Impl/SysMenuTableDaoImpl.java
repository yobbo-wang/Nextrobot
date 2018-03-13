package wang.yobbo.sys.dao.Impl;

import org.springframework.stereotype.Component;
import wang.yobbo.common.appengine.dao.Impl.BaseDaoImpl;
import wang.yobbo.sys.dao.SysMenuTableDao;
import wang.yobbo.sys.entity.SysMenuEntity;

@Component
public class SysMenuTableDaoImpl extends BaseDaoImpl<SysMenuEntity, String> implements SysMenuTableDao {

    public SysMenuEntity addEntity(SysMenuEntity sysMenuTable) throws Exception {
        return super.save(sysMenuTable);
    }

    public int deleteEntity(String id) {
        return super.delete(id);
    }

    public SysMenuEntity findSysMenuTableById(String id) {
        return super.findById(id);
    }

}
