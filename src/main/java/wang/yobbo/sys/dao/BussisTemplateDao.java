package wang.yobbo.sys.dao;

import wang.yobbo.sys.entity.BusinessTemplate;

import java.util.List;

public interface BussisTemplateDao {
    BusinessTemplate saveBusinessTemplate(BusinessTemplate nextRobotBusinessTemplate) throws Exception;

    BusinessTemplate findTemplate(String id);

    List<BusinessTemplate> findTemplateAll();

    int deleteTemplate(String id);
}
