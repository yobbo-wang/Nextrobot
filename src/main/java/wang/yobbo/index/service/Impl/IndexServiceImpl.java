package wang.yobbo.index.service.Impl;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import wang.yobbo.common.appengine.entity.Searchable;
import wang.yobbo.index.dao.IndexDao;
import wang.yobbo.index.entity.Index;
import wang.yobbo.index.service.IndexService;


import java.util.List;

/**
* 此文件由应用引擎生成，严禁修改，如重复生成时，该文件将被覆盖。
* IndexServiceImpl
* 应用引擎第一张表服务实现类
* @author 应用引擎自动生成
* @date 2018-03-13 18:03:06
*
*/
@Service("indexService")
public class IndexServiceImpl implements IndexService{
    @Autowired
    private IndexDao indexDao;

    public Index save(Index index) {
        return this.indexDao.save(index);
    }

    public Index findById(String id) {
        return this.indexDao.findById(id);
    }

    public int delete(String... ids) {
        return this.indexDao.delete(ids);
    }

    public void delete(Index index) {
        this.indexDao.delete(index);
    }

    public Long count(Searchable searchable) {
        return this.indexDao.count(searchable);
    }

    public long count() {
        return this.indexDao.count();
    }

    public List<Index> findPageWithoutCount(Searchable searchable) {
        return this.indexDao.findPageWithoutCount(searchable);
    }

    public List<Index> findAll() {
        return this.indexDao.findAll();
    }

    public List<Index> findAll(Index index) {
        return this.indexDao.findAll(index);
    }

    public Page<Index> findPage(Searchable searchable) {
        return this.indexDao.findPage(searchable);
    }

}
