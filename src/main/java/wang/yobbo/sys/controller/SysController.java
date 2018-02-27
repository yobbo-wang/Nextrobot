package wang.yobbo.sys.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import wang.yobbo.common.appengine.InvokeResult;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "sys")
public class SysController {

    @RequestMapping(value = "login", method = RequestMethod.POST)
    @ResponseBody
    public InvokeResult login(){
        Map<String, String> result = new HashMap();
        result.put("url", "http://127.0.0.1:8881/MS/sys/index");
        result.put("msg", "登录成功，正在跳转...");
        InvokeResult invokeResult = InvokeResult.success(result);
        return invokeResult;
    }

    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public ModelAndView logout(){
        //清除cookie，退出到登录页面
        ModelAndView modelAndView = new ModelAndView("index");
        return modelAndView;
    }

    @RequestMapping(value = "index", method = RequestMethod.GET)
    public ModelAndView toIndex(){
        ModelAndView modelAndView = new ModelAndView("index");
        return modelAndView;
    }

    @RequestMapping(value = "setting", method = RequestMethod.GET)
    public ModelAndView toSetting(){
        ModelAndView modelAndView = new ModelAndView("setting/index");
        return modelAndView;
    }
}
