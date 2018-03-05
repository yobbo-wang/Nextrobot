package wang.yobbo.sys.dao.Impl;

import org.springframework.stereotype.Component;
import wang.yobbo.common.appengine.dao.Impl.BaseDaoImpl;
import wang.yobbo.sys.dao.NextRobotBussisTemplateDao;
import wang.yobbo.sys.entity.NextRobotBusinessTemplate;

@Component
public class NextRobotBussisTemplateDaoImpl extends BaseDaoImpl<NextRobotBusinessTemplate, String> implements NextRobotBussisTemplateDao {
    public NextRobotBusinessTemplate saveBusinessTemplate(NextRobotBusinessTemplate nextRobotBusinessTemplate) {
        return super.save(nextRobotBusinessTemplate);
    }

    public NextRobotBusinessTemplate findTemplate(String id) {
        return super.findById(id);
    }
}
