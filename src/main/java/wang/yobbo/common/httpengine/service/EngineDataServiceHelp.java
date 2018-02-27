package wang.yobbo.common.httpengine.service;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.druid.util.StringUtils;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class EngineDataServiceHelp {
    public static final int  RESULT_CODE_SUCCESS        = 1;
    public static final int  RESULT_CODE_ERROR          = -1;

    /**
     * 获取url路径中的参数，放到map中
     * 如果url中参数为空，直接返回空集合
     * @param url
     * @return
     */
    public Map<String, String> getParameters(String url) {
        if (url == null || (url = url.trim()).length() == 0) {
            return Collections.<String, String> emptyMap();
        }

        String parametersStr = StringUtils.subString(url, "?", null);
        if (parametersStr == null || parametersStr.length() == 0) {
            return Collections.<String, String> emptyMap();
        }

        String[] parametersArray = parametersStr.split("&");
        Map<String, String> parameters = new LinkedHashMap<String, String>();

        for (String parameterStr : parametersArray) {
            int index = parameterStr.indexOf("=");
            if (index <= 0) {
                continue;
            }

            String name = parameterStr.substring(0, index);
            String value = parameterStr.substring(index + 1);
            parameters.put(name, value);
        }
        return parameters;
    }

    /**
     * 返回ajax结果，前端统一以这种格式来解析
     * @param resultCode 返回处理结果的状态，1：成功,2：失败
     * @param content 返回的内容，可以是任意类型的对象
     * @return
     */
    public static String returnJSONResult(int resultCode, Object content) {
        Map<String, Object> dataMap = new LinkedHashMap<String, Object>();
        dataMap.put("ResultCode", resultCode);
        dataMap.put("Content", content);
        return JSONUtils.toJSONString(dataMap);
    }
}
