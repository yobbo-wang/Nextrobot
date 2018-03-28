package wang.yobbo.sys.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import wang.yobbo.common.appengine.InvokeResult;
import wang.yobbo.common.spring.PropertyConfigurer;
import wang.yobbo.sys.entity.BusinessTemplate;
import wang.yobbo.sys.entity.EntityProperty;
import wang.yobbo.sys.entity.SysMenu;
import wang.yobbo.sys.entity.SysMenuEntity;
import wang.yobbo.sys.service.SysMenuService;

import javax.servlet.http.HttpServletRequest;
import javax.sql.rowset.serial.SerialClob;
import javax.sql.rowset.serial.SerialException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/menu")
public class SysMenuController {

    @Autowired private PropertyConfigurer propertyConfigurer;
    @Autowired private SysMenuService sysMenuService;

    @RequestMapping(value = "/getTemplates", method = RequestMethod.GET)
    @ResponseBody
    public List<BusinessTemplate> getTemplates(){
        try{
            return this.sysMenuService.findTemplateAll();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping(value = "/getEntitys", method = RequestMethod.GET)
    @ResponseBody
    public List<SysMenuEntity> getEntitys(){
        try{
            List<SysMenuEntity> entityList = this.sysMenuService.getEntitys();
            return entityList;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping(value = "/deleteTemplate/{id}", method = RequestMethod.POST)
    @ResponseBody
    public InvokeResult deleteTemplate(@PathVariable(value = "id") String id){
        try {
            int i = this.sysMenuService.deleteTemplate(id);
            if(i > 0){
                return InvokeResult.success("删除成功");
            }
            else{
                return InvokeResult.failure("删除失败");
            }
        }catch (Exception e){
            e.printStackTrace();
            return InvokeResult.failure("删除失败");
        }
    }

    @RequestMapping(value = "/createFileByTemplate", method = RequestMethod.POST)
    @ResponseBody
    public InvokeResult createFileByTemplate(@RequestParam Map<String, Object> param){
        try{
            if(param == null || param.isEmpty()) return InvokeResult.failure("生成失败，请检查参数");
            boolean status = this.sysMenuService.createFileByTemplate(param);
            if(status){
                return InvokeResult.success("生成成功!");
            }else {
                return InvokeResult.failure("生成失败!");
            }
        }catch (Exception e){
            e.printStackTrace();
            return InvokeResult.failure("生成失败：" + e.getMessage());
        }
    }

    @RequestMapping(value = "/getProjectDirTree", method = RequestMethod.GET)
    @ResponseBody
    public InvokeResult getProjectDirTree(){
        String systemBasePath = this.propertyConfigurer.getProperty("system.base.path");
        if(systemBasePath.isEmpty()){
            return InvokeResult.failure("请在系统配置文件中设置system.base.path");
        }
        try{
            List<Map<String, Object >> dirs =  this.sysMenuService.getProjectDirTree(systemBasePath + "/src");
            if(dirs == null || dirs.size() == 0){
                return InvokeResult.failure("获取系统工程目录失败，请检查工程");
            }
            return InvokeResult.success(dirs);
        }catch (Exception e){
            e.printStackTrace();
            return InvokeResult.failure("获取系统工程目录失败：" + e.getMessage());
        }
    }

    @RequestMapping(value = "/findByPId", method = RequestMethod.POST)
    @ResponseBody
    public List<SysMenu> findByPId(HttpServletRequest request){
        String pid = new String();
        if(request.getParameter("PID") != null || StringUtils.isNotEmpty(request.getParameter("PID"))){
            pid = request.getParameter("PID");
        }
        List<SysMenu> menus = this.sysMenuService.findByPId(pid);
        return menus;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public InvokeResult save(SysMenu sysMenu){
        try{
            if(StringUtils.isEmpty(sysMenu.getParentId())){
                sysMenu.setParentId(null);
            }
            this.sysMenuService.save(sysMenu);
            return InvokeResult.success("保存成功!");
        }catch (Exception e){
            e.printStackTrace();
            return InvokeResult.failure("保存失败!");
        }
    }

    @RequestMapping(value = "/uploadTemplate", method = RequestMethod.POST)
    @ResponseBody
    public InvokeResult uploadTemplate(@RequestParam("templateFile")MultipartFile templateFile,
                                       @RequestParam("templateJson")String templateJson,
                                       @RequestParam("fileType")String fileType) {
        ObjectMapper mapper = new ObjectMapper();
        BusinessTemplate nextRobotBusinessTemplate = new BusinessTemplate();
        //序列化检查json格式
        if(templateJson != null && !templateJson.isEmpty()){
            try {
                mapper.readValue(templateJson, new TypeReference<List<Map>>() {});
            } catch (IOException e) {
                try {
                    mapper.readValue(templateJson, new TypeReference<Map>() {});
                } catch (IOException e1) {
                    return InvokeResult.failure("请检查json数据");
                }
            }
            nextRobotBusinessTemplate.setTemplate_json(templateJson);
        }

        try {
            String originalFilename = templateFile.getOriginalFilename();
            int i = originalFilename.lastIndexOf(".");
            InputStream stream = templateFile.getInputStream();
            SerialClob clob = new SerialClob(IOUtils.toCharArray(stream, Charset.forName("utf-8"))); //实例化clob
            nextRobotBusinessTemplate.setFileType(fileType);
            nextRobotBusinessTemplate.setName(originalFilename.substring(0, i));
            nextRobotBusinessTemplate.setFileContent(clob);
            stream.close();
            this.sysMenuService.saveBusinessTemplate(nextRobotBusinessTemplate);
        } catch (IOException e) {
            e.printStackTrace();
            return InvokeResult.failure("上传失败");
        } catch (SerialException e) {
            e.printStackTrace();
            return InvokeResult.failure("上传失败");
        } catch (SQLException e) {
            e.printStackTrace();
            return InvokeResult.failure("上传失败");
        } catch (Exception e) {
            e.printStackTrace();
            return InvokeResult.failure("上传失败");
        }
        return InvokeResult.success("上传成功");
    }

    @RequestMapping(value = "/saveEntity", method = RequestMethod.POST)
    @ResponseBody
    public InvokeResult saveEntity(SysMenuEntity nextRobotSysMenuEntity,
                                   @RequestParam(value = "entityRow") String entityRow,
                                   @RequestParam(value = "deleteEntityRow") String deleteEntityRow){
        if(StringUtils.isEmpty(entityRow))
            return InvokeResult.failure("请添加实体属性!");
        ObjectMapper mapper = new ObjectMapper();
        try {
            if(nextRobotSysMenuEntity != null)this.sysMenuService.addEntity(nextRobotSysMenuEntity);
            List<EntityProperty> nextRobotEntityProperties = mapper.readValue(entityRow, new TypeReference<List<EntityProperty>>(){});
            List<EntityProperty> nextRobotEntityPropertiesDelete = mapper.readValue(deleteEntityRow, new TypeReference<List<EntityProperty>>(){});
            for(EntityProperty entityProperty : nextRobotEntityPropertiesDelete){
                if(entityProperty == null || entityProperty.getId().isEmpty()) continue;
                this.sysMenuService.deleteEntityProperty(entityProperty.getId());
            }
            List<EntityProperty> newProperties = this.sysMenuService.saveEntityProperty(nextRobotEntityProperties);
            return InvokeResult.success(newProperties);
        } catch (Exception e) {
            e.printStackTrace();
            return InvokeResult.failure("保存失败");
        }
    }

    @RequestMapping(value = "/createBusinessCode", method = RequestMethod.POST)
    @ResponseBody
    public InvokeResult createBusinessCode(SysMenuEntity nextRobotSysMenuTable,
                                           @RequestParam(value = "entityMode") String entityMode,
                                           @RequestParam(value = "entityRow") String entityRow,
                                           @RequestParam(value = "deleteEntityRow") String deleteEntityRow){
        if(StringUtils.isEmpty(entityMode))
            return InvokeResult.failure("请选择生成业务代码!");
        if(StringUtils.isEmpty(entityRow))
            return InvokeResult.failure("请添加实体属性!");
        try {
            ObjectMapper mapper = new ObjectMapper();
            List<EntityProperty> nextRobotEntityProperties = mapper.readValue(entityRow, new TypeReference<List<EntityProperty>>(){});
            List<EntityProperty> nextRobotEntityPropertiesDelete = mapper.readValue(deleteEntityRow, new TypeReference<List<EntityProperty>>(){});
            for(EntityProperty entityProperty : nextRobotEntityPropertiesDelete){
                if(entityProperty.getId().isEmpty()) continue;
                this.sysMenuService.deleteEntityProperty(entityProperty.getId());
            }
            boolean businessCode = this.sysMenuService.createBusinessCode(nextRobotSysMenuTable, entityMode, nextRobotEntityProperties);
            if(businessCode){
                return InvokeResult.success("生成成功!");
            }else{
                return InvokeResult.failure("操作失败，请联系管理员");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return InvokeResult.failure("操作失败，请联系管理员");
        }
    }

    @RequestMapping(value = "/addEntity", method = RequestMethod.POST)
    @ResponseBody
    public InvokeResult addEntity(SysMenuEntity sysMenuTable){
        try{
            if(StringUtils.isEmpty(sysMenuTable.getMenuId())){
                return InvokeResult.failure("请选择对应菜单，再添加实体!");
            }
            if(StringUtils.isEmpty(sysMenuTable.getId())){
                sysMenuTable.setTableName(propertyConfigurer.getProperty("system.table.prefix.name") + "_" + sysMenuTable.getEntityName());
            }
            sysMenuTable.setBusinessClassification(sysMenuTable.getEntityName());
            sysMenuTable.setEntityName(propertyConfigurer.getProperty("system.entity.prefix.name") + sysMenuTable.getEntityName());
            this.sysMenuService.addEntity(sysMenuTable);
            return InvokeResult.success("保存成功!");
        }catch (Exception e){
            e.printStackTrace();
            return InvokeResult.failure("保存失败!");
        }
    }

    @RequestMapping(value = "/deleteEntity", method = RequestMethod.POST)
    @ResponseBody
    public InvokeResult deleteEntity(@RequestParam(value="id") String id){
        try{
            int count = this.sysMenuService.deleteEntity(id);
            return InvokeResult.success("已删除" + count + "条记录!");
        }catch (Exception e){
            e.printStackTrace();
            return InvokeResult.failure("删除失败!");
        }
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public InvokeResult delete(@RequestParam(value="id") String id){
        try{
            int count = this.sysMenuService.delete(id);
            return InvokeResult.success("已删除" + count + "条记录!");
        }catch (Exception e){
            e.printStackTrace();
            return InvokeResult.failure("删除失败!");
        }
    }
}
