package wang.yobbo.common.httpengine.service;

import com.alibaba.druid.VERSION;

import javax.servlet.ServletContext;
import java.util.LinkedHashMap;
import java.util.Map;

public class EngineDataService extends EngineDataServiceHelp{
    private static EngineDataService instance                      = null;
    private static EngineDataManagerFacade engineDataManagerFacade  = EngineDataManagerFacade.getInstance(); //获取具体数据操作类
    private EngineDataService(){}
    public static EngineDataService getInstance(){
        if (instance == null) {
            instance = new EngineDataService();
        }
        return instance;
    }

    /**
     * 获取模板中数据,返回给模板
     * @param url 前端发送的请求
     * @param parameters URL中的参数列表
     * @return 返回模板中需要的数据
     */
    public Object processTemplate(String url, Map<String, String> parameters){
        Map<String,Object> basicInfo = getBasicInfo(parameters);
        if (url.startsWith("/index.html")) { //首页信息
            return  engineDataManagerFacade.getIndexInfo(basicInfo);
        }else if(url.startsWith("/menu/index.html")){
            return engineDataManagerFacade.getMenuInfo(basicInfo);
        }else if(url.startsWith("/menu/entity.html")){
            return engineDataManagerFacade.getMenuTableInfo(basicInfo);
        }
        return null;
    }

    /**
     * 组合工具数据
     */
    public Map<String,Object> getBasicInfo(Map<String,String> params){
        Map<String,Object> dataMap = new LinkedHashMap<String,Object>();
        dataMap.putAll(params);
        dataMap.put("Version", VERSION.getVersionNumber());
        return dataMap;
    }

    /**
     * 调用服务，ajax异步相关的，只要是ajax请求都会走process这个方法去处理
     * @param url
     * @param context 上下文
     * @return
     */
    public String process(String url,ServletContext context){
        Map<String, String> parameters = getParameters(url);
        if (url.startsWith("/index.json")) {
            return returnJSONResult(RESULT_CODE_SUCCESS, "");
        }
        return returnJSONResult(RESULT_CODE_ERROR, "Do not support this request, please contact with administrator.");
    }
}
