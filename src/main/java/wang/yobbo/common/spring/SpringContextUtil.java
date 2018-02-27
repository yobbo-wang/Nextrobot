package wang.yobbo.common.spring;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Spring工具类
 */
public class SpringContextUtil implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    @Contract(pure = true)
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    //提供set方法自动注入
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextUtil.applicationContext = applicationContext;
    }
    //通过名称获取Bean
    @Nullable
    public static <T> T getBean(String name) throws BeansException {
        if(!checkApplication()) return null;
        Object bean = applicationContext.getBean(name);
        if(bean != null)
        {
            return (T)bean;
        }
        return null;
    }

    //通过Class获取Bean
    @Nullable
    public static <T> T getBean(Class<?> _class){
        if(!checkApplication()) return null;
        Object bean = applicationContext.getBean(_class);
        if(bean != null)
        {
            return (T)bean;
        }
        return null;
    }

    @Contract(pure = true)
    public static boolean checkApplication(){
        if(applicationContext == null){
            return false;
        }
        return true;
    }

}
