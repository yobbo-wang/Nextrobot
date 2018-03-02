package wang.yobbo.sys.service.Impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import wang.yobbo.common.appengine.entity.Searchable;
import wang.yobbo.common.httpengine.http.EngineViewServlet;
import wang.yobbo.common.spring.PropertyConfigurer;
import wang.yobbo.sys.dao.SysMenuDao;
import wang.yobbo.sys.dao.SysMenuTableDao;
import wang.yobbo.sys.entity.NextRobotSysMenu;
import wang.yobbo.sys.entity.NextRobotSysMenuTable;
import wang.yobbo.sys.service.SysMenuService;

import java.io.IOException;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class SysMenuServiceImpl implements SysMenuService {
    @Autowired private SysMenuDao sysMenuDao;
    @Autowired private SysMenuTableDao sysMenuTableDao;
    @Autowired private PropertyConfigurer propertyConfigurer;

    public Page<NextRobotSysMenu> getPage(Searchable searchable) {
        return this.sysMenuDao.getPage(searchable);
    }

    public Long getCount(Searchable searchable) {
        return this.sysMenuDao.getCount(searchable);
    }

    //使用懒加载
    public List<NextRobotSysMenu> findByPId(String pid) {
        List<NextRobotSysMenu> menus = this.sysMenuDao.findByPId(pid);
        return menus;
    }

    public NextRobotSysMenu findById(String id) {
        return this.sysMenuDao.findById(id);
    }

    public NextRobotSysMenu save(NextRobotSysMenu sysMenu) {
        return this.sysMenuDao.save(sysMenu);
    }

    public int delete(String id) {
        return this.sysMenuDao.delete(id);
    }

    public NextRobotSysMenuTable addEntity(NextRobotSysMenuTable sysMenuTable) {
        return this.sysMenuTableDao.addEntity(sysMenuTable);
    }

    public int deleteEntity(String id) {
        return this.sysMenuTableDao.deleteEntity(id);
    }

    public NextRobotSysMenuTable findSysMenuTableById(String id) {
        return this.sysMenuTableDao.findSysMenuTableById(id);
    }

    //首字母转小写
    private String toLowerCaseFirstOne(String s){
        if(Character.isLowerCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
    }


    //首字母转大写
    private String toUpperCaseFirstOne(String s){
        if(Character.isUpperCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
    }

    public boolean createBusinessCode(NextRobotSysMenuTable nextRobotSysMenuTable,String entityMode,String entityRow) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        try {
            this.sysMenuTableDao.addEntity(nextRobotSysMenuTable); //保存
            //业务分类首字母设置为小写
            nextRobotSysMenuTable.setBusinessClassification(this.toLowerCaseFirstOne(nextRobotSysMenuTable.getBusinessClassification()));
            List<String> entityModeBean = mapper.readValue(entityMode, new TypeReference<List<String>>() {});
            //获取应用引擎信息
            String basePath = EngineViewServlet.getBase_path();
            String packageName = EngineViewServlet.getPackage_name();
            packageName = packageName.replaceAll("\\.", "/");
            String basePathPrefix = basePath + "/src/main/java/" + packageName;

            List<Map<String, String>> files = new ArrayList<Map<String, String>>();
            for(String mode : entityModeBean){
                if("entity".equals(mode)){
                    Map entity = this.createEntity(basePathPrefix, nextRobotSysMenuTable, entityRow);
                    files.add(entity);
                }else if("service".equals(mode)){
                    List<Map<String, String>> service = this.createService(basePathPrefix, nextRobotSysMenuTable);
                    files.add(service.get(0));
                    files.add(service.get(1));
                }else if("dao".equals(mode)){
                    List<Map<String, String>> dao = this.createDao(basePathPrefix, nextRobotSysMenuTable);
                    files.add(dao.get(0));
                    files.add(dao.get(1));
                }else{
                    //直接是模板路径情况....
                }
            }

            //遍历模板文件，写入到指定文件中
            for(Map<String, String> file : files){
                System.out.println(file);
            }

        } catch (IOException e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
        return true;
    }

    //生成entity
    public Map createEntity(String path, NextRobotSysMenuTable nextRobotSysMenuTable, String entityRow) throws Exception{
        Map<String,Object> dataMap = new HashMap<String, Object>();
        dataMap.put("nowDate", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        Map<String,Object> engine = new HashMap<String, Object>();
        engine.put("entityName", nextRobotSysMenuTable.getEntityName());
        engine.put("businessClassification", nextRobotSysMenuTable.getBusinessClassification());
        engine.put("tableName", nextRobotSysMenuTable.getTableName());
        engine.put("remark", nextRobotSysMenuTable.getRemark());
        engine.put("packageName", propertyConfigurer.getProperty("system.package.name"));
        String templateEntityPath = "ame_entity.ftl";
        String entityPath = path + "/" + nextRobotSysMenuTable.getBusinessClassification() + "/entity/" + nextRobotSysMenuTable.getEntityName() + ".java";
        ObjectMapper mapper = new ObjectMapper();
        List<Map> entitys = mapper.readValue(entityRow, new TypeReference<List<Map>>(){});
        dataMap.put("engine", engine);
        dataMap.put("fieldList", entitys);
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_21);
        configuration.setDefaultEncoding( "UTF-8" );
        configuration.setClassForTemplateLoading(this.getClass(), "/monitor/codeEngine");
        Template template = configuration.getTemplate(templateEntityPath);
        StringWriter stringWriter = new StringWriter();// 本应用无需生成文件，直接输出即可
        template.process(dataMap, stringWriter);
        Map<String,Object> fileInfo = new HashMap<String, Object>();
        fileInfo.put("entityFilePath",entityPath);
        fileInfo.put("entityFileContent", stringWriter.toString());
        return fileInfo;
    }

    //生成dao
    private List<Map<String, String>> createDao(String path, NextRobotSysMenuTable nextRobotSysMenuTable) throws Exception{

        Map<String,Object> dataMap = new HashMap<String, Object>();
        dataMap.put("nowDate", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        Map<String,Object> engine = new HashMap<String, Object>();
        engine.put("entityName", nextRobotSysMenuTable.getEntityName());
        engine.put("businessClassification", nextRobotSysMenuTable.getBusinessClassification());
        engine.put("remark", nextRobotSysMenuTable.getRemark());
        engine.put("packageName", propertyConfigurer.getProperty("system.package.name"));
        dataMap.put("engine", engine);
        String templateDaoPath = "ame_dao.ftl";
        String templateDaoImplPath = "ame_daoImpl.ftl";
        String daoPath = path + "/"+ nextRobotSysMenuTable.getBusinessClassification() + "/dao/" + nextRobotSysMenuTable.getEntityName() + "Dao.java";
        String daoImplPath = path + "/"+ nextRobotSysMenuTable.getBusinessClassification() + "/dao/Impl/" + nextRobotSysMenuTable.getEntityName() + "DaoImpl.java";

        Configuration configuration = new Configuration(Configuration.VERSION_2_3_21);
        configuration.setDefaultEncoding( "UTF-8" );
        configuration.setClassForTemplateLoading(this.getClass(), "/monitor/codeEngine");
        Template templateDao = configuration.getTemplate(templateDaoPath);
        StringWriter stringWriterDao = new StringWriter();// 本应用无需生成文件，直接输出即可
        templateDao.process(dataMap, stringWriterDao);

        Template templateDaoImpl = configuration.getTemplate(templateDaoImplPath);
        StringWriter stringWriterDaoImpl = new StringWriter();// 本应用无需生成文件，直接输出即可
        templateDaoImpl.process(dataMap, stringWriterDaoImpl);

        List<Map<String, String>> filesInfo = new ArrayList<Map<String, String>>();
        Map<String, String> file = new HashMap<String, String>();
        file.put("daoFilePath", daoPath);
        file.put("daoFileContent", stringWriterDao.toString());
        filesInfo.add(0, file);

        Map<String, String> fileImpl = new HashMap<String, String>();
        file.put("daoImplFilePath", daoImplPath);
        file.put("daoImplFileContent", stringWriterDaoImpl.toString());
        filesInfo.add(1, fileImpl);

        return filesInfo;
    }

    //生成service
    private List<Map<String, String>> createService(String path, NextRobotSysMenuTable nextRobotSysMenuTable) throws Exception{
        List<Map<String, String>> filesInfo = new ArrayList<Map<String, String>>();
        Map<String,Object> dataMap = new HashMap<String, Object>();
        dataMap.put("nowDate", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        Map<String,Object> engine = new HashMap<String, Object>();
        engine.put("entityName", nextRobotSysMenuTable.getEntityName());
        engine.put("businessClassification", nextRobotSysMenuTable.getBusinessClassification());
        engine.put("remark", nextRobotSysMenuTable.getRemark());
        engine.put("packageName", propertyConfigurer.getProperty("system.package.name"));
        dataMap.put("engine", engine);

        String templateServicePath = "ame_service.ftl";
        String templateServiceImplPath = "ame_serviceImpl.ftl";
        String servicePath = path + "/"+ nextRobotSysMenuTable.getBusinessClassification() + "/service/" + nextRobotSysMenuTable.getEntityName() + "Service.java";
        String serviceImplPath = path + "/"+ nextRobotSysMenuTable.getBusinessClassification() + "/service/Impl/" + nextRobotSysMenuTable.getEntityName() + "ServiceImpl.java";

        Configuration configuration = new Configuration(Configuration.VERSION_2_3_21);
        configuration.setDefaultEncoding( "UTF-8" );
        configuration.setClassForTemplateLoading(this.getClass(), "/monitor/codeEngine");
        Template templateService = configuration.getTemplate(templateServicePath);
        StringWriter stringWriterService = new StringWriter();// 本应用无需生成文件，直接输出即可
        templateService.process(dataMap, stringWriterService);

        Template templateServiceImpl = configuration.getTemplate(templateServiceImplPath);
        StringWriter stringWriterServiceImpl = new StringWriter();// 本应用无需生成文件，直接输出即可
        templateServiceImpl.process(dataMap, stringWriterServiceImpl);

        Map<String, String> file = new HashMap<String, String>();
        file.put("serviceFilePath", servicePath);
        file.put("serviceFileContent", stringWriterService.toString());
        filesInfo.add(file);

        Map<String, String> fileImpl = new HashMap<String, String>();
        file.put("serviceImplFilePath", serviceImplPath);
        file.put("serviceImplFileContent", stringWriterServiceImpl.toString());
        filesInfo.add(fileImpl);

        return filesInfo;
    }

}
