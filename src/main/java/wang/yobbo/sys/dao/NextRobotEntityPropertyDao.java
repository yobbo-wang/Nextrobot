package wang.yobbo.sys.dao;

import wang.yobbo.sys.entity.NextRobotEntityProperty;

import java.util.List;

/**
 * Created by xiaoyang on 2018/3/3.
 */
public interface NextRobotEntityPropertyDao {
    boolean saveEntityProperty(List<NextRobotEntityProperty> nextRobotEntityProperties);
}
