package wang.yobbo.sys.dao;

import wang.yobbo.sys.entity.EntityProperty;

import java.util.List;

/**
 * Created by xiaoyang on 2018/3/3.
 */
public interface EntityPropertyDao {
    List<EntityProperty> saveEntityProperty(List<EntityProperty> nextRobotEntityProperties) throws Exception;

    int delete(String[] id);

    List<EntityProperty> queryEntityPropertyByEntityId(String master_slave_type_id);
}
