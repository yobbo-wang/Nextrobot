package wang.yobbo.compiler;

import com.alibaba.druid.pool.DruidDataSource;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.schema.TargetType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import wang.yobbo.common.appengine.compile.JavaStringCompiler;
import wang.yobbo.common.spring.PropertyConfigurer;

import java.io.IOException;
import java.util.EnumSet;
import java.util.Hashtable;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring-root.xml")
public class compiler {
    private final static String CODE = "package wang.yobbo.sys.entity;       "
            + "import wang.yobbo.common.appengine.entity.BaseEntity;                            "
            + "import javax.persistence.Entity;"
            + "@Entity                                                      "
            + "public class CompilerTest extends BaseEntity<String> {     "
            + "    private String name;                                  "
            + "    public void setName(String name) {                             "
            + "        this.name = name;                                      "
            + "    }                                                          "
            + "    public String getName() {                         "
            + "        return this.name;                                  "
            + "    }                                                          "
            + "}                                                              ";

    @Autowired
    private DruidDataSource dataSource;

    @Autowired
    private PropertyConfigurer propertyConfigurer;

    @Test
    public void dd(){
        JavaStringCompiler stringCompiler = JavaStringCompiler.getInstance();  //初始化编译容器
        try {
            Map<String, byte[]> results = stringCompiler.compile("CompilerTest.java", CODE);  //编译java代码，放到内存中
            Class<?> clazz = stringCompiler.loadClass("wang.yobbo.sys.entity.CompilerTest", results);

            //TODO 一下代码失败。待找原因
            Class<?> aClass = Class.forName("wang.yobbo.sys.entity.CompilerTest");

            System.out.println(aClass);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

}
