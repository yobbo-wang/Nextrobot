package wang.yobbo.sys.dao.Impl;

import net.sf.ehcache.config.Searchable;
import org.springframework.stereotype.Service;
import wang.yobbo.common.appengine.dao.Impl.BaseDaoImpl;
import wang.yobbo.sys.dao.SysUsersDao;
import wang.yobbo.sys.entity.SysUser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xiaoyang on 2017/12/28.
 *
 */
@Service
public class UsersDaoImpl extends BaseDaoImpl<SysUser, String> implements SysUsersDao {

    public SysUser findBySqlOne(String sql, Map<String, Object> params) {
        return super.findOneBySql(sql,  params, SysUser.class);
    }
    @Override
    public Long findBySqlCount(String sql, Object... params) {
        return super.findBySqlCount(sql, params);
    }

    public long getCount(Searchable searchable) {
        return 0;
    }

    public List<SysUser> findUserAll() {
        return super.findAll();
    }

    public List<SysUser> findUserAll(SysUser user){
        return super.findAll(user);
    }

    public int deleteByPrimaryKeys(String... primaryKey) {
        return super.delete(primaryKey);
    }

    public void deleteForSysUser(SysUser user) {
        super.delete(user);
    }

    public SysUser updateUser(SysUser user) throws Exception{
        return super.save(user) ;
    }

    public SysUser save(SysUser user) throws Exception{
        return super.save(user);
    }

    public List<SysUser> findAllBySql(String sql, Map<String, Object> params) {
        return super.findAllBySql(sql, params, SysUser.class);
    }

    @Override
    public SysUser findByEmail(String email) {
        String hql = "select sysUser from SysUser sysUser where sysUser.email= :email";
        Map<String, Object> params = new HashMap<>();
        params.put("email", email);
        SysUser result = super.findOneByHQL(hql,params, SysUser.class);
        return result;
    }

    @Override
    public SysUser findByMobilePhoneNumber(String mobilePhoneNumber) {
        String hql = "select sysUser from SysUser sysUser where sysUser.mobilePhoneNumber = :mobilePhoneNumber";
        Map<String, Object> params = new HashMap<>();
        params.put("mobilePhoneNumber", mobilePhoneNumber);
        SysUser result = super.findOneByHQL(hql, params, SysUser.class);
        return result;
    }

    @Override
    public SysUser findByUserName(String userName) {
        String hql = "select sysUser from SysUser sysUser where sysUser.userName = :userName";
        Map<String, Object> params = new HashMap<>();
        params.put("userName", userName);
        SysUser result = super.findOneByHQL(hql, params, SysUser.class);
        return result;
    }

    @Override
    public SysUser login(String field, String username, String password) {
        StringBuilder hql = new StringBuilder("select sysUser from SysUser sysUser where ");
        hql.append("sysUser.").append(field).append(" = ").append(":").append(field).append(" ");
        hql.append("and sysUser.password =:password ");
        Map<String, Object> params = new HashMap<>();
        params.put(field, username);
        params.put("password", password);
        SysUser result = super.findOneByHQL(hql.toString(), params, SysUser.class);
        return result;
    }
}
