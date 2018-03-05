package wang.yobbo.sys.dao;

import wang.yobbo.sys.entity.NextRobotBusinessTemplate;

public interface NextRobotBussisTemplateDao {
    NextRobotBusinessTemplate saveBusinessTemplate(NextRobotBusinessTemplate nextRobotBusinessTemplate);

    NextRobotBusinessTemplate findTemplate(String id);
}
