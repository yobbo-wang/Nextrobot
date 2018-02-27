package wang.yobbo.common.activiti.editor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Model;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by xiaoyang on 2017/12/23.
 * 流程设计模型控制器
 */
@RestController
@RequestMapping("/activiti-edit")
public class ModuleController {
    private final Logger logger = Logger.getLogger(ModuleController.class);
    @Autowired private RepositoryService repositoryService;

    /**
     * 创建流程
     * @param name 流程名称
     * @param key 流程key
     * @param description 描述
     * @param request 请求
     * @param response 响应
     */
    @RequestMapping(value = "/model/create")
    public void create(@RequestParam(value = "name") String name , @RequestParam(value = "key") String key,
                       @RequestParam(value = "description", required = false) String description, HttpServletRequest request,
                       HttpServletResponse response) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode editorNode = objectMapper.createObjectNode();
            editorNode.put("id", "canvas");
            editorNode.put("resourceId", "canvas");
            ObjectNode stencilSetNode = objectMapper.createObjectNode();
            stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
            editorNode.put("stencilset", stencilSetNode);
            Model modelData = repositoryService.newModel();

            ObjectNode modelObjectNode = objectMapper.createObjectNode();
            modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, name);
            modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
            modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, description == null ? "" : description);
            modelData.setMetaInfo(modelObjectNode.toString());
            modelData.setName(name);
            modelData.setKey(StringUtils.defaultString(key));

            repositoryService.saveModel(modelData);
            repositoryService.addModelEditorSource(modelData.getId(), editorNode.toString().getBytes("utf-8"));

            response.sendRedirect(request.getContextPath() + "/activiti-app/index.jsp?modelId=" + modelData.getId());
        } catch (Exception e) {
            logger.error("创建模型失败：", e);
        }
    }

}
