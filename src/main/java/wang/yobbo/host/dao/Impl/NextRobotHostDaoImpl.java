package wang.yobbo.host.dao.Impl;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import wang.yobbo.common.appengine.entity.Searchable;
import wang.yobbo.common.appengine.dao.Impl.BaseDaoImpl;
import wang.yobbo.host.dao.NextRobotHostDao;
import wang.yobbo.host.entity.Host;
import wang.yobbo.sys.entity.BusinessTemplate;

import java.util.List;
import java.util.Map;

/**
* 此文件由应用引擎生成，严禁修改，如重复生成时，该文件将被覆盖。
* NextRobotHostDaoImpl
* 主机信息数据访问实现类
* @author 应用引擎自动生成
* @date 2018-03-03 02:25:55
*
*/
@Component
public class NextRobotHostDaoImpl extends BaseDaoImpl<Host, String> implements NextRobotHostDao{

    public Host save(Host nextRobotHost) throws Exception {
        return super.save(nextRobotHost);
    }

    public Host findById(String id) {
        return super.findById(id);
    }

    public int delete(String... ids) {
        return super.delete(ids);
    }

    public void delete(Host nextRobotHost) {
        super.delete(nextRobotHost);
    }

    public Long count(Searchable searchable) {
        return super.count(searchable);
    }

    public long count() {
        return super.count();
    }

    public List<Host> findPageWithoutCount(Searchable searchable) {
        return super.findPageWithoutCount(searchable);
    }

    public List<Host> findAll() {
        return super.findAll();
    }

    public List<Host> findAll(Host nextRobotHost) {
        return super.findAll(nextRobotHost);
    }

    public Page<Host> getPage(Searchable searchable) {
        return super.findPage(searchable);
    }

    public List<BusinessTemplate> findTemplateByHql(String hql, Map<String, Object> params) {
        return super.findByHQL(hql,params, BusinessTemplate.class);
    }

}