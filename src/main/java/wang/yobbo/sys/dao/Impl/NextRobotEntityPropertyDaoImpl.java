package wang.yobbo.sys.dao.Impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import wang.yobbo.common.appengine.dao.Impl.BaseDaoImpl;
import wang.yobbo.sys.dao.NextRobotEntityPropertyDao;
import wang.yobbo.sys.entity.NextRobotEntityProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaoyang on 2018/3/3.
 */
@Component
public class NextRobotEntityPropertyDaoImpl extends BaseDaoImpl<NextRobotEntityProperty, String> implements NextRobotEntityPropertyDao {

    public List<NextRobotEntityProperty> saveEntityProperty(List<NextRobotEntityProperty> nextRobotEntityProperties) {
        List<NextRobotEntityProperty> newProperties = new ArrayList<NextRobotEntityProperty>();
        for(NextRobotEntityProperty nextRobotEntityProperty : nextRobotEntityProperties){
            newProperties.add(super.save(nextRobotEntityProperty));
        }
        return newProperties;
    }
}
