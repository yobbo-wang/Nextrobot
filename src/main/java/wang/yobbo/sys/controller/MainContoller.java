package wang.yobbo.sys.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "main")
public class MainContoller {

    @RequestMapping(value = "index", method = RequestMethod.GET)
    public ModelAndView toIndex(){
        ModelAndView modelAndView = new ModelAndView("main/index");
        return modelAndView;
    }
}
