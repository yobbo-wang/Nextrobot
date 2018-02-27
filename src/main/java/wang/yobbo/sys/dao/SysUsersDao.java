package wang.yobbo.sys.dao;

import net.sf.ehcache.config.Searchable;
import wang.yobbo.sys.entity.NextRobotSysUsers;

import java.util.List;
import java.util.Map;

public interface SysUsersDao {
    /**
     * 根据自定义sql查询数据结果集
     * @param sql 自定sql
     * @param params 参数数组
     */
    Map findBySqlOne(String sql, Object...params);

    /**
     * 根据自定义sql查询数据结果集记录数
     * @param sql 自定sql
     * @param params 参数数组
     */
    int findBySqlCount(String sql, Object ...params);

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
    List<NextRobotSysUsers> findUserAll();

    /**
     * 根据实体值，获取所有结果集
     * @param user
     * @return
     */
    List<NextRobotSysUsers> findUserAll(NextRobotSysUsers user);

    /**
     * 根据主键ID批量删除
     * @param primaryKey
     * @return
     */
    int deleteByPrimaryKeys(String ... primaryKey);

    void deleteForSysUser(NextRobotSysUsers user);

    NextRobotSysUsers updateUser(NextRobotSysUsers user);

    NextRobotSysUsers save(NextRobotSysUsers user);
}
