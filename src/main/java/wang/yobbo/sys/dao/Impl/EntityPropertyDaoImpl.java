package wang.yobbo.sys.dao.Impl;

import org.springframework.stereotype.Component;
import wang.yobbo.common.appengine.dao.Impl.BaseDaoImpl;
import wang.yobbo.sys.dao.EntityPropertyDao;
import wang.yobbo.sys.entity.EntityProperty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Override
    public int delete(String ... id) {
        return super.delete(id);
    }

    @Override
    public List<EntityProperty> queryEntityPropertyByEntityId(String master_slave_type_id) {
        String hql = "select entityProperty from EntityProperty entityProperty where entityProperty.entity_id = :entity_id";
        Map param = new HashMap<>();
        param.put("entity_id", master_slave_type_id);
        return super.findByHQL(hql, param, EntityProperty.class);
    }
}
