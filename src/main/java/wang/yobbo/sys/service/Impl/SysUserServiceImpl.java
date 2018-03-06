package wang.yobbo.sys.service.Impl;

import net.sf.ehcache.config.Searchable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import wang.yobbo.common.appengine.InvokeResult;
import wang.yobbo.sys.dao.SysUsersDao;
import wang.yobbo.sys.entity.NextRobotSysUser;
import wang.yobbo.sys.service.SysUserService;

import java.util.List;
import java.util.regex.Pattern;

@Service
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUsersDao usersDao;

    public Page<NextRobotSysUser> getPage(wang.yobbo.common.appengine.entity.Searchable searchable) {
        return null;
    }

    public NextRobotSysUser findBySqlOne(String sql, Object... params) {
        return this.usersDao.findBySqlOne(sql, params);
    }

    public List<NextRobotSysUser> findAllBySql(String sql, Object... params) {
        return this.usersDao.findAllBySql(sql, params);
    }

    public Long findBySqlCount(String sql, Object... params) {
        return this.usersDao.findBySqlCount(sql, params);
    }

    public long getCount(wang.yobbo.common.appengine.entity.Searchable searchable) {
        return 0;
    }

    public long getCount(Searchable searchable) {
        return this.usersDao.getCount(searchable);
    }

    public List<NextRobotSysUser> findUserAll() {
        return this.usersDao.findUserAll();
    }

    public List<NextRobotSysUser> findUserAll(NextRobotSysUser user) {
        return this.usersDao.findUserAll(user);
    }

    public int deleteByPrimaryKeys(String... primaryKey) {
        return this.usersDao.deleteByPrimaryKeys(primaryKey);
    }

    public void deleteForUser(NextRobotSysUser SysUser) {
    }

    public NextRobotSysUser update(NextRobotSysUser user) {
        return this.usersDao.updateUser(user);
    }

    public NextRobotSysUser save(NextRobotSysUser user) {
        return this.usersDao.save(user);
    }

    @Override
    public NextRobotSysUser findByEmail(String email) {
        return this.usersDao.findByEmail(email);
    }

    @Override
    public NextRobotSysUser findByMobilePhoneNumber(String mobilePhone) {
        return this.usersDao.findByMobilePhoneNumber(mobilePhone);
    }

    @Override
    public NextRobotSysUser findByUserName(String userName) {
        return this.usersDao.findByUserName(userName);
    }

    @Override
    public NextRobotSysUser login(String username, String password) {
        final String EMAIL_PATTERN = "^((([a-z]|\\d|[!#\\$%&'\\*\\+\\-\\/=\\?\\^_`{\\|}~]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])+(\\.([a-z]|\\d|[!#\\$%&'\\*\\+\\-\\/=\\?\\^_`{\\|}~]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])+)*)|((\\x22)((((\\x20|\\x09)*(\\x0d\\x0a))?(\\x20|\\x09)+)?(([\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x7f]|\\x21|[\\x23-\\x5b]|[\\x5d-\\x7e]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(\\\\([\\x01-\\x09\\x0b\\x0c\\x0d-\\x7f]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF]))))*(((\\x20|\\x09)*(\\x0d\\x0a))?(\\x20|\\x09)+)?(\\x22)))@((([a-z]|\\d|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(([a-z]|\\d|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])([a-z]|\\d|-|\\.|_|~|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])*([a-z]|\\d|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])))\\.)+(([a-z]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(([a-z]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])([a-z]|\\d|-|\\.|_|~|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])*([a-z]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])))\\.?";
        final String MOBILE_PHONE_NUMBER_PATTERN = "^0{0,1}(13[0-9]|15[0-9]|14[0-9]|18[0-9])[0-9]{8}$";
        String field = new String();
        if(Pattern.matches(EMAIL_PATTERN, username)){ //用邮箱查找用户
            field = "email";
        }else if(Pattern.matches(MOBILE_PHONE_NUMBER_PATTERN, username)){ //用手机查找用户
            field = "mobilePhoneNumber";
        }else{ //用用户查找用户
            field = "userName";
        }
        return this.usersDao.login(field ,username, password);
    }
}
