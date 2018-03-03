package wang.yobbo.iptables.service.Impl;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import wang.yobbo.common.appengine.entity.Searchable;
import wang.yobbo.iptables.dao.NextRobotIptablesDao;
import wang.yobbo.iptables.entity.NextRobotIptables;
import wang.yobbo.iptables.service.NextRobotIptablesService;


import java.util.List;

/**
* 此文件由应用引擎生成，严禁修改，如重复生成时，该文件将被覆盖。
* NextRobotIptablesServiceImpl
* 仪表破傲顶顶顶服务实现类
* @author 应用引擎自动生成
* @date 2018-03-03 23:14:28
*
*/
@Service("nextRobotIptablesService")
public class NextRobotIptablesServiceImpl implements NextRobotIptablesService{
    @Autowired
    private NextRobotIptablesDao nextRobotIptablesDao;

    public NextRobotIptables save(NextRobotIptables nextRobotIptables) {
        return this.nextRobotIptablesDao.save(nextRobotIptables);
    }

    public NextRobotIptables findById(String id) {
        return this.nextRobotIptablesDao.findById(id);
    }

    public int delete(String... ids) {
        return this.nextRobotIptablesDao.delete(ids);
    }

    public void delete(NextRobotIptables nextRobotIptables) {
        this.nextRobotIptablesDao.delete(nextRobotIptables);
    }

    public Long count(Searchable searchable) {
        return this.nextRobotIptablesDao.count(searchable);
    }

    public long count() {
        return this.nextRobotIptablesDao.count();
    }

    public List<NextRobotIptables> findPageWithoutCount(Searchable searchable) {
        return this.nextRobotIptablesDao.findPageWithoutCount(searchable);
    }

    public List<NextRobotIptables> findAll() {
        return this.nextRobotIptablesDao.findAll();
    }

    public List<NextRobotIptables> findAll(NextRobotIptables nextRobotIptables) {
        return this.nextRobotIptablesDao.findAll(nextRobotIptables);
    }

    public Page<NextRobotIptables> getPage(Searchable searchable) {
        return this.nextRobotIptablesDao.getPage(searchable);
    }

}
