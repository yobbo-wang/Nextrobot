package wang.yobbo.sys.controller;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import wang.yobbo.common.appengine.InvokeResult;
import wang.yobbo.common.appengine.plugin.NtConstants;
import wang.yobbo.sys.entity.NextRobotSysUser;
import wang.yobbo.sys.security.CurrentUser;
import wang.yobbo.sys.service.SysUserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@Controller
@RequestMapping(value = "/sys")
public class LoginController {
    @Autowired private SysUserService sysUserService;


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(HttpServletResponse response, HttpServletRequest request, HttpSession httpSession) {
        if (CurrentUser.getSubject().isAuthenticated()) {
            return "redirect:/sys/index";
        }
        response.addHeader("REQ_AUTH", "1");
        return "redirect:/login.jsp";
    }

    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public InvokeResult login(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        String userName = request.getParameter("userName");
        String password = request.getParameter("userCode");
        if(userName == null|| StringUtils.isEmpty(userName) || password == null || StringUtils.isEmpty(password))
        {
            return InvokeResult.failure("账号或密码错误!");
        }
        try{
            UsernamePasswordToken usernamePasswordToken = null;
            final String USERNAME_PATTERN = "^[\\u4E00-\\u9FA5\\uf900-\\ufa2d_a-zA-Z][\\u4E00-\\u9FA5\\uf900-\\ufa2d\\w]{1,19}$";
            final String EMAIL_PATTERN = "^((([a-z]|\\d|[!#\\$%&'\\*\\+\\-\\/=\\?\\^_`{\\|}~]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])+(\\.([a-z]|\\d|[!#\\$%&'\\*\\+\\-\\/=\\?\\^_`{\\|}~]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])+)*)|((\\x22)((((\\x20|\\x09)*(\\x0d\\x0a))?(\\x20|\\x09)+)?(([\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x7f]|\\x21|[\\x23-\\x5b]|[\\x5d-\\x7e]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(\\\\([\\x01-\\x09\\x0b\\x0c\\x0d-\\x7f]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF]))))*(((\\x20|\\x09)*(\\x0d\\x0a))?(\\x20|\\x09)+)?(\\x22)))@((([a-z]|\\d|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(([a-z]|\\d|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])([a-z]|\\d|-|\\.|_|~|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])*([a-z]|\\d|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])))\\.)+(([a-z]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(([a-z]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])([a-z]|\\d|-|\\.|_|~|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])*([a-z]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])))\\.?";
            final String MOBILE_PHONE_NUMBER_PATTERN = "^0{0,1}(13[0-9]|15[0-9]|14[0-9]|18[0-9])[0-9]{8}$";
            NextRobotSysUser nextRobotSysUser = null;
            if(Pattern.matches(EMAIL_PATTERN, userName)){ //用邮箱查找用户
                nextRobotSysUser = this.sysUserService.findByEmail(userName);
            }else if(Pattern.matches(MOBILE_PHONE_NUMBER_PATTERN, userName)){ //用手机查找用户
                nextRobotSysUser = this.sysUserService.findByMobilePhoneNumber(userName);
            }else if(Pattern.matches(USERNAME_PATTERN, userName)){ //用账号查找用户
                nextRobotSysUser = this.sysUserService.findByUserName(userName);
            }else{
                return InvokeResult.failure("请输入合法账号!");
            }
            if(nextRobotSysUser == null)
                return InvokeResult.failure("账号或密码错误!");
            if(nextRobotSysUser != null){
                //查找角色
            }
            usernamePasswordToken = new UsernamePasswordToken(userName, password);
            Subject subject = SecurityUtils.getSubject();
            subject.login(usernamePasswordToken);
            Session shrioSession = subject.getSession();
            session.setAttribute(NtConstants.SESSION_USERNAME, userName);
            session.setAttribute(NtConstants.SESSION_SHIRO_SESSION_ID, shrioSession.getId());
            Map<String, String> map = new HashMap<String, String>();
            return InvokeResult.success("/sys/index");
        }catch (Exception e){
            e.printStackTrace();
            return InvokeResult.failure("账号或密码错误!");
        }
    }
}
