package wang.yobbo.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import wang.yobbo.common.appengine.InvokeResult;

import java.util.Map;

/**
 * restful 入口
 * version v1.0
 */
@Controller
@RequestMapping(value = "/api/v1.0")
public class APIController {

    @RequestMapping(value = "/result", method = RequestMethod.POST)
    @ResponseBody
    public InvokeResult getExeResult(@RequestBody Map<String, Object> params){
        System.out.println("params: " + params);
        return InvokeResult.success("已接收成功!");
    }
}
