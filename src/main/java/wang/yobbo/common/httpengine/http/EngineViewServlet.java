package wang.yobbo.common.httpengine.http;


import com.alibaba.druid.util.Utils;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.jetbrains.annotations.Contract;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import wang.yobbo.common.httpengine.service.EngineDataService;
import wang.yobbo.common.spring.PropertyConfigurer;
import wang.yobbo.common.spring.SpringContextUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * servlet读取静态文件响应
 */
public class EngineViewServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = LoggerFactory.getLogger(EngineViewServlet.class);

    private final static String RESOURCE_PATH            = "engine/http/resources";
    private static String jar_path                         = null;
    private String webAppPath				                 = null;
    private static String base_path						         = null;
    private String table_prefix_name                      = null;
    private static String entity_prefix_name                = null;
    private String jar_version                             = null;
    private static String package_name					         = null;
    private Configuration configuration                    = null;

    @Override
    public void init(ServletConfig config) {
        configuration = new Configuration(Configuration.getVersion()); // 创建模板
        try {
            configuration.setDefaultEncoding("UTF-8");
            configuration.setClassForTemplateLoading(this.getClass(),"/");
            PropertyConfigurer propertyConfigurer = SpringContextUtil.getBean(PropertyConfigurer.class);
            jar_version = propertyConfigurer.getProperty("system.jar.version");
            package_name = propertyConfigurer.getProperty("system.package.name");
            table_prefix_name = propertyConfigurer.getProperty("system.table.prefix.name");
            entity_prefix_name = propertyConfigurer.getProperty("system.entity.prefix.name");
            base_path = propertyConfigurer.getProperty("system.base.path");
            webAppPath = config.getServletContext().getRealPath("/");
            jar_path = webAppPath + "WEB-INF/lib/appEngine-"+this.jar_version+".jar"; // 最终版，发布时去掉注释
        } catch (Exception e) {
            e.printStackTrace();
            LOG.info("创建模板失败!");
    }
        LOG.info("engineViewServlet初始化成功!");
    }

    protected void returnResourceFile(String fileName, String uri,HttpServletResponse response)
            throws IOException
    {
        String filePath = getFilePath(fileName);

        if (fileName.endsWith(".woff")) {
            response.setContentType("application/x-font-woff");
        } else if (fileName.endsWith(".ttf")){
            response.setContentType("application/octet-stream");
        } else if (fileName.endsWith(".svg")) {
            response.setContentType("image/svg+xml");
        } else if (fileName.endsWith(".eot")) {
            response.setContentType("application/vnd.ms-fontobject");
        }

        if (fileName.endsWith(".jpg")
                || fileName.endsWith(".png")
                || fileName.endsWith(".gif")
                || fileName.endsWith(".woff")
                || fileName.endsWith(".ttf")
                || fileName.endsWith(".svg")
                || fileName.endsWith(".eot")) {
            byte[] bytes = Utils.readByteArrayFromResource(filePath);
            if (bytes != null) {
                response.getOutputStream().write(bytes);
            }
            return;
        }

        String text = Utils.readFromResource(filePath);
        if (text == null) {
            response.sendRedirect(uri + "/index.html"); // 继续跳转到service方法中，进入returnTemplateHtml中输出模板
            return;
        }
        if (fileName.endsWith(".css")) {
            response.setContentType("text/css;charset=utf-8");
        } else if (fileName.endsWith(".js")) {
            response.setContentType("text/javascript;charset=utf-8");
        }
        response.getWriter().write(text);
    }

    public void service(HttpServletRequest request, HttpServletResponse response)
            throws IOException
    {
        String contextPath = request.getContextPath();
        String servletPath = request.getServletPath();
        String requestURI = request.getRequestURI();

        response.setCharacterEncoding("utf-8");
        if (contextPath == null) { // root context
            contextPath = "";
        }
        String uri = contextPath + servletPath;
        String path = requestURI.substring(contextPath.length() + servletPath.length());

        // 这种情况 跳转freeMark模板
        if ("".equals(path)) {
            if (contextPath.equals("") || contextPath.equals("/")) {
                response.sendRedirect(servletPath+"/index.html"); //转发到service中的.html中
            } else {
                response.sendRedirect(servletPath.substring(1)+"/index.html");
            }
            return;
        }
        // 这种情况，跳转freeMark模板
        if ("/".equals(path)) {
            response.sendRedirect("index.html");
            return;
        }

        if(path.endsWith(".html")){
            String fullUrl = path;
            if (request.getQueryString() != null && request.getQueryString().length() > 0) {
                fullUrl += "?" + request.getQueryString();
            }
            response.setContentType("text/html; charset=utf-8");
            returnTemplateHtml(path,fullUrl,request.getServletContext(),response);
            return;
        }

        if (path.contains(".json")) {
            String fullUrl = path;
            if (request.getQueryString() != null && request.getQueryString().length() > 0) {
                fullUrl += "?" + request.getQueryString();
            }
            response.getWriter().print(EngineDataService.getInstance().process(fullUrl,request.getServletContext()));
            return;
        }

        // 其他静态文件
        returnResourceFile(path, uri, response);
    }

    /**
     * 静态页面，生成模板并跳转到前台
     * @param path 静态文件在资源中的路径
     * @param url 请求的当前路径
     * @param servletContext
     * @param response
     * @throws IOException
     */
    protected void returnTemplateHtml(String path, String url, ServletContext servletContext, HttpServletResponse response)
            throws IOException {
        String filePath = getFilePath(path);
        try {
            // 获取相应数据
            Map<String, String> params = EngineDataService.getInstance().getParameters(url);
            if (params.size() ==0) {
                params = new HashMap<String, String>();
            }
            params.put("base_path",base_path);
            params.put("package_name",package_name);
            params.put("table_prefix_name", table_prefix_name);
            params.put("entity_prefix_name", entity_prefix_name);
            params.put("contextPath", servletContext.getContextPath());
            Object data = EngineDataService.getInstance().processTemplate(path, params);

            Template template = configuration.getTemplate(filePath);
            Writer writer = response.getWriter();
            template.process(data, writer);
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取jar包中的文件名
     * @param fileName 文件名
     * @return
     */
    protected String getFilePath(String fileName) {
        return RESOURCE_PATH + fileName;
    }

    @Contract(pure = true)
    public static String getBase_path() {
        return base_path;
    }

    @Contract(pure = true)
    public static String getPackage_name() {
        return package_name;
    }

    @Contract(pure = true)
    public static String getJar_path() {
        return jar_path;
    }

    public void setJar_version(String jar_version) {
        this.jar_version = jar_version;
    }

    @Contract(pure = true)
    public static String getEntity_prefix_name() {
        return entity_prefix_name;
    }
}
