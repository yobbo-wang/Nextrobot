package wang.yobbo.index.service;

import org.springframework.data.domain.Page;
import wang.yobbo.common.appengine.entity.Searchable;
import wang.yobbo.index.entity.Index;
import java.util.List;

/**
* 此文件由应用引擎生成，严禁修改，如重复生成时，该文件将被覆盖。
* IndexService
*dd服务接口类
* @author 应用引擎自动生成
* @date 2018-03-16 15:39:19
*
*/
public interface IndexService {

    /**功能：插入对象或更新对象
    * @param index
    * @return Index 返回 插入数据库后结果对象
    */
    Index save(Index index) throws Exception;

    /**功能：根据主键id查询对象
    * @param id 主键id
    * @return Index 返回 对象
    */
    Index findById(String id);

    /**
    * 功能：根据实体id，批量删除
    * @param ids 主键ID数组
    * @return int 返回 删除记录数
    */
    int delete(String ...ids);

    /**
    * 功能：根据实体对象删除
    * @param index 实体对象
    */
    void delete(Index index);

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
    * 功能：按分页、排序、自定义条件查询，返回不带分页信息的结果集
    * @param searchable 查询条件对象
    * @return List<Index> 返回 所有结果集
    */
    List<Index> findPageWithoutCount(Searchable searchable);

    /**
    * 功能：查询所有结果集
    * @return List<Index> 返回 所有结果集
    */
    List<Index> findAll();

    /**
    * 功能：查询所有结果集
    * @param index 实体对象
    * @return List<Index> 返回 所有结果集
    */
    List<Index> findAll(Index index);

    /**功能：分页、排序、自定义条件查询
    * @param searchable 查询条件对象
    * @return Page<Index> 返回带有分页结果集
    */
    Page<Index> findPage(Searchable searchable);

}