package wang.yobbo.iptables.dao.Impl;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import wang.yobbo.common.appengine.entity.Searchable;
import wang.yobbo.common.appengine.dao.Impl.BaseDaoImpl;
import wang.yobbo.iptables.dao.NextRobotIptablesDao;
import wang.yobbo.iptables.entity.NextRobotIptables;

import java.util.List;
/**
* 此文件由应用引擎生成，严禁修改，如重复生成时，该文件将被覆盖。
* NextRobotIptablesDaoImpl
* 仪表破傲顶顶顶数据访问实现类
* @author 应用引擎自动生成
* @date 2018-03-03 23:14:28
*
*/
@Component
public class NextRobotIptablesDaoImpl extends BaseDaoImpl<NextRobotIptables, String> implements NextRobotIptablesDao{

    public NextRobotIptables save(NextRobotIptables nextRobotIptables) {
        return super.save(nextRobotIptables);
    }

    public NextRobotIptables findById(String id) {
        return super.get(id);
    }

    public int delete(String... ids) {
        return super.delete(ids);
    }

    public void delete(NextRobotIptables nextRobotIptables) {
        super.delete(nextRobotIptables);
    }

    public Long count(Searchable searchable) {
        return super.count(searchable);
    }

    public long count() {
        return super.count();
    }

    public List<NextRobotIptables> findPageWithoutCount(Searchable searchable) {
        return super.findPageWithoutCount(searchable);
    }

    public List<NextRobotIptables> findAll() {
        return super.findAll();
    }

    public List<NextRobotIptables> findAll(NextRobotIptables nextRobotIptables) {
        return super.findAll(nextRobotIptables);
    }

    public Page<NextRobotIptables> getPage(Searchable searchable) {
        return super.find(searchable);
    }

}