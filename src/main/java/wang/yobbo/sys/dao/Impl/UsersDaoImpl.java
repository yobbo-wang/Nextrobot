package wang.yobbo.sys.dao.Impl;

import net.sf.ehcache.config.Searchable;
import org.springframework.stereotype.Service;
import wang.yobbo.common.appengine.dao.Impl.BaseDaoImpl;
import wang.yobbo.sys.dao.SysUsersDao;
import wang.yobbo.sys.entity.NextRobotSysUsers;

import java.util.List;
import java.util.Map;

/**
 * Created by xiaoyang on 2017/12/28.
 *
 */
@Service
public class UsersDaoImpl extends BaseDaoImpl<NextRobotSysUsers, String> implements SysUsersDao {

    public Map<String, Object> findBySqlOne(String sql, Object...params) {
        return super.findBySqlOne(sql, params);
    }
    @Override
    public int findBySqlCount(String sql, Object... params) {
        return super.findBySqlCount(sql, params);
    }

    public long getCount(Searchable searchable) {
        return 0;
    }

    public List<NextRobotSysUsers> findUserAll() {
        return super.findAll();
    }

    public List<NextRobotSysUsers> findUserAll(NextRobotSysUsers user){
        return super.findAll(user);
    }

    public int deleteByPrimaryKeys(String... primaryKey) {
        return super.deleteById(primaryKey);
    }

    public void deleteForSysUser(NextRobotSysUsers user) {
        super.deleteOfEntity(user);
    }

    public NextRobotSysUsers updateUser(NextRobotSysUsers user) {
        return super.saveOfEntity(user);
    }

    public NextRobotSysUsers save(NextRobotSysUsers user){
        return super.saveOfEntity(user);
    }
}
