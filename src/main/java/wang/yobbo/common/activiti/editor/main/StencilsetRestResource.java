package wang.yobbo.common.activiti.editor.main;

import java.io.InputStream;

import org.activiti.engine.ActivitiException;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xiaoyang
 */
@RestController
public class StencilsetRestResource {
  private static final String ACTIVITIINFONAME = "stencilset.json";

  /**
   * 获取Activiti配置信息
   * @return
   */
  @RequestMapping(value="/editor/stencilset", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
  public @ResponseBody String getStencilset() {
    InputStream stencilsetStream = this.getClass().getClassLoader().getResourceAsStream(ACTIVITIINFONAME);
    try {
      return IOUtils.toString(stencilsetStream, "utf-8");
    } catch (Exception e) {
      throw new ActivitiException("Error while loading stencil set", e);
    }
  }
}
