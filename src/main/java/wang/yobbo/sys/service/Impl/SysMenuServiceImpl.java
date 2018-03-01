package wang.yobbo.sys.service.Impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import wang.yobbo.common.entity.Searchable;
import wang.yobbo.common.httpengine.http.EngineViewServlet;
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

    public Page<NextRobotSysMenu> getPage(Searchable searchable) {
        return this.sysMenuDao.getPage(searchable);
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

    public boolean createBusinessCode(NextRobotSysMenuTable nextRobotSysMenuTable,String entityMode,String entityRow) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        try {
            this.sysMenuTableDao.addEntity(nextRobotSysMenuTable); //保存
            List<String> entityModeBean = mapper.readValue(entityMode, new TypeReference<List<String>>() {});
            List<Map> entityRowBean = mapper.readValue(entityRow, new TypeReference<List<Map>>() {});
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
        fileInfo.put("filePath",entityPath);
        fileInfo.put("fileContent", stringWriter.toString());
        return fileInfo;
    }

    //生成dao
    private List<Map<String, String>> createDao(String path, NextRobotSysMenuTable nextRobotSysMenuTable) throws Exception{
        List<Map<String, String>> filesInfo = new ArrayList<Map<String, String>>();
        Map<String,Object> dataMap = new HashMap<String, Object>();
        dataMap.put("entityName", nextRobotSysMenuTable.getEntityName());
        dataMap.put("businessClassification", nextRobotSysMenuTable.getBusinessClassification());
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

        HashMap<String, String> file = new HashMap<String, String>();
        file.put("filePath", daoPath);
        file.put("fileContent", stringWriterDao.toString());
        filesInfo.add(file);

        HashMap<String, String> fileImpl = new HashMap<String, String>();
        file.put("filePath", daoImplPath);
        file.put("fileContent", stringWriterDaoImpl.toString());
        filesInfo.add(fileImpl);

        return filesInfo;
    }

    //生成service
    private List<Map<String, String>> createService(String path, NextRobotSysMenuTable nextRobotSysMenuTable) throws Exception{
        List<Map<String, String>> filesInfo = new ArrayList<Map<String, String>>();
        Map<String,Object> dataMap = new HashMap<String, Object>();
        dataMap.put("entityName", nextRobotSysMenuTable.getEntityName());
        dataMap.put("businessClassification", nextRobotSysMenuTable.getBusinessClassification());
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

        HashMap<String, String> file = new HashMap<String, String>();
        file.put("filePath", servicePath);
        file.put("fileContent", stringWriterService.toString());
        filesInfo.add(file);

        HashMap<String, String> fileImpl = new HashMap<String, String>();
        file.put("filePath", serviceImplPath);
        file.put("fileContent", stringWriterServiceImpl.toString());
        filesInfo.add(fileImpl);

        return filesInfo;
    }

}
