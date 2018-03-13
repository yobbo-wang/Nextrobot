package wang.yobbo.index.dao.Impl;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import wang.yobbo.common.appengine.entity.Searchable;
import wang.yobbo.common.appengine.dao.Impl.BaseDaoImpl;
import wang.yobbo.index.dao.IndexDao;
import wang.yobbo.index.entity.Index;

import java.util.List;
/**
* 此文件由应用引擎生成，严禁修改，如重复生成时，该文件将被覆盖。
* IndexDaoImpl
* 应用引擎第一张表数据访问实现类
* @author 应用引擎自动生成
* @date 2018-03-13 18:03:06
*
*/
@Component
public class IndexDaoImpl extends BaseDaoImpl<Index, String> implements IndexDao{

    public Index save(Index index) {
        return super.save(index);
    }

    public Index findById(String id) {
        return super.get(id);
    }

    public int delete(String... ids) {
        return super.delete(ids);
    }

    public void delete(Index index) {
        super.delete(index);
    }

    public Long count(Searchable searchable) {
        return super.count(searchable);
    }

    public long count() {
        return super.count();
    }

    public List<Index> findPageWithoutCount(Searchable searchable) {
        return super.findPageWithoutCount(searchable);
    }

    public List<Index> findAll() {
        return super.findAll();
    }

    public List<Index> findAll(Index index) {
        return super.findAll(index);
    }

    public Page<Index> findPage(Searchable searchable) {
        return super.findPage(searchable);
    }

}