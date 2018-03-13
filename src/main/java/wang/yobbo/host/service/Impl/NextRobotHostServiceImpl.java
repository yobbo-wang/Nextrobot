package wang.yobbo.host.service.Impl;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import wang.yobbo.common.appengine.entity.Searchable;
import wang.yobbo.host.dao.NextRobotHostDao;
import wang.yobbo.host.entity.Host;
import wang.yobbo.host.service.NextRobotHostService;
import wang.yobbo.sys.entity.BusinessTemplate;


import java.util.List;
import java.util.Map;

/**
* 此文件由应用引擎生成，严禁修改，如重复生成时，该文件将被覆盖。
* NextRobotHostServiceImpl
* 主机信息服务实现类
* @author 应用引擎自动生成
* @date 2018-03-03 02:25:55
*
*/
@Service("nextRobotHostService")
public class NextRobotHostServiceImpl implements NextRobotHostService{
    @Autowired
    private NextRobotHostDao nextRobotHostDao;

    public Host save(Host nextRobotHost) throws Exception {
        return this.nextRobotHostDao.save(nextRobotHost);
    }

    public Host findById(String id) {
        return this.nextRobotHostDao.findById(id);
    }

    public int delete(String... ids) {
        return this.nextRobotHostDao.delete(ids);
    }

    public void delete(Host nextRobotHost) {
        this.nextRobotHostDao.delete(nextRobotHost);
    }

    public Long count(Searchable searchable) {
        return this.nextRobotHostDao.count(searchable);
    }

    public long count() {
        return this.nextRobotHostDao.count();
    }

    public List<Host> findPageWithoutCount(Searchable searchable) {
        return this.nextRobotHostDao.findPageWithoutCount(searchable);
    }

    public List<Host> findAll() {
        return this.nextRobotHostDao.findAll();
    }

    public List<Host> findAll(Host nextRobotHost) {
        return this.nextRobotHostDao.findAll(nextRobotHost);
    }

    public Page<Host> getPage(Searchable searchable) {
        return this.nextRobotHostDao.getPage(searchable);
    }

    public List<BusinessTemplate> findTemplateByHql(String sql, Map<String, Object> params) {
        return this.nextRobotHostDao.findTemplateByHql(sql, params);
    }

}
