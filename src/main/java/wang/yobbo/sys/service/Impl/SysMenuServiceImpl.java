package wang.yobbo.sys.service.Impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.shiro.codec.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wang.yobbo.common.httpengine.http.EngineViewServlet;
import wang.yobbo.sys.dao.SysMenuDao;
import wang.yobbo.sys.dao.SysMenuTableDao;
import wang.yobbo.sys.entity.NextRobotSysMenu;
import wang.yobbo.sys.entity.NextRobotSysMenuTable;
import wang.yobbo.sys.service.SysMenuService;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

@Service
public class SysMenuServiceImpl implements SysMenuService {
    @Autowired
    private SysMenuDao sysMenuDao;

    @Autowired
    private SysMenuTableDao sysMenuTableDao;

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

            for(String mode : entityModeBean){
                if("entity".equals(mode)){
                    this.createEntity(basePathPrefix,nextRobotSysMenuTable,entityRow);
                }else if("service".equals(mode)){
                    this.createService(basePathPrefix,nextRobotSysMenuTable,entityRow);
                }else if("dao".equals(mode)){
                    this.createDao(basePathPrefix,nextRobotSysMenuTable,entityRow);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
        return true;
    }

    //生成entity
    public void createEntity(String path, NextRobotSysMenuTable nextRobotSysMenuTable, String entityRow) throws Exception{
        String templateEntityPath = "monitor/codeEngine/ame_entity.ftl";
        String entityPath = path + "/" + nextRobotSysMenuTable.getBusinessClassification() + "/entity/" + nextRobotSysMenuTable.getEntityName() + ".java";
        System.out.println(entityPath);
    }

    //生成dao
    private void createDao(String path, NextRobotSysMenuTable nextRobotSysMenuTable, String entityRow) throws Exception{
        String templateDaoPath = "monitor/codeEngine/ame_dao.ftl";
        String templateDaoImplPath = "monitor/codeEngine/ame_daoImpl.ftl";
        String daoPath = path + "/"+ nextRobotSysMenuTable.getBusinessClassification() + "/dao/" + nextRobotSysMenuTable.getEntityName() + "Dao.java";
        String daoImplPath = path + "/"+ nextRobotSysMenuTable.getBusinessClassification() + "/dao/Impl/" + nextRobotSysMenuTable.getEntityName() + "DaoImpl.java";
        System.out.println(daoImplPath);
    }

    //生成service
    private void createService(String path, NextRobotSysMenuTable nextRobotSysMenuTable, String entityRow) throws Exception{
        String templateServicePath = "monitor/codeEngine/ame_service.ftl";
        String templateServiceImplPath = "monitor/codeEngine/ame_serviceImpl.ftl";
        String servicePath = path + "/"+ nextRobotSysMenuTable.getBusinessClassification() + "/service/" + nextRobotSysMenuTable.getEntityName() + "Service.java";
        String serviceImplPath = path + "/"+ nextRobotSysMenuTable.getBusinessClassification() + "/service/Impl/" + nextRobotSysMenuTable.getEntityName() + "ServiceImpl.java";
        System.out.println(serviceImplPath);
    }

}
