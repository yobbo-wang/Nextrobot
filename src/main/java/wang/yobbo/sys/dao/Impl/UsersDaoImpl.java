package wang.yobbo.sys.dao.Impl;

import net.sf.ehcache.config.Searchable;
import org.springframework.stereotype.Service;
import wang.yobbo.common.appengine.dao.Impl.BaseDaoImpl;
import wang.yobbo.sys.dao.SysUsersDao;
import wang.yobbo.sys.entity.NextRobotSysUsers;

import java.util.List;

/**
 * Created by xiaoyang on 2017/12/28.
 *
 */
@Service
public class UsersDaoImpl extends BaseDaoImpl<NextRobotSysUsers, String> implements SysUsersDao {

    public NextRobotSysUsers findBySqlOne(String sql, Object...params) {
        return super.findBySqlOne(sql, NextRobotSysUsers.class, params);
    }
    @Override
    public Long findBySqlCount(String sql, Object... params) {
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
        return super.delete(primaryKey);
    }

    public void deleteForSysUser(NextRobotSysUsers user) {
        super.delete(user);
    }

    public NextRobotSysUsers updateUser(NextRobotSysUsers user) {
        return super.save(user);
    }

    public NextRobotSysUsers save(NextRobotSysUsers user){
        return super.save(user);
    }

    public List<NextRobotSysUsers> findAllBySql(String sql, Object[] params) {
        return super.fingAllBySql(sql, NextRobotSysUsers.class, params);
    }
}
