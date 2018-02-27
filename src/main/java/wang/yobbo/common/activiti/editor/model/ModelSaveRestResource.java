package wang.yobbo.common.activiti.editor.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.Resource;
import org.activiti.bpmn.model.Signal;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Model;
import org.activiti.validation.ProcessValidator;
import org.activiti.validation.ProcessValidatorFactory;
import org.activiti.validation.ValidationError;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;

/**
 * @author xiaoyang Rademakers
 */
@RestController
public class ModelSaveRestResource implements ModelDataJsonConstants {
  private static final Logger LOGGER = LoggerFactory.getLogger(ModelSaveRestResource.class);

  private RepositoryService repositoryService;
  
  private ObjectMapper objectMapper;

  /**
   * 保存模型，并验证合法性
   * @param modelId
   * @param values
   */
  @RequestMapping(value="/model/{modelId}/save", method = RequestMethod.PUT)
  @ResponseStatus(value = HttpStatus.OK)
  public void saveModel(@PathVariable String modelId, @RequestBody MultiValueMap<String, String> values) {

      try {
      
        Model model = repositoryService.getModel(modelId);

        ObjectNode modelJson = (ObjectNode) objectMapper.readTree(model.getMetaInfo());

        modelJson.put(MODEL_NAME, values.getFirst("name"));
        modelJson.put(MODEL_DESCRIPTION, values.getFirst("description"));
        model.setMetaInfo(modelJson.toString());
        model.setName(values.getFirst("name"));

        repositoryService.saveModel(model);
        repositoryService.addModelEditorSource(model.getId(), values.getFirst("json_xml").getBytes("utf-8"));

        InputStream svgStream = new ByteArrayInputStream(values.getFirst("svg_xml").getBytes("utf-8"));
        TranscoderInput input = new TranscoderInput(svgStream);

        PNGTranscoder transcoder = new PNGTranscoder();
        // Setup output
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        TranscoderOutput output = new TranscoderOutput(outStream);

        // Do the transformation
        transcoder.transcode(input, output);
        final byte[] result = outStream.toByteArray();
        repositoryService.addModelEditorSourceExtra(model.getId(), result);
        outStream.close();

        //保存之前验证流程图合法性
        ProcessValidatorFactory processValidatorFactory = new ProcessValidatorFactory();
        ProcessValidator processValidator = processValidatorFactory.createDefaultProcessValidator();
        //根据modelId获取字节流
        byte[] modelEditorSource = this.repositoryService.getModelEditorSource(model.getId());
        JsonNode jsonNode = new ObjectMapper().readTree(modelEditorSource);
        BpmnModel bpmnModel = new BpmnJsonConverter().convertToBpmnModel(jsonNode);
        List<ValidationError> validate = processValidator.validate(bpmnModel);

        if(validate != null && validate .size() > 0){
          ValidationError validationError = validate.get(0);
          //如果有错误，总是抛出第一个错误
          throw new ActivitiException("Activiti Error. ActivitiId is " + validationError.getActivityId()
                  + ", problem is " + validationError.getProblem());
        }
    } catch (Exception e) {
      LOGGER.error("Error saving model", e);
      throw new ActivitiException("Error saving model", e);
    }
  }

  @Autowired
  public void setRepositoryService(RepositoryService repositoryService) {
    this.repositoryService = repositoryService;
  }

  @Autowired
  public void setObjectMapper(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }
}
