package wang.yobbo.sys.service;

import org.springframework.data.domain.Page;
import wang.yobbo.common.entity.Searchable;
import wang.yobbo.sys.entity.NextRobotSysUsers;

import java.util.List;

public interface SysUserService{

    Page<NextRobotSysUsers> getPage(Searchable searchable);
    /**
     * 根据自定义sql查询数据结果集
     * @param sql 自定sql
     * @param params 参数数组
     */
    NextRobotSysUsers findBySqlOne(String sql, Object...params);

    /**
     * 根据自定义sql查询数据结果集
     * @param sql
     * @param params
     * @return
     */
    List<NextRobotSysUsers> findAllBySql(String sql, Object...params);

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
    List<NextRobotSysUsers> findUserAll();

    /**
     * 根据实体值，获取所有结果集
     * @param SysUser
     * @return
     */
    List<NextRobotSysUsers> findUserAll(NextRobotSysUsers SysUser);

    /**
     * 根据主键ID批量删除
     * @param primaryKey
     * @return
     */
    int deleteByPrimaryKeys(String ... primaryKey);

    void deleteForUser(NextRobotSysUsers SysUser);

    NextRobotSysUsers update(NextRobotSysUsers SysUser);

    NextRobotSysUsers save(NextRobotSysUsers SysUser);
}
