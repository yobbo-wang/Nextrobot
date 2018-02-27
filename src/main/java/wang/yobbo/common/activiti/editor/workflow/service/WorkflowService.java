package wang.yobbo.common.activiti.editor.workflow.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.*;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import wang.yobbo.common.activiti.editor.workflow.entity.StatefulEntity;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wang.yobbo.sys.dao.SysUsersDao;
import wang.yobbo.sys.exception.ProcessInstancesIsNotInactiveException;
import wang.yobbo.sys.util.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xiaoyang on 2017/12/23.
 * 流程管理服务类
 * TODO 封装Exception公共处理类
 */
@Service
public class WorkflowService {
    private final Logger logger = Logger.getLogger(WorkflowService.class);

    @Autowired private RepositoryService repositoryService;
    @Autowired private RuntimeService runtimeService;
    @Autowired private TaskService taskService;
    @Autowired private HistoryService historyService;
    @Autowired private ManagementService managementService;
    @Autowired private IdentityService identityService;
    @Autowired private SysUsersDao usersDao;

    /**
     * 从数据库中获取流，并部署流程
     * @param modelId
     * @return
     */
    public boolean deployByModelId(String modelId) throws Exception {
        try {
            Model model = this.repositoryService.getModel(modelId);
            byte[] modelEditorSource = this.repositoryService.getModelEditorSource(model.getId()); //根据model获取字节流
            JsonNode jsonNode = new ObjectMapper().readTree(modelEditorSource);
            BpmnModel bpmnModel = new BpmnJsonConverter().convertToBpmnModel(jsonNode);
            byte[] bpmnBytes = new BpmnXMLConverter().convertToXML(bpmnModel);
            String processName = model.getName() + ".bpmn20.xml";
            DeploymentBuilder deploymentBuilder = repositoryService.createDeployment().name(model.getName()); //创建部署
            deploymentBuilder.addString(processName, new String(bpmnBytes, "utf-8")).deploy(); //往内容里写东西
        } catch (IOException e) {
            logger.error("部署流程失败：", e);
            throw new Exception(e.getMessage());
        }
        return true;
    }

    /**
     * 部署流程，支持流部署
     * @param resourceName 资源名称
     * @param inputStream 资源流
     * @return 执行结果
     */
    public boolean deploy(String resourceName, InputStream inputStream) throws Exception {
        try{
            if(null == resourceName || StringUtils.isEmpty(resourceName)) throw new Exception("资源名称为空，部署失败！");
            if(null == inputStream) throw new Exception("资源流为空，部署失败！");
            this.repositoryService.createDeployment().addInputStream(resourceName, inputStream).enableDuplicateFiltering().deploy();
        }catch (Exception e){
            logger.error("部署流程失败：", e);
            throw new Exception(e.getMessage());
        }
       return true;
    }

    /**
     * 删除流程
     * @param deploymentId 流程ID
     * @return 执行结果
     */
    public boolean deleteDeploy(String deploymentId){
        try{
            if(null == deploymentId) throw new Exception("流程ID为空，删除失败！");
            //根据流程id，查找流程
            ProcessDefinition processDefinition = this.repositoryService.createProcessDefinitionQuery().deploymentId(deploymentId).singleResult();
            if(null == processDefinition) throw new Exception("查询到流程为空，删除失败！");
            this.repositoryService.deleteDeployment(deploymentId, false);
        }catch (Exception e){
            logger.error("删除流程失败：", e);
            throw new ProcessInstancesIsNotInactiveException(e.getMessage()); // 抛出为了事务回滚
        }
        return true;
    }

    /**
     * 删除部署内容，支持级联删除
     * @param deploymentId 流程ID
     * @param cascade 为true做级联删除
     */
    public boolean deleteDeploy(String deploymentId,boolean cascade) throws Exception {
        try{
            if(null == deploymentId || StringUtils.isEmpty(deploymentId)) throw new Exception("流程ID为空，删除部署内容失败！");
            ProcessDefinition definition = repositoryService.createProcessDefinitionQuery().deploymentId(deploymentId).singleResult();
            if (null == definition) throw new Exception("查询到流程为空，删除部署内容失败！");
            repositoryService.deleteDeployment(definition.getId(), cascade);
        }catch (Exception e){
            logger.error("删除部署内容失败：", e);
            throw  new Exception(e.getMessage());
        }
        return true;
    }

    /**
     * 获得资源文件
     * @param output 流容器
     * @param deploymentId 流程ID
     * @throws IOException
     */
    public void getProcessModal(OutputStream output, String deploymentId) throws Exception {
        try{
            if(null == deploymentId || StringUtils.isEmpty(deploymentId)) throw new Exception("流程ID为空，获取资源文件失败！");
            if(null == output) throw new Exception("接收流为空！");
            ProcessDefinition definition = repositoryService.createProcessDefinitionQuery().deploymentId(deploymentId).singleResult();
            if (definition != null) throw new Exception("查询到流程为空，获取资源文件失败！");;
            InputStream ins = repositoryService.getProcessModel(definition.getId());
            IOUtils.byteToOutputStream(IOUtils.toByteArray(ins), output);
        }catch (Exception e){
            logger.error("获取资源文件失败：", e);
            throw new Exception(e.getMessage());
        }
    }

    /**
     * 启动流程
     * @param entity 流程相关的业务实体
     * @param initiator 流程创建人ID
     * @param processKey 流程启动的key
     * @param appName 应用名称
     * @param appURI 应用URI
     * @param variables 流程变量信息
     */
    public void startProcess(StatefulEntity entity, Long initiator, String processKey,
                             String appName, String appURI, Map<String, Object> variables){
        if(null == variables) variables = new HashMap<String, Object>();
        variables.put(VAR_APP_NAME, appName);
        variables.put(VAR_APP_URI, appURI);
        variables.put(VAR_OWNER_TABLE, entity.getClass().getName());
        variables.put(VAR_OWNER_ID, entity.getID());
        variables.put(VAR_CREATOR, initiator);
//        if(null != initiator) variables.put(VAR_CREATOR_NAME, usersDao.findOne(initiator).getUsername());
        variables.put(VAR_CREATE_DATE_TIME, new Date());
        this.identityService.setAuthenticatedUserId(initiator.toString()); //设置授权用户信息
        ProcessInstance processInstance = this.runtimeService.startProcessInstanceByKey(processKey, variables);
        if(null != processInstance){
            //更新业务信息
            System.out.print("更新业务信息");
            //TODO
        }


    }

    private static final String VAR_APP_NAME = "APP_NAME"; //应用名
    private static final String VAR_APP_URI = "APP_URI"; //应用访问地址
    private static final String VAR_OWNER_TABLE = "OWNER_TABLE"; //应用主对象名
    private static final String VAR_OWNER_ID = "OWNER_ID"; //应用主对象ID
    private static final String VAR_CREATOR = "CREATOR"; //创建者账号
    private static final String VAR_CREATOR_NAME = "CREATOR_NAME"; //创建者姓名
    private static final String VAR_CREATE_DATE_TIME = "CREATE_DATE_TIME"; //创建时间

}
