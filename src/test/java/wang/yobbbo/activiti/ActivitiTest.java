//package wang.yobbbo.activiti;
//
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import jdk.nashorn.internal.ir.ObjectNode;
//import org.activiti.bpmn.converter.BpmnXMLConverter;
//import org.activiti.bpmn.model.BpmnModel;
//import org.activiti.editor.language.json.converter.BpmnJsonConverter;
//import org.activiti.engine.ProcessEngine;
//import org.activiti.engine.RepositoryService;
//import org.activiti.engine.RuntimeService;
//import org.activiti.engine.TaskService;
//import org.activiti.engine.history.HistoricProcessInstance;
//import org.activiti.engine.history.HistoricTaskInstance;
//import org.activiti.engine.repository.DeploymentBuilder;
//import org.activiti.engine.repository.Model;
//import org.activiti.engine.repository.ProcessDefinition;
//import org.activiti.engine.repository.ProcessDefinitionQuery;
//import org.activiti.engine.runtime.ProcessInstance;
//import org.activiti.engine.task.Task;
//import org.activiti.engine.test.ActivitiRule;
//import org.activiti.engine.test.Deployment;
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.List;
//
//import static org.junit.Assert.assertEquals;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration("classpath:spring/spring-root.xml")
//public class ActivitiTest {
//    @Autowired
//    private RuntimeService runtimeService;
//    @Autowired
//    private TaskService taskService;
//    @Autowired
//    private RepositoryService repositoryService;
//    @Autowired
//    private ProcessEngine processEngine;
//    @Rule
//    public ActivitiRule activitiSpringRule;
//
//    //部署
//    @Test
//    public void deploy(){
//        //先获取
//        Model model = this.repositoryService.getModel("15001");
//        byte[] modelEditorSource = this.repositoryService.getModelEditorSource(model.getId()); //根据model获取字节流
//        try {
//            JsonNode jsonNode = new ObjectMapper().readTree(modelEditorSource);
//            byte[] bpmnBytes = null;
//            BpmnModel bpmnModel = new BpmnJsonConverter().convertToBpmnModel(jsonNode);
//            bpmnBytes = new BpmnXMLConverter().convertToXML(bpmnModel);
////            String processName = model.getName() + ".bpmn20.xml";
//            String processName =  "条件分支.bpmn20.xml";
//            DeploymentBuilder deploymentBuilder = repositoryService.createDeployment().name(model.getName()); //创建部署
//            deploymentBuilder.addString(processName, new String(bpmnBytes, "utf-8")).deploy(); //往内容里写东西
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 1)在数据库的act_ru_execution正在执行的执行对象表中插入一条记录
//     2)在数据库的act_hi_procinst程实例的历史表中插入一条记录
//     3)在数据库的act_hi_actinst活动节点的历史表中插入一条记录
//     4)我们图中节点都是任务节点，所以同时也会在act_ru_task流程实例的历史表添加一条记录
//     5)在数据库的act_hi_taskinst任务历史表中也插入一条记录。
//     */
//    @Test
//    public void startDeploy(){
////        this.processEngine.getRuntimeService().startProcessInstanceById("1");
//        ProcessInstance pi = this.processEngine.getRuntimeService().startProcessInstanceByKey("process_437520");
//        System.out.println("流程实例ID:" + pi.getId());
//        System.out.println("流程定义ID:" + pi.getProcessDefinitionId());
//    }
//
//    /**
//     * 查询历史流程实例
//     */
//    @Test
//    public void findHistoryProcessInstance(){
//        String processInstanceId = "7501";
//        HistoricProcessInstance hpi = processEngine.getHistoryService()
//                .createHistoricProcessInstanceQuery()
//                .processInstanceId(processInstanceId)
//                .singleResult();
//        System.out.println(hpi.getId() +"    "+hpi.getProcessDefinitionId()+"   "+ hpi.getStartTime()+"   "+hpi.getDurationInMillis());
//    }
//
//    /**
//     * 获取个人代办任务
//     */
//    /**
//    *1)因为是任务查询，所以从processEngine中应该得到TaskService
//     2)使用TaskService获取到任务查询对象TaskQuery
//     3)为查询对象添加查询过滤条件，使用taskAssignee指定任务的办理者（即查询指定用户的代办任务），同时可以添加分页排序等过滤条件
//     4)调用list方法执行查询，返回办理者为指定用户的任务列表
//     5)任务ID、名称、办理人、创建时间可以从act_ru_task表中查到。
//     6)在现在这种情况下，ProcessInstance相当于Execution
//     7) 一个Task节点和Execution节点是1对1的情况，在task对象中使用Execution_来表示他们之间的关系
//     8)任务ID在数据库表act_ru_task中对应“ID_”列
//     */
//    @Test
//    public void getTask(){
//       /* Task task = this.processEngine.getTaskService().createTaskQuery().singleResult();
//        System.out.println("任务ID：" + task.getId());
//        System.out.println("任务名称:" + task.getName());
//        System.out.println("任务的创建时间:" + task.getCreateTime());
//        System.out.println("任务的办理人:" + task.getAssignee());
//        System.out.println("流程实例ID:" + task.getProcessInstanceId());
//        System.out.println("执行对象ID:" + task.getExecutionId());
//        System.out.println("流程定义ID:" + task.getProcessDefinitionId());
//        System.out.println("##################################################");*/
//        //查询张三任务
//        List<Task> list = this.processEngine.getTaskService().createTaskQuery().taskAssignee("zhangsan")
//                // .taskCandidateGroup("")//组任务的办理人查询
//                // .processDefinitionId("")//使用流程定义ID查询
//                // .processInstanceId("")//使用流程实例ID查询
//                // .executionId(executionId)//使用执行对象ID查询
//                .orderByTaskCreateTime().desc().list();
//        for (Task task : list){
//            System.out.println("任务ID：" + task.getId());
//            System.out.println("任务名称:" + task.getName());
//            System.out.println("任务的创建时间:" + task.getCreateTime());
//            System.out.println("任务的办理人:" + task.getAssignee());
//            System.out.println("流程实例ID:" + task.getProcessInstanceId());
//            System.out.println("执行对象ID:" + task.getExecutionId());
//            System.out.println("流程定义ID:" + task.getProcessDefinitionId());
//            System.out.println("##################################################");
//        }
//
//    }
//
//    /**
//     * 完成我的任务
//     */
//    @Test
//    public void compliteMyPersonTask() {
//        // 任务ID
//        String taskId = "5004";
//        this.processEngine.getTaskService().complete(taskId);;
//        System.out.println("完成任务：任务ID:" + taskId);
//    }
//
//    /**
//     * 查询历史任务
//     */
//    @Test
//    public void findHistoryTask(){
//        String processInstanceId = "5001";
//        List<HistoricTaskInstance> list = processEngine.getHistoryService()//与历史数据（历史表）相关的service
//                .createHistoricTaskInstanceQuery()//创建历史任务实例查询
//                .processInstanceId(processInstanceId)
////              .taskAssignee(taskAssignee)//指定历史任务的办理人
//                .list();
//        if(list!=null && list.size()>0){
//            for(HistoricTaskInstance hti:list){
//                System.out.println(hti.getId()+"    "+hti.getName()+"    "+hti.getProcessInstanceId()+"   "+hti.getStartTime()
//                        +"   "+hti.getEndTime()+"   "+hti.getDurationInMillis());
//                System.out.println("################################");
//            }
//        }
//
//    }
//
//    /**
//     * 查询流程状态（判断流程正在执行，还是结束）
//     */
//    @Test
//    public void isProcessEnd(){
//        String processInstanceId =  "7501";
//        ProcessInstance pi = this.processEngine.getRuntimeService()//表示正在执行的流程实例和执行对象
//                .createProcessInstanceQuery()//创建流程实例查询
//                .processInstanceId(processInstanceId)//使用流程实例ID查询
//                .singleResult();
//
//        if(pi==null){
//            System.out.println("流程已经结束");
//        }
//        else{
//            System.out.println("流程没有结束");
//        }
//
//    }
//
//    @Test
//    @Deployment
//    public void simpleProcessTest() {
//        runtimeService.startProcessInstanceByKey("mysql-001");
//        Task task = taskService.createTaskQuery().singleResult();
//        System.out.println("我的任务名为:" + task.getName());
//        System.out.println("我的任务ID为:" + task.getName());
//
//        taskService.complete(task.getId());
//        System.out.println("任务数：" + runtimeService.createProcessInstanceQuery().count());
//        assertEquals(0, runtimeService.createProcessInstanceQuery().count());
//
//    }
//
//}
