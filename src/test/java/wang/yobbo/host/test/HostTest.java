package wang.yobbo.host.test;

import com.alibaba.druid.pool.DruidDataSource;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.cfgxml.spi.LoadedConfig;
import org.hibernate.boot.registry.BootstrapServiceRegistryBuilder;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.jpa.boot.internal.EntityManagerFactoryBuilderImpl;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.schema.TargetType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import wang.yobbo.common.appengine.entity.Pageable;
import wang.yobbo.common.appengine.entity.Searchable;
import wang.yobbo.common.appengine.entity.Sortable;
import wang.yobbo.common.appengine.plugin.SearchOperator;
import wang.yobbo.common.spring.PropertyConfigurer;
import wang.yobbo.host.entity.Host;
import wang.yobbo.host.service.NextRobotHostService;
import wang.yobbo.sys.entity.TestEntity;

import java.util.*;

/**
 * Created by xiaoyang on 2018/3/3.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring-root.xml")
public class HostTest {
    @Autowired
    private NextRobotHostService nextRobotHostService;

    @Autowired
    private DruidDataSource dataSource;

    @Autowired
    private PropertyConfigurer propertyConfigurer;

    // 根据dataSource动态创建表
    @Test
    public void testDDl(){
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
        metadataSources.addAnnotatedClassName("wang.yobbo.sys.entity.TestEntity");
        Metadata metadata = metadataSources.buildMetadata();
        SchemaExport export = new SchemaExport();
        export.create(EnumSet.of(TargetType.DATABASE), metadata);


    }

    @Test
    public void testCount(){
        this.nextRobotHostService.count();
    }

    @Test
    public void test_1(){
        List<Host> all = this.nextRobotHostService.findAll();
        printJson(all);
    }

    @Test
    public void test_2(){
        Host nextRobotHost = new Host();
        nextRobotHost.setName("201");
        List<Host> all = this.nextRobotHostService.findAll(nextRobotHost);
        printJson(all);
    }

    @Test
    public void Test_3(){
        Host byId = this.nextRobotHostService.findById("12111111");
        System.out.println(byId);
        printJson(byId);
    }

    @Test
    public void Test_4(){
        Host nextRobotHost = new Host();
        nextRobotHost.setName("23-12");
        nextRobotHost.setIp("192.168.10.10");
//        Host byId = this.nextRobotHostService.save(nextRobotHost);
//        printJson(byId);
    }

    @Test
    public void Test_5(){
        Pageable pageable = new Pageable(1, 20);
        Sortable sortable = new Sortable();
        sortable.add(Sortable.Sort.DESC, "createDate");
        Searchable searchable = new Searchable(pageable, sortable);
        searchable.addSearchRule("name", "20", SearchOperator.like);
        Page<Host> page = this.nextRobotHostService.getPage(searchable);
        printJson(page);
    }

    @Test
    public void Test_6(){
        Pageable pageable = new Pageable(1, 20);
        Sortable sortable = new Sortable();
        sortable.add(Sortable.Sort.DESC, "createDate");
        Searchable searchable = new Searchable(pageable, sortable);
        searchable.addSearchRule("name", "20", SearchOperator.like);
        printJson(this.nextRobotHostService.count(searchable));
    }

    @Test
    public void Test_7(){
        Pageable pageable = new Pageable(1, 20);
        Sortable sortable = new Sortable();
        sortable.add(Sortable.Sort.DESC, "createDate");
        Searchable searchable = new Searchable(pageable, sortable);
        searchable.addSearchRule("name", "20", SearchOperator.like);
        printJson(this.nextRobotHostService.findPageWithoutCount(searchable));
    }

    @Test
    public void Test_8(){
        System.out.println(this.nextRobotHostService.findTemplateByHql("select nextRobotBusinessTemplate from NextRobotBusinessTemplate nextRobotBusinessTemplate", null));
    }

    private void printJson(Object value){
        ObjectMapper mapper = new ObjectMapper();
        try {
            String s = mapper.writeValueAsString(value);
            System.out.println(s);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }



}
