package wang.yobbo.sys.service.Impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import wang.yobbo.common.appengine.InvokeResult;
import wang.yobbo.common.appengine.entity.Searchable;
import wang.yobbo.common.httpengine.http.EngineViewServlet;
import wang.yobbo.common.spring.PropertyConfigurer;
import wang.yobbo.sys.dao.BussisTemplateDao;
import wang.yobbo.sys.dao.EntityPropertyDao;
import wang.yobbo.sys.dao.SysMenuDao;
import wang.yobbo.sys.dao.SysMenuTableDao;
import wang.yobbo.sys.entity.BusinessTemplate;
import wang.yobbo.sys.entity.EntityProperty;
import wang.yobbo.sys.entity.SysMenu;
import wang.yobbo.sys.entity.SysMenuEntity;
import wang.yobbo.sys.service.SysMenuService;

import java.io.*;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.sql.Clob;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class SysMenuServiceImpl implements SysMenuService {
    private static final Logger LOG = LoggerFactory.getLogger(SysMenuServiceImpl.class);
    @Autowired private SysMenuDao sysMenuDao;
    @Autowired private SysMenuTableDao sysMenuTableDao;
    @Autowired private PropertyConfigurer propertyConfigurer;
    @Autowired private BussisTemplateDao nextRobotBussisTemplateDao;
    @Autowired private EntityPropertyDao nextRobotEntityPropertyDao;

    public Page<SysMenu> getPage(Searchable searchable) {
        return this.sysMenuDao.getPage(searchable);
    }

    public Long getCount(Searchable searchable) {
        return this.sysMenuDao.getCount(searchable);
    }

    //使用懒加载
    public List<SysMenu> findByPId(String pid) {
        List<SysMenu> menus = this.sysMenuDao.findByPId(pid);
        return menus;
    }

    public SysMenu findById(String id) {
        return this.sysMenuDao.findById(id);
    }

    public SysMenu save(SysMenu sysMenu) throws Exception{
        return this.sysMenuDao.save(sysMenu);
    }

    public int delete(String id) {
        return this.sysMenuDao.delete(id);
    }

    public SysMenuEntity addEntity(SysMenuEntity nextRobotSysMenuEntity) throws Exception{
        return this.sysMenuTableDao.addEntity(nextRobotSysMenuEntity);
    }

    public int deleteEntity(String id) {
        return this.sysMenuTableDao.deleteEntity(id);
    }

    public SysMenuEntity findSysMenuTableById(String id) {
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

    public boolean createBusinessCode(SysMenuEntity nextRobotSysMenuTable, String entityMode, List<EntityProperty> nextRobotEntityProperties) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        try {
            this.sysMenuTableDao.addEntity(nextRobotSysMenuTable); //保存
            this.nextRobotEntityPropertyDao.saveEntityProperty(nextRobotEntityProperties);
            //业务分类转为小写
            nextRobotSysMenuTable.setBusinessClassification(nextRobotSysMenuTable.getBusinessClassification().toLowerCase());
            //将实体首字母设置为大写
            nextRobotSysMenuTable.setEntityName(this.toUpperCaseFirstOne(nextRobotSysMenuTable.getEntityName()));
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

    public List<EntityProperty> saveEntityProperty(List<EntityProperty> nextRobotEntityProperties) throws Exception {
        return this.nextRobotEntityPropertyDao.saveEntityProperty(nextRobotEntityProperties);
    }

    public BusinessTemplate saveBusinessTemplate(BusinessTemplate nextRobotBusinessTemplate) throws Exception{
        return this.nextRobotBussisTemplateDao.saveBusinessTemplate(nextRobotBusinessTemplate);
    }

    public BusinessTemplate findTemplate(String id) {
        return this.nextRobotBussisTemplateDao.findTemplate(id);
    }

    @Override
    public List<BusinessTemplate> findTemplateAll() {
        return this.nextRobotBussisTemplateDao.findTemplateAll();
    }

    @Override
    public int deleteTemplate(String id) throws Exception {
        return this.nextRobotBussisTemplateDao.deleteTemplate(id);
    }

    //获取系统工程目录
    @Override
    public List<Map<String, Object>> getProjectDirTree(String systemBasePath) throws IOException {
        try {
            systemBasePath = URLDecoder.decode(systemBasePath,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new UnsupportedEncodingException("系统工程目录不支持utf-8字符编码");
        }
        File file = new File(systemBasePath);
        if(!file.exists()) throw new IOException("系统工程目录不存在");
        FileFilter fileFilter = new FileFilter() {
            public boolean accept(File file) {
                if(file.isDirectory()){
                    return true;
                }
                return false;
            }
        };
        List<Map<String, Object>> dir = new ArrayList<>();
        Map<String, Object> dirs = new HashMap<>();
        this.getDirList(file, fileFilter, dirs);
        Object children = dirs.get("children");
        if(children != null){
            dir.addAll((List)children);
        }
        return dir;
    }

    @Override
    public boolean createFileByTemplate(Map<String, Object> param) throws Exception {
        Object id = param.get("id");
        Object templateJson = param.get("template_json");
        Object templateWritePath = param.get("templateWritePath");
        Object name = param.get("name");
        Object json = null;
        if(id == null || id.toString().isEmpty()){
            throw new Exception("请选择模板！");
        }
        //检查json
        if(templateJson != null && !templateJson.toString().isEmpty()){
            ObjectMapper mapper = new ObjectMapper();
            try {
                json = mapper.readValue(templateJson.toString(), new TypeReference<List<Map>>() {});
            } catch (IOException e) {
                try {
                    json = mapper.readValue(templateJson.toString(), new TypeReference<Map>() {});
                } catch (IOException e1) {
                    throw new IOException("请检查json数据格式");
                }
            }
        }

        BusinessTemplate businessTemplate = this.findTemplate(id.toString());
        //读取文件流
        Clob fileContent = businessTemplate.getFileContent();
        Reader characterStream = fileContent.getCharacterStream();
        byte[] bytes = IOUtils.toByteArray(characterStream, Charset.forName("utf-8"));
        String fileContentStr = new String (bytes, Charset.forName("utf-8"));
        String filePath = templateWritePath + "/" + name + businessTemplate.getFileType();

        Configuration configuration = new Configuration(Configuration.VERSION_2_3_21);
        configuration.setDefaultEncoding( "UTF-8" );
        Template template = new Template("", fileContentStr, configuration);
        StringWriter stringWriter = new StringWriter();
        template.process(json, stringWriter);
        characterStream.close();
        String fileContentFinally = stringWriter.toString();

        File file = new File(filePath);
        if(!file.exists()){
            if(!file.createNewFile()){
                throw new RuntimeException("创建文件是出错，请检查!");
            }
        }
        FileOutputStream out = new FileOutputStream(file);
        Writer writer = new BufferedWriter(new OutputStreamWriter(out, Charset.forName("utf-8")));
        writer.write(fileContentFinally);
        writer.flush();
        out.close();
        writer.close();
        LOG.info("应用引擎已生成文件：" + filePath);
        return true;
    }

    @Override
    public List<SysMenuEntity> getEntitys() {
        return this.sysMenuTableDao.getEntitys();
    }

    // 递归获取目录
    private void getDirList(File file,FileFilter fileFilter, Map<String, Object> files){
        File[] f = file.listFiles(fileFilter);
        if(f != null && f.length >= 1){
            List<Map<String, Object>> dir = new ArrayList<>();
            for(int i=0;i<f.length;i++){
                File file_1 = f[i];
                Map<String, Object> dir_2 = new HashMap<>();
                dir_2.put("text", file_1.getName());
                dir_2.put("id", file_1.getPath().replaceAll("\\\\", "/"));
                if(file_1.listFiles(fileFilter).length == 0 ){
                    dir_2.put("state", "open");
                }else{
                    dir_2.put("state", "closed");
                }
                dir.add(dir_2);
                this.getDirList(file_1,fileFilter,dir_2);
            }
            files.put("children", dir) ;
        }
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
    private Map createEntity(String path, SysMenuEntity nextRobotSysMenuTable, List<EntityProperty> nextRobotEntityProperties) throws Exception{
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
    private List<Map<String, String>> createDao(String path, SysMenuEntity nextRobotSysMenuTable) throws Exception{
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
    private List<Map<String, String>> createService(String path, SysMenuEntity nextRobotSysMenuTable) throws Exception{
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
