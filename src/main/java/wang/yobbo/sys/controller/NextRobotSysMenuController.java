package wang.yobbo.sys.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import wang.yobbo.common.appengine.InvokeResult;
import wang.yobbo.common.spring.PropertyConfigurer;
import wang.yobbo.sys.entity.NextRobotBusinessTemplate;
import wang.yobbo.sys.entity.NextRobotEntityProperty;
import wang.yobbo.sys.entity.NextRobotSysMenu;
import wang.yobbo.sys.entity.NextRobotSysMenuEntity;
import wang.yobbo.sys.service.NextRobotSysMenuService;

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
public class NextRobotSysMenuController {

    @Autowired private PropertyConfigurer propertyConfigurer;
    @Autowired private NextRobotSysMenuService sysMenuService;

    @RequestMapping(value = "/getEntityProperty/{entityName}", method = RequestMethod.GET)
    @ResponseBody
    public String getEntityProperty(@PathVariable(value = "entityName") String entityName){

        return null;
    }

    @RequestMapping(value = "findByPId", method = RequestMethod.POST)
    @ResponseBody
    public List<NextRobotSysMenu> findByPId(HttpServletRequest request){
        String pid = new String();
        if(request.getParameter("PID") != null || StringUtils.isNotEmpty(request.getParameter("PID"))){
            pid = request.getParameter("PID");
        }
        List<NextRobotSysMenu> menus = this.sysMenuService.findByPId(pid);
        return menus;
    }

    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public InvokeResult save(NextRobotSysMenu sysMenu){
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

    @RequestMapping(value = "uploadTemplate", method = RequestMethod.POST)
    @ResponseBody
    public InvokeResult uploadTemplate(@RequestParam("templateFile")MultipartFile templateFile,
                                       @RequestParam("templateJson")String templateJson){
        ObjectMapper mapper = new ObjectMapper();
        //序列化检查json格式
        try {
            mapper.readValue(templateJson, new TypeReference<List<Map>>() {});
        } catch (IOException e) {
            try {
                mapper.readValue(templateJson, new TypeReference<Map>() {});
            } catch (IOException e1) {
                return InvokeResult.failure("请检查json格式");
            }
        }
        NextRobotBusinessTemplate nextRobotBusinessTemplate = new NextRobotBusinessTemplate();
        try {
            String originalFilename = templateFile.getOriginalFilename();
            int i = originalFilename.lastIndexOf(".");
            InputStream stream = templateFile.getInputStream();
            SerialClob clob = new SerialClob(IOUtils.toCharArray(stream, Charset.forName("utf-8"))); //实例化clob
            nextRobotBusinessTemplate.setFileType(originalFilename.substring(i));
            nextRobotBusinessTemplate.setName(originalFilename.substring(0, i));
            nextRobotBusinessTemplate.setFileContent(clob);
            nextRobotBusinessTemplate.setTemplate_json(templateJson);
            stream.close();
            this.sysMenuService.saveBusinessTemplate(nextRobotBusinessTemplate);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SerialException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return InvokeResult.success("上传成功");
    }

    @RequestMapping(value = "saveEntity", method = RequestMethod.POST)
    @ResponseBody
    public InvokeResult saveEntity(NextRobotSysMenuEntity nextRobotSysMenuEntity, @RequestParam(value = "entityRow") String entityRow){
        if(StringUtils.isEmpty(entityRow))
            return InvokeResult.failure("请添加实体属性!");
        ObjectMapper mapper = new ObjectMapper();
        try {
            this.sysMenuService.addEntity(nextRobotSysMenuEntity);
            List<NextRobotEntityProperty> nextRobotEntityProperties = mapper.readValue(entityRow, new TypeReference<List<NextRobotEntityProperty>>(){});
            List<NextRobotEntityProperty> newProperties = this.sysMenuService.saveEntityProperty(nextRobotEntityProperties);
            if(!newProperties.isEmpty() && newProperties.size() > 0){
                return InvokeResult.success(newProperties);
            }else{
                return InvokeResult.failure("保存失败");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return InvokeResult.failure("保存失败");
        }
    }

    @RequestMapping(value = "createBusinessCode", method = RequestMethod.POST)
    @ResponseBody
    public InvokeResult createBusinessCode(NextRobotSysMenuEntity nextRobotSysMenuTable,
                                           @RequestParam(value = "entityMode") String entityMode,
                                           @RequestParam(value = "entityRow") String entityRow){
        if(StringUtils.isEmpty(entityMode))
            return InvokeResult.failure("请选择生成业务代码!");
        if(StringUtils.isEmpty(entityRow))
            return InvokeResult.failure("请添加实体属性!");
        try {
            ObjectMapper mapper = new ObjectMapper();
            List<NextRobotEntityProperty> nextRobotEntityProperties = mapper.readValue(entityRow, new TypeReference<List<NextRobotEntityProperty>>(){});
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

    @RequestMapping(value = "addEntity", method = RequestMethod.POST)
    @ResponseBody
    public InvokeResult addEntity(NextRobotSysMenuEntity sysMenuTable){
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

    @RequestMapping(value = "deleteEntity", method = RequestMethod.POST)
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

    @RequestMapping(value = "delete", method = RequestMethod.POST)
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