package wang.yobbo.sys.dao.Impl;

import org.springframework.stereotype.Component;
import wang.yobbo.common.appengine.dao.Impl.BaseDaoImpl;
import wang.yobbo.sys.dao.BussisTemplateDao;
import wang.yobbo.sys.entity.BusinessTemplate;

import java.util.List;

@Component
public class BussisTemplateDaoImpl extends BaseDaoImpl<BusinessTemplate, String> implements BussisTemplateDao {
    public BusinessTemplate saveBusinessTemplate(BusinessTemplate nextRobotBusinessTemplate) throws Exception{
        return super.save(nextRobotBusinessTemplate);
    }

    public BusinessTemplate findTemplate(String id) {
        return super.findById(id);
    }

    @Override
    public List<BusinessTemplate> findTemplateAll() {
        return super.findAll();
    }
}
