package wang.yobbo.host.service;

import org.springframework.data.domain.Page;
import wang.yobbo.common.appengine.entity.Searchable;
import wang.yobbo.host.entity.NextRobotHost;
import wang.yobbo.sys.entity.NextRobotBusinessTemplate;

import java.util.List;

/**
* 此文件由应用引擎生成，严禁修改，如重复生成时，该文件将被覆盖。
* NextRobotHostService
*主机信息服务接口类
* @author 应用引擎自动生成
* @date 2018-03-03 02:25:55
*
*/

public interface NextRobotHostService {

    /**功能：插入对象或更新对象
    * @param nextRobotHost
    * @return NextRobotHost 返回 插入数据库后结果对象
    */
    NextRobotHost save(NextRobotHost nextRobotHost);

    /**功能：根据主键id查询对象
    * @param id 主键id
    * @return NextRobotHost 返回 对象
    */
    NextRobotHost findById(String id);

    /**
    * 功能：根据实体id，批量删除
    * @param ids 主键ID数组
    * @return int 返回 删除记录数
    */
    int delete(String ...ids);

    /**
    * 功能：根据实体对象删除
    * @param nextRobotHost 实体对象
    */
    void delete(NextRobotHost nextRobotHost);

    /**
    * 功能：分页、排序、自定义条件查询结果集记录数
    * @param searchable 查询条件对象
    * @return Long 返回 结果集记录数
    */
    Long count(Searchable searchable);

    /**
    * 功能：查询对象所有记录数
    * @return Long 返回 结果集记录数
    */
    long count();

    /**
    * 功能：按分页、排序、自定义条件查询，返回不带count结果集
    * @param searchable 查询条件对象
    * @return List<NextRobotHost> 返回 所有结果集
    */
    List<NextRobotHost> findPageWithoutCount(Searchable searchable);

    /**
    * 功能：查询所有结果集
    * @return List<NextRobotHost> 返回 所有结果集
    */
    List<NextRobotHost> findAll();

    /**
    * 功能：查询所有结果集
    * @param nextRobotHost 实体对象
    * @return List<NextRobotHost> 返回 所有结果集
    */
    List<NextRobotHost> findAll(NextRobotHost nextRobotHost);

    /**功能：分页、排序、自定义条件查询
    * @param searchable 查询条件对象
    * @return Page<NextRobotHost> 返回带有分页结果集
    */
    Page<NextRobotHost> getPage(Searchable searchable);

    List<NextRobotBusinessTemplate> findTemplateByHql(String sql, Object ... params);

}