package wang.yobbo.sys.dao.Impl;

import org.springframework.stereotype.Component;
import wang.yobbo.common.appengine.dao.Impl.BaseDaoImpl;
import wang.yobbo.sys.dao.EntityPropertyDao;
import wang.yobbo.sys.entity.EntityProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaoyang on 2018/3/3.
 */
@Component
public class EntityPropertyDaoImpl extends BaseDaoImpl<EntityProperty, String> implements EntityPropertyDao {

    public List<EntityProperty> saveEntityProperty(List<EntityProperty> nextRobotEntityProperties) throws Exception {
        List<EntityProperty> newProperties = new ArrayList<EntityProperty>();
        for(EntityProperty nextRobotEntityProperty : nextRobotEntityProperties){
            newProperties.add(super.save(nextRobotEntityProperty));
        }
        return newProperties;
    }
}
