package wang.yobbo.sys.service.Impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import wang.yobbo.common.appengine.entity.Searchable;
import wang.yobbo.common.httpengine.http.EngineViewServlet;
import wang.yobbo.common.spring.PropertyConfigurer;
import wang.yobbo.sys.dao.NextRobotBussisTemplateDao;
import wang.yobbo.sys.dao.NextRobotEntityPropertyDao;
import wang.yobbo.sys.dao.NextRobotSysMenuDao;
import wang.yobbo.sys.dao.NextRobotSysMenuTableDao;
import wang.yobbo.sys.entity.NextRobotBusinessTemplate;
import wang.yobbo.sys.entity.NextRobotEntityProperty;
import wang.yobbo.sys.entity.NextRobotSysMenu;
import wang.yobbo.sys.entity.NextRobotSysMenuEntity;
import wang.yobbo.sys.service.NextRobotSysMenuService;

import java.io.*;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class NextRobotSysMenuServiceImpl implements NextRobotSysMenuService {
    private static final Logger LOG = LoggerFactory.getLogger(NextRobotSysMenuServiceImpl.class);
    @Autowired private NextRobotSysMenuDao sysMenuDao;
    @Autowired private NextRobotSysMenuTableDao sysMenuTableDao;
    @Autowired private PropertyConfigurer propertyConfigurer;
    @Autowired private NextRobotBussisTemplateDao nextRobotBussisTemplateDao;
    @Autowired private NextRobotEntityPropertyDao nextRobotEntityPropertyDao;

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

    public NextRobotSysMenuEntity addEntity(NextRobotSysMenuEntity nextRobotSysMenuEntity) {
        return this.sysMenuTableDao.addEntity(nextRobotSysMenuEntity);
    }

    public int deleteEntity(String id) {
        return this.sysMenuTableDao.deleteEntity(id);
    }

    public NextRobotSysMenuEntity findSysMenuTableById(String id) {
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

    public boolean createBusinessCode(NextRobotSysMenuEntity nextRobotSysMenuTable, String entityMode, List<NextRobotEntityProperty> nextRobotEntityProperties) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        try {
            this.sysMenuTableDao.addEntity(nextRobotSysMenuTable); //保存
            this.nextRobotEntityPropertyDao.saveEntityProperty(nextRobotEntityProperties);
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
                    Map entity = this.createEntity(basePathPrefix, nextRobotSysMenuTable, nextRobotEntityProperties);
                    files.add(entity);
                }else if("service".equals(mode)){
                    List<Map<String, String>> service = this.createService(basePathPrefix, nextRobotSysMenuTable);
                    files.addAll(service);
                }else if("dao".equals(mode)){
                    List<Map<String, String>> dao = this.createDao(basePathPrefix, nextRobotSysMenuTable);
                    files.addAll(dao);
                }else{
                    //直接是模板路径情况....
                }
            }

            //遍历模板文件，写入到指定文件中
            for(Map<String, String> file : files){
                this.createCodeFile(file.get("packagePath") ,file.get("filePath"), file.get("fileContent"));
            }

        } catch (IOException e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
        return true;
    }

    public List<NextRobotEntityProperty> saveEntityProperty(List<NextRobotEntityProperty> nextRobotEntityProperties) {
        return this.nextRobotEntityPropertyDao.saveEntityProperty(nextRobotEntityProperties);
    }

    public NextRobotBusinessTemplate saveBusinessTemplate(NextRobotBusinessTemplate nextRobotBusinessTemplate) {
        return this.nextRobotBussisTemplateDao.saveBusinessTemplate(nextRobotBusinessTemplate);
    }

    public NextRobotBusinessTemplate findTemplate(String id) {
        return this.nextRobotBussisTemplateDao.findTemplate(id);
    }

    //创建文件
    private synchronized void createCodeFile(String packagePath, String path, String content) throws IOException {
        File realDir = new File(packagePath);
        if(!realDir.exists()){
            if(!realDir.mkdirs()) {
                throw new RuntimeException("创建包路径时出错，请检查项目目录结构!");
            }
        }
        File file = new File(path);
        if(!file.exists()){
            if(!file.createNewFile()){
                throw new RuntimeException("创建实体文件时出错，请检查目录结构!");
            }
        }
        FileOutputStream out = new FileOutputStream(file);
        Writer writer = new BufferedWriter(new OutputStreamWriter(out, Charset.forName("utf-8")));
        writer.write(content);
        writer.flush();
        out.close();
        writer.close();
        LOG.info("应用引擎已生成文件：" + file);
    }

    //生成entity
    private Map createEntity(String path, NextRobotSysMenuEntity nextRobotSysMenuTable, List<NextRobotEntityProperty> nextRobotEntityProperties) throws Exception{
        Map<String,Object> dataMap = new HashMap<String, Object>();
        dataMap.put("nowDate", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        Map<String,Object> engine = new HashMap<String, Object>();
        engine.put("entityName", nextRobotSysMenuTable.getEntityName());
        engine.put("businessClassification", nextRobotSysMenuTable.getBusinessClassification());
        engine.put("tableName", nextRobotSysMenuTable.getTableName());
        engine.put("remark", nextRobotSysMenuTable.getRemark());
        engine.put("packageName", propertyConfigurer.getProperty("system.package.name"));
        String templateEntityPath = "ame_entity.ftl";
        String packagePath = path + "/" + nextRobotSysMenuTable.getBusinessClassification() + "/entity";
        String entityPath = packagePath + "/" + nextRobotSysMenuTable.getEntityName() + ".java";
        dataMap.put("engine", engine);
        dataMap.put("fieldList", nextRobotEntityProperties);
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_21);
        configuration.setDefaultEncoding( "UTF-8" );
        configuration.setClassForTemplateLoading(this.getClass(), "/monitor/codeEngine");
        Template template = configuration.getTemplate(templateEntityPath);
        StringWriter stringWriter = new StringWriter();
        template.process(dataMap, stringWriter);
        Map<String,Object> fileInfo = new HashMap<String, Object>();
        fileInfo.put("filePath",entityPath);
        fileInfo.put("fileContent", stringWriter.toString());
        fileInfo.put("packagePath", packagePath);
        return fileInfo;
    }

    //生成dao
    private List<Map<String, String>> createDao(String path, NextRobotSysMenuEntity nextRobotSysMenuTable) throws Exception{
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
        String daoPackagePath = path + "/"+ nextRobotSysMenuTable.getBusinessClassification() + "/dao";
        String daoImplPackagePath = path + "/"+ nextRobotSysMenuTable.getBusinessClassification() + "/dao/Impl";
        String daoPath = daoPackagePath + "/" + nextRobotSysMenuTable.getEntityName() + "Dao.java";
        String daoImplPath = daoImplPackagePath + "/" + nextRobotSysMenuTable.getEntityName() + "DaoImpl.java";

        Configuration configuration = new Configuration(Configuration.VERSION_2_3_21);
        configuration.setDefaultEncoding( "UTF-8" );
        configuration.setClassForTemplateLoading(this.getClass(), "/monitor/codeEngine");
        Template templateDao = configuration.getTemplate(templateDaoPath);
        StringWriter stringWriterDao = new StringWriter();
        templateDao.process(dataMap, stringWriterDao);

        Template templateDaoImpl = configuration.getTemplate(templateDaoImplPath);
        StringWriter stringWriterDaoImpl = new StringWriter();
        templateDaoImpl.process(dataMap, stringWriterDaoImpl);

        List<Map<String, String>> filesInfo = new ArrayList<Map<String, String>>();
        Map<String, String> file = new HashMap<String, String>();
        file.put("filePath", daoPath);
        file.put("fileContent", stringWriterDao.toString());
        file.put("packagePath", daoPackagePath);
        filesInfo.add(file);

        Map<String, String> fileImpl = new HashMap<String, String>();
        fileImpl.put("filePath", daoImplPath);
        fileImpl.put("fileContent", stringWriterDaoImpl.toString());
        fileImpl.put("packagePath", daoImplPackagePath);
        filesInfo.add(fileImpl);

        return filesInfo;
    }

    //生成service
    private List<Map<String, String>> createService(String path, NextRobotSysMenuEntity nextRobotSysMenuTable) throws Exception{
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
        String servicePackagePath = path + "/"+ nextRobotSysMenuTable.getBusinessClassification() + "/service";
        String serviceImplPackagePath = path + "/"+ nextRobotSysMenuTable.getBusinessClassification() + "/service/Impl";
        String servicePath = servicePackagePath + "/" + nextRobotSysMenuTable.getEntityName() + "Service.java";
        String serviceImplPath = serviceImplPackagePath + "/" + nextRobotSysMenuTable.getEntityName() + "ServiceImpl.java";

        Configuration configuration = new Configuration(Configuration.VERSION_2_3_21);
        configuration.setDefaultEncoding( "UTF-8" );
        configuration.setClassForTemplateLoading(this.getClass(), "/monitor/codeEngine");
        Template templateService = configuration.getTemplate(templateServicePath);
        StringWriter stringWriterService = new StringWriter();
        templateService.process(dataMap, stringWriterService);

        Template templateServiceImpl = configuration.getTemplate(templateServiceImplPath);
        StringWriter stringWriterServiceImpl = new StringWriter();
        templateServiceImpl.process(dataMap, stringWriterServiceImpl);

        List<Map<String, String>> filesInfo = new ArrayList<Map<String, String>>();
        Map<String, String> file = new HashMap<String, String>();
        file.put("filePath", servicePath);
        file.put("fileContent", stringWriterService.toString());
        file.put("packagePath", servicePackagePath);
        filesInfo.add(file);

        Map<String, String> fileImpl = new HashMap<String, String>();
        fileImpl.put("filePath", serviceImplPath);
        fileImpl.put("fileContent", stringWriterServiceImpl.toString());
        fileImpl.put("packagePath", serviceImplPackagePath);
        filesInfo.add(fileImpl);

        return filesInfo;
    }
}
