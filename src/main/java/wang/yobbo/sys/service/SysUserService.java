package wang.yobbo.sys.service;

import org.springframework.data.domain.Page;
import wang.yobbo.common.appengine.entity.Searchable;
import wang.yobbo.sys.entity.NextRobotSysUser;

import java.util.List;

public interface SysUserService{

    Page<NextRobotSysUser> getPage(Searchable searchable);
    /**
     * 根据自定义sql查询数据结果集
     * @param sql 自定sql
     * @param params 参数数组
     */
    NextRobotSysUser findBySqlOne(String sql, Object...params);

    /**
     * 根据自定义sql查询数据结果集
     * @param sql
     * @param params
     * @return
     */
    List<NextRobotSysUser> findAllBySql(String sql, Object...params);

    /**
     * 根据自定义sql查询数据结果集记录数
     * @param sql 自定sql
     * @param params 参数数组
     */
    Long findBySqlCount(String sql, Object ...params);

    /**
     * 获取结果集个数
     * @param searchable
     * @return
     */
    long getCount(Searchable searchable);

    /**
     * 获取所有实体所有结果集
     * @return
     */
    List<NextRobotSysUser> findUserAll();

    /**
     * 根据实体值，获取所有结果集
     * @param SysUser
     * @return
     */
    List<NextRobotSysUser> findUserAll(NextRobotSysUser SysUser);

    /**
     * 根据主键ID批量删除
     * @param primaryKey
     * @return
     */
    int deleteByPrimaryKeys(String ... primaryKey);

    void deleteForUser(NextRobotSysUser SysUser);

    NextRobotSysUser update(NextRobotSysUser SysUser);

    NextRobotSysUser save(NextRobotSysUser SysUser);

    NextRobotSysUser findByEmail(String email);

    NextRobotSysUser findByMobilePhoneNumber(String mobilePhone);

    NextRobotSysUser findByUserName(String userName);

    NextRobotSysUser login(String username, String password);
}
