package wang.yobbo.sys.dao.Impl;

import net.sf.ehcache.config.Searchable;
import org.springframework.stereotype.Service;
import wang.yobbo.common.appengine.dao.Impl.BaseDaoImpl;
import wang.yobbo.sys.dao.SysUsersDao;
import wang.yobbo.sys.entity.NextRobotSysUser;

import java.util.List;

/**
 * Created by xiaoyang on 2017/12/28.
 *
 */
@Service
public class UsersDaoImpl extends BaseDaoImpl<NextRobotSysUser, String> implements SysUsersDao {

    public NextRobotSysUser findBySqlOne(String sql, Object...params) {
        return super.findBySqlOne(sql, NextRobotSysUser.class, params);
    }
    @Override
    public Long findBySqlCount(String sql, Object... params) {
        return super.findBySqlCount(sql, params);
    }

    public long getCount(Searchable searchable) {
        return 0;
    }

    public List<NextRobotSysUser> findUserAll() {
        return super.findAll();
    }

    public List<NextRobotSysUser> findUserAll(NextRobotSysUser user){
        return super.findAll(user);
    }

    public int deleteByPrimaryKeys(String... primaryKey) {
        return super.delete(primaryKey);
    }

    public void deleteForSysUser(NextRobotSysUser user) {
        super.delete(user);
    }

    public NextRobotSysUser updateUser(NextRobotSysUser user) {
        return super.save(user);
    }

    public NextRobotSysUser save(NextRobotSysUser user){
        return super.save(user);
    }

    public List<NextRobotSysUser> findAllBySql(String sql, Object[] params) {
        return super.fingAllBySql(sql, NextRobotSysUser.class, params);
    }

    @Override
    public NextRobotSysUser findByEmail(String email) {
        String hql = "select nextRobotSysUser from NextRobotSysUser nextRobotSysUser where nextRobotSysUser.email=?1";
        List<NextRobotSysUser> result = super.findByHQL(hql, NextRobotSysUser.class, email);
        if(result != null && result.size() == 1){
            return result.get(0);
        }
        return null;
    }

    @Override
    public NextRobotSysUser findByMobilePhoneNumber(String mobilePhoneNumber) {
        String hql = "select nextRobotSysUser from NextRobotSysUser nextRobotSysUser where nextRobotSysUser.mobilePhoneNumber = ?1";
        List<NextRobotSysUser> result = super.findByHQL(hql, NextRobotSysUser.class, mobilePhoneNumber);
        if(result != null && result.size() == 1){
            return result.get(0);
        }
        return null;
    }

    @Override
    public NextRobotSysUser findByUserName(String userName) {
        String hql = "select nextRobotSysUser from NextRobotSysUser nextRobotSysUser where nextRobotSysUser.userName = ?1";
        List<NextRobotSysUser> result = super.findByHQL(hql, NextRobotSysUser.class, userName);
        if(result != null && result.size() == 1){
            return result.get(0);
        }
        return null;
    }

    @Override
    public NextRobotSysUser login(String field, String username, String password) {
        StringBuilder hql = new StringBuilder("select nextRobotSysUser from NextRobotSysUser nextRobotSysUser where ");
        hql.append("nextRobotSysUser.").append(field).append(" = ?1 ");
        hql.append("and password = ?2 ");
        List<NextRobotSysUser> result = super.findByHQL(hql.toString(), NextRobotSysUser.class, username, password);
        if(result != null && result.size() == 1){
            return result.get(0);
        }
        return null;
    }
}
