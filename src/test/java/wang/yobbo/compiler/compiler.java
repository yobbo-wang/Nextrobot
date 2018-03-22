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

import java.io.*;
import java.util.*;

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
            //Class<?> clazz = stringCompiler.loadClass("wang.yobbo.sys.entity.CompilerTest", results);

            //获取class流写入到文件中
            OutputStream out = new FileOutputStream("E:\\intelljis workspace\\Nextrobot\\target\\classes\\wang\\yobbo\\sys\\entity\\CompilerTest.class");
            InputStream is = new ByteArrayInputStream(results.get("wang.yobbo.sys.entity.CompilerTest"));
            byte[] buff = new byte[1024];
            int len = 0;
            while( (len=is.read(buff)) != -1){
                out.write(buff, 0, len);
            }
            is.close();
            out.close();


            //调用hibernate底层方法创建表
            Map configuration = new Hashtable();
            configuration.put("hibernate.connection.url", this.dataSource.getUrl());
            configuration.put("hibernate.connection.username", this.dataSource.getUsername());
            configuration.put("hibernate.connection.password", this.dataSource.getPassword());
            configuration.put("hibernate.connection.driver_class", this.dataSource.getDriverClassName());
            configuration.put("hibernate.hbm2ddl.auto" , "create");
            configuration.put("hibernate.dialect", propertyConfigurer.getProperty("jpa.databasePlatform"));
            configuration.put("hibernate.show_sql", true);

            ServiceRegistry registry = new StandardServiceRegistryBuilder().applySettings(configuration).build();
            MetadataSources metadataSources = new MetadataSources(registry);
            metadataSources.addAnnotatedClassName("wang.yobbo.sys.entity.CompilerTest");
            Metadata metadata = metadataSources.buildMetadata();
            SchemaExport export = new SchemaExport();
            export.create(EnumSet.of(TargetType.DATABASE), metadata);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
