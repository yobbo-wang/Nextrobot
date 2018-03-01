package wang.yobbo.sys.service.Impl;

import net.sf.ehcache.config.Searchable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import wang.yobbo.sys.dao.SysUsersDao;
import wang.yobbo.sys.entity.NextRobotSysUsers;
import wang.yobbo.sys.service.SysUserService;

import java.util.List;

@Service
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUsersDao usersDao;

    public Page<NextRobotSysUsers> getPage(wang.yobbo.common.entity.Searchable searchable) {
        return null;
    }

    public NextRobotSysUsers findBySqlOne(String sql, Object... params) {
        return this.usersDao.findBySqlOne(sql, params);
    }

    public List<NextRobotSysUsers> findAllBySql(String sql, Object... params) {
        return this.usersDao.findAllBySql(sql, params);
    }

    public Long findBySqlCount(String sql, Object... params) {
        return this.usersDao.findBySqlCount(sql, params);
    }

    public long getCount(wang.yobbo.common.entity.Searchable searchable) {
        return 0;
    }

    public long getCount(Searchable searchable) {
        return this.usersDao.getCount(searchable);
    }

    public List<NextRobotSysUsers> findUserAll() {
        return this.usersDao.findUserAll();
    }

    public List<NextRobotSysUsers> findUserAll(NextRobotSysUsers user) {
        return this.usersDao.findUserAll(user);
    }

    public int deleteByPrimaryKeys(String... primaryKey) {
        return this.usersDao.deleteByPrimaryKeys(primaryKey);
    }

    public void deleteForUser(NextRobotSysUsers SysUser) {

    }

    public NextRobotSysUsers update(NextRobotSysUsers user) {
        return this.usersDao.updateUser(user);
    }

    public NextRobotSysUsers save(NextRobotSysUsers user) {
        return this.usersDao.save(user);
    }
}
