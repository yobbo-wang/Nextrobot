package wang.yobbo.host.dao.Impl;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import wang.yobbo.common.appengine.entity.Searchable;
import wang.yobbo.common.appengine.dao.Impl.BaseDaoImpl;
import wang.yobbo.host.dao.NextRobotHostDao;
import wang.yobbo.host.entity.NextRobotHost;
import wang.yobbo.sys.entity.NextRobotBusinessTemplate;

import java.util.List;
/**
* 此文件由应用引擎生成，严禁修改，如重复生成时，该文件将被覆盖。
* NextRobotHostDaoImpl
* 主机信息数据访问实现类
* @author 应用引擎自动生成
* @date 2018-03-03 02:25:55
*
*/
@Component
public class NextRobotHostDaoImpl extends BaseDaoImpl<NextRobotHost, String> implements NextRobotHostDao{

    public NextRobotHost save(NextRobotHost nextRobotHost) {
        return super.save(nextRobotHost);
    }

    public NextRobotHost findById(String id) {
        return super.findById(id);
    }

    public int delete(String... ids) {
        return super.delete(ids);
    }

    public void delete(NextRobotHost nextRobotHost) {
        super.delete(nextRobotHost);
    }

    public Long count(Searchable searchable) {
        return super.count(searchable);
    }

    public long count() {
        return super.count();
    }

    public List<NextRobotHost> findPageWithoutCount(Searchable searchable) {
        return super.findPageWithoutCount(searchable);
    }

    public List<NextRobotHost> findAll() {
        return super.findAll();
    }

    public List<NextRobotHost> findAll(NextRobotHost nextRobotHost) {
        return super.findAll(nextRobotHost);
    }

    public Page<NextRobotHost> getPage(Searchable searchable) {
        return super.find(searchable);
    }

    public List<NextRobotBusinessTemplate> findTemplateByHql(String hql, Object[] params) {
        return super.findByHQL(hql, NextRobotBusinessTemplate.class, params);
    }

}