package wang.yobbo.common.appengine.plugin;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

public class EntityUtils {

    private static final Logger log = LoggerFactory.getLogger(EntityUtils.class);
    private static Map<String, Field[]> entityFields = new HashMap();

    public EntityUtils() {
    }

    public static Boolean isNotNull(Object obj) {
        return obj != null;
    }

    public static void getAllDeclaredFields(Class<?> clazz, List<Field> result) {
        if (result == null) {
            result = new ArrayList();
        }

        Field[] fields = clazz.getDeclaredFields();
        Collections.addAll((Collection)result, fields);
        Class<?> superClazz = clazz.getSuperclass();
        if (superClazz != Object.class) {
            getAllDeclaredFields(superClazz, (List)result);
        }

    }

    public static Field[] getClassFields(Class<?> clazz, boolean isself) {
        Field[] fs = null;
        fs = (Field[])entityFields.get(clazz.getName());
        if (fs == null) {
            if (isself) {
                fs = clazz.getDeclaredFields();
            } else {
                List<Field> fields = new ArrayList();
                getAllDeclaredFields(clazz, fields);
                fs = new Field[fields.size()];
                fields.toArray(fs);
            }

            if (fs != null) {
                entityFields.put(clazz.getName(), fs);
            }
        }

        return fs;
    }

    public static Field getField(String fieldName, Class<?> clazz) {
        Field[] fs = getClassFields(clazz, false);
        if (fs != null) {
            Field[] var3 = fs;
            int var4 = fs.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                Field field = var3[var5];
                if (field.getName().equals(fieldName)) {
                    return field;
                }
            }
        }

        return null;
    }

    public static Class<?> getFileType(Field field) {
        Class<?> clazz = null;
        clazz = field.getType();
        return clazz;
    }

    public static Class<?> getClassByName(String className) {
        Class clazz = null;

        try {
            clazz = Class.forName(className);
        } catch (ClassNotFoundException var3) {
            var3.printStackTrace();
            log.error("获取Class失败，失败原因:" + var3.getMessage());
        }

        return clazz;
    }

    public static Object invokeMethod(Object owner, String methodName, Class[] argsClass, Object[] args) {
        Class<?> ownerClass = owner.getClass();
        if (argsClass == null && args != null) {
            argsClass = new Class[args.length];

            for(int i = 0; i < args.length; ++i) {
                if (args[i] != null) {
                    if ("java.lang.Bollean".equals(args[i].getClass())) {
                        argsClass[i] = Boolean.TYPE;
                    } else {
                        argsClass[i] = args[i].getClass();
                    }
                } else {
                    argsClass[i] = Object.class;
                }
            }
        }

        Method method = null;

        try {
            method = ownerClass.getMethod(methodName, argsClass);
        } catch (Exception var9) {
            log.error("获取类函数失败" + var9.getMessage());
        }

        Object obj = null;

        try {
            obj = method.invoke(owner, args);
        } catch (Exception var8) {
            log.error("执行" + ownerClass.getName() + "类中的" + method.getName() + "函数失败");
        }

        return obj;
    }

    public static void invokeSetMethod(Object model, String keyName, Class[] argClazzs, Object[] args) {
        String setMethodName = "set" + keyName.substring(0, 1).toUpperCase() + keyName.substring(1);
        invokeMethod(model, setMethodName, argClazzs, args);
    }

    public static <T> T getPropertyValue(Object model, String proName) {
        String getMethodName = "get" + proName.substring(0, 1).toUpperCase() + proName.substring(1);
        T obj = (T) invokeMethod(model, getMethodName, (Class[])null, (Object[])null);
        return obj;
    }

    public static <T> T getSimpleFieldVavlueByName(Object model, String proName) {
        return model != null && !StringUtils.isBlank(proName) ? (T) getPropertyValue(model, proName) : null;
    }

    public static <T> T getSimpleFieldVavlue(Object model, Field field) {
        String propName = field.getName();
        return getSimpleFieldVavlueByName(model, propName);
    }

    public static void copyProperties(Object source, Object target) {
        BeanUtils.copyProperties(source, target);
    }
}
