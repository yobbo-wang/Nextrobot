package wang.yobbo.common.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.util.Properties;

/**
 * 自定义类，获取配置文件
 * 前提，在spring配置文件中，已把配置文件加载进去
 * 使用方式，在用到的类中，使用@Autowired注入即可
 */
public class PropertyConfigurer extends PropertyPlaceholderConfigurer {
    private Properties props;

    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props)
            throws BeansException {
        super.processProperties(beanFactoryToProcess, props);
        this.props = props;
    }

    public String getProperty(String key){
        return this.props.getProperty(key);
    }

    public String getProperty(String key, String defaultValue) {
        return this.props.getProperty(key, defaultValue);
    }

    public Object setProperty(String key, String value) {
        return this.props.setProperty(key, value);
    }

}
