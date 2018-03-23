package wang.yobbo.compiler;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.commons.lang3.ClassUtils;
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

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration("classpath:spring/spring-root.xml")
public class compiler {
    private final static String CODE = "package wang.yobbo.message.entity;\n" +
            "\n" +
            "import wang.yobbo.common.appengine.entity.BaseEntity;\n" +
            "import javax.persistence.*;\n" +
            "    /**\n" +
            "     * 此文件由应用引擎生成，严禁修改，如重复生成时，该文件将被覆盖。\n" +
            "     * Message 实体信息\n" +
            "     * 应用引擎消息管理\n" +
            "     * @author 应用引擎自动生成\n" +
            "     * @date 2018-03-23 09:57:27\n" +
            "     *\n" +
            "     */\n" +
            "    @Entity\n" +
            "    @Table(name = \"NR_MESSAGE\")\n" +
            "    public class Message extends BaseEntity<String> {\n" +
            "        private static final long serialVersionUID = 1L;\n" +
            "\n" +
            "        @Column(name = \"NAME\",length = 30 )\n" +
            "        private String name;\n" +
            "\n" +
            "        @Column(name = \"MASSAGE\")\n" +
            "        private java.sql.Clob massage;\n" +
            "\n" +
            "        @Column(name = \"STATUS\",length = 2 )\n" +
            "        private String status;\n" +
            "\n" +
            "        public String getName(){\n" +
            "            return name;\n" +
            "        }\n" +
            "\n" +
            "        public void setName(String name){\n" +
            "            this.name = name;\n" +
            "        }\n" +
            "\n" +
            "        public java.sql.Clob getMassage(){\n" +
            "            return massage;\n" +
            "        }\n" +
            "\n" +
            "        public void setMassage(java.sql.Clob massage){\n" +
            "            this.massage = massage;\n" +
            "        }\n" +
            "\n" +
            "        public String getStatus(){\n" +
            "            return status;\n" +
            "        }\n" +
            "\n" +
            "        public void setStatus(String status){\n" +
            "            this.status = status;\n" +
            "        }\n" +
            "\n" +
            "    }";

//    @Autowired
//    private DruidDataSource dataSource;
//
//    @Autowired
//    private PropertyConfigurer propertyConfigurer;

    private String classpath = null;
    private java.net.URLClassLoader parentClassLoader;

    /**
     * @MethodName    : 创建classpath
     */
    private void buildClassPath() {
        this.parentClassLoader = (java.net.URLClassLoader) this.getClass().getClassLoader();
        this.classpath = null;
        StringBuilder sb = new StringBuilder();
        for (java.net.URL url : this.parentClassLoader.getURLs()) {
            String p = url.getFile();
            sb.append(p).append(File.pathSeparator);
        }
        this.classpath = sb.toString();
    }

    @Test
    public void dd(){

//        this.buildClassPath();
//        JavaStringCompiler stringCompiler = JavaStringCompiler.getInstance();  //初始化编译容器
        try {
//            Iterable<String> options = Arrays.asList("-encoding", "UTF-8",
//                    "-classpath", this.classpath,
//                    "-d", "/D:/ideaWorkspace/Nextrobot/target/Nextrobot/WEB-INF/classes/",
//                    "-sourcepath", "/D:/ideaWorkspace/Nextrobot/target/Nextrobot/WEB-INF/classes/");
//            Map<String, byte[]> results = stringCompiler.compile("Message.java", CODE, options);  //编译java代码，放到内存中
//            //Class<?> clazz = stringCompiler.loadClass("wang.yobbo.sys.entity.CompilerTest", results);

            //获取class流写入到文件中
//            OutputStream out = new FileOutputStream("E:\\intelljis workspace\\Nextrobot\\target\\classes\\wang\\yobbo\\sys\\entity\\CompilerTest.class");
//            InputStream is = new ByteArrayInputStream(results.get("wang.yobbo.sys.entity.CompilerTest"));
//            byte[] buff = new byte[1024];
//            int len = 0;
//            while( (len=is.read(buff)) != -1){
//                out.write(buff, 0, len);
//            }
//            is.close();
//            out.close();

            //调用hibernate底层方法创建表
//            Map configuration = new Hashtable();
//            configuration.put("hibernate.connection.url", this.dataSource.getUrl());
//            configuration.put("hibernate.connection.username", this.dataSource.getUsername());
//            configuration.put("hibernate.connection.password", this.dataSource.getPassword());
//            configuration.put("hibernate.connection.driver_class", this.dataSource.getDriverClassName());
//            configuration.put("hibernate.hbm2ddl.auto" , "create");
//            configuration.put("hibernate.dialect", propertyConfigurer.getProperty("jpa.databasePlatform"));
//            configuration.put("hibernate.show_sql", true);
//
//            ServiceRegistry registry = new StandardServiceRegistryBuilder().applySettings(configuration).build();
//            MetadataSources metadataSources = new MetadataSources(registry);
//            metadataSources.addAnnotatedClassName("wang.yobbo.sys.entity.CompilerTest");
//            Metadata metadata = metadataSources.buildMetadata();
//            SchemaExport export = new SchemaExport();
//            export.create(EnumSet.of(TargetType.DATABASE), metadata);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
