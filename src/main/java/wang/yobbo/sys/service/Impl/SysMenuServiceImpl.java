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
                String path = basePathPrefix + "/" + mode;
                if("entity".equals(mode)){
                    this.createEntity(path,nextRobotSysMenuTable,entityRow );
                }else if("service".equals(mode)){
                    this.createService(path,nextRobotSysMenuTable,entityRow);
                }else if("dao".equals(mode)){
                    this.createDao(path,nextRobotSysMenuTable,entityRow);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
        return true;
    }

    //生成dao
    private void createDao(String path, NextRobotSysMenuTable nextRobotSysMenuTable, String entityRow) throws Exception{
    }

    //生成service
    private void createService(String path, NextRobotSysMenuTable nextRobotSysMenuTable, String entityRow) throws Exception{
    }

    //生成entity
    public void createEntity(String path, NextRobotSysMenuTable nextRobotSysMenuTable, String entityRow) throws Exception{
    }
}
