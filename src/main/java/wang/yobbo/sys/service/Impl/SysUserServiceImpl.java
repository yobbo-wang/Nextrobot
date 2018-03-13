package wang.yobbo.sys.service.Impl;

import net.sf.ehcache.config.Searchable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import wang.yobbo.sys.dao.SysUsersDao;
import wang.yobbo.sys.entity.SysUser;
import wang.yobbo.sys.service.SysUserService;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Service
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUsersDao usersDao;

    public Page<SysUser> getPage(wang.yobbo.common.appengine.entity.Searchable searchable) {
        return null;
    }

    public SysUser findBySqlOne(String sql, Map<String, Object> params) {
        return this.usersDao.findBySqlOne(sql, params);
    }

    public List<SysUser> findAllBySql(String sql, Map<String, Object> params) {
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

    public List<SysUser> findUserAll() {
        return this.usersDao.findUserAll();
    }

    public List<SysUser> findUserAll(SysUser user) {
        return this.usersDao.findUserAll(user);
    }

    public int deleteByPrimaryKeys(String... primaryKey) {
        return this.usersDao.deleteByPrimaryKeys(primaryKey);
    }

    public void deleteForUser(SysUser SysUser) {
    }

    public SysUser update(SysUser user) throws Exception {
        return this.usersDao.updateUser(user) ;
    }

    public SysUser save(SysUser user) throws Exception {
        return this.usersDao.save(user);
    }

    @Override
    public SysUser findByEmail(String email) {
        return this.usersDao.findByEmail(email);
    }

    @Override
    public SysUser findByMobilePhoneNumber(String mobilePhone) {
        return this.usersDao.findByMobilePhoneNumber(mobilePhone);
    }

    @Override
    public SysUser findByUserName(String userName) {
        return this.usersDao.findByUserName(userName);
    }

    @Override
    public SysUser login(String username, String password) {
        final String USERNAME_PATTERN = "^[\\u4E00-\\u9FA5\\uf900-\\ufa2d_a-zA-Z][\\u4E00-\\u9FA5\\uf900-\\ufa2d\\w]{1,19}$";
        final String EMAIL_PATTERN = "^((([a-z]|\\d|[!#\\$%&'\\*\\+\\-\\/=\\?\\^_`{\\|}~]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])+(\\.([a-z]|\\d|[!#\\$%&'\\*\\+\\-\\/=\\?\\^_`{\\|}~]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])+)*)|((\\x22)((((\\x20|\\x09)*(\\x0d\\x0a))?(\\x20|\\x09)+)?(([\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x7f]|\\x21|[\\x23-\\x5b]|[\\x5d-\\x7e]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(\\\\([\\x01-\\x09\\x0b\\x0c\\x0d-\\x7f]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF]))))*(((\\x20|\\x09)*(\\x0d\\x0a))?(\\x20|\\x09)+)?(\\x22)))@((([a-z]|\\d|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(([a-z]|\\d|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])([a-z]|\\d|-|\\.|_|~|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])*([a-z]|\\d|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])))\\.)+(([a-z]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(([a-z]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])([a-z]|\\d|-|\\.|_|~|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])*([a-z]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])))\\.?";
        final String MOBILE_PHONE_NUMBER_PATTERN = "^0{0,1}(13[0-9]|15[0-9]|14[0-9]|18[0-9]|17[0-9])[0-9]{8}$";
        String field = new String();
        if(Pattern.matches(EMAIL_PATTERN, username)){ //用邮箱查找用户
            field = "email";
        }else if(Pattern.matches(MOBILE_PHONE_NUMBER_PATTERN, username)){ //用手机查找用户
            field = "mobilePhoneNumber";
        }else if(Pattern.matches(USERNAME_PATTERN, username)){ //用账号
            field = "userName";
        }else{
            return null;
        }
        return this.usersDao.login(field ,username, password);
    }
}
