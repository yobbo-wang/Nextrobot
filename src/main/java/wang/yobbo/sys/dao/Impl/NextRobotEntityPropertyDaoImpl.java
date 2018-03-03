package wang.yobbo.sys.dao.Impl;

import org.springframework.stereotype.Component;
import wang.yobbo.common.appengine.dao.Impl.BaseDaoImpl;
import wang.yobbo.sys.dao.NextRobotEntityPropertyDao;
import wang.yobbo.sys.entity.NextRobotEntityProperty;

import java.util.List;

/**
 * Created by xiaoyang on 2018/3/3.
 */
@Component
public class NextRobotEntityPropertyDaoImpl extends BaseDaoImpl<NextRobotEntityProperty, String> implements NextRobotEntityPropertyDao {

    public boolean saveEntityProperty(List<NextRobotEntityProperty> nextRobotEntityProperties) {
        for(NextRobotEntityProperty nextRobotEntityProperty : nextRobotEntityProperties){
            super.save(nextRobotEntityProperty);
        }
        return true;
    }
}
