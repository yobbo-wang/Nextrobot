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
