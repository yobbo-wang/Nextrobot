package wang.yobbo.user.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.shiro.codec.Base64;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import wang.yobbo.common.entity.PageAble;
import wang.yobbo.common.entity.Searchable;
import wang.yobbo.common.entity.SortAble;
import wang.yobbo.common.spring.PropertyConfigurer;
import wang.yobbo.common.spring.SpringContextUtil;
import wang.yobbo.sys.entity.NextRobotSysMenu;
import wang.yobbo.sys.entity.NextRobotSysUsers;
import wang.yobbo.sys.service.SysMenuService;
import wang.yobbo.sys.service.SysUserService;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by xiaoyang on 2017/12/28.
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring-root.xml")
public class UserTest {

    public static void main(String[] s) {
        KeyGenerator keygen = null;
        try {
            keygen = KeyGenerator.getInstance("AES");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        SecretKey deskey = keygen.generateKey();
        System.out.println(Base64.encodeToString(deskey.getEncoded()));

    }

    /**
     * 自定义生成表
     */
//    @Test
    public void createTable(){
        org.hibernate.cfg.Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(NextRobotSysMenu.class);
        configuration.setProperty("hibernate.dialect", propertyConfigurer.getProperty("jpa.databasePlatform")); //设置数据库方言
        configuration.setProperty("hibernate.hbm2ddl.auto", "create");
        configuration.setProperty("showSql", "true");

        SchemaExport schemaExport = new SchemaExport(configuration);
        boolean script = true;
        boolean export = true;
        /**
         * 创建表结构
         * 第一个参数script的作用：print the DDL to the console
         * 第二个参数export的作用：export the script to the database
         */
        schemaExport.create(script, export);
        // drop 表结构
        // schemaExport.drop(script, export);
    }

    @Autowired
    private SysUserService sysService;

    @Autowired SysMenuService sysMenuService;

    @Autowired
    private PropertyConfigurer propertyConfigurer;

    private short a;

    @Test
    public void testGetPage(){
        Searchable searchable = new Searchable(null, new PageAble(1,2), new SortAble(SortAble.Sort.DESC, "createDate"));
        Page<NextRobotSysMenu> page = this.sysMenuService.getPage(searchable);
        System.out.println("TotalElements:" + page.getTotalElements());
        System.out.println("TotalPages:" + page.getTotalPages());
        System.out.println("list<NextRobotSysMenu>:" + page.getContent());
        System.out.println("getNumber:" + page.getNumber());
        System.out.println("getSort:" + page.getSort());

        //object -> json
        ObjectMapper mapper = new ObjectMapper();
        try {
            String s = mapper.writeValueAsString(page);
            System.out.println("json:" + s);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetBean(){
        SysMenuService sysMenuService = SpringContextUtil.getBean(SysMenuService.class);
        NextRobotSysMenu sysMenu = sysMenuService.findById("12345678");
        ObjectMapper mapper = new ObjectMapper();
        try {
            String json = mapper.writeValueAsString(sysMenu);
            System.out.println("序列化:" + json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }

//    @Test
    public void testFindBySql(){
        String sql = " SELECT * FROM act_re_model where ID_ = ?1 and CREATE_TIME_ >= ?2 and CREATE_TIME_ <= ?3";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            NextRobotSysUsers nextRobotSysUsers = this.sysService.findBySqlOne(sql, 1, sdf.parse("2017-1-1").getTime(),
                    new Timestamp(System.currentTimeMillis()));
            System.out.println(nextRobotSysUsers);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testFindCountBySql(){
        Long count = this.sysService.findBySqlCount("SELECT * FROM NEXT_ROBOT_SYS_USERS");
        System.out.println(count);
    }

    @Test
    public void testFindAllBySql(){
        List<NextRobotSysUsers> sss = this.sysService.findAllBySql("SELECT * FROM NEXT_ROBOT_SYS_USERS");
        System.out.println(sss);
    }

    @Test
    public void testOne(){
        NextRobotSysUsers nextRobotSysUsers = this.sysService.findBySqlOne("SELECT * FROM NEXT_ROBOT_SYS_USERS");
        System.out.println(nextRobotSysUsers);
    }

    @Test
    public void findBySqlCount(){
        System.out.println(this.sysService.findBySqlCount("SELECT * FROM act_hi_actinst"));
    }

    @Test
    public void findAll(){
        List<NextRobotSysUsers> all = this.sysService.findUserAll();
        for (NextRobotSysUsers user : all){
            System.out.println(user.getName());
        }
    }

    @Test
    public void getCount(){
        Searchable searchable = new Searchable();
//        searchable.addSearchAttribute(");
        long count = this.sysService.getCount(searchable);
        System.out.println(count);
    }

//    @Test
    public void delete(){
        int count = this.sysService.deleteByPrimaryKeys("2c9f8c0b61419751016141975d0e0000", "2c9f8c0b6141846b0161418474c50000");
        System.out.println(count);
    }

    @Test
    public void deleteByEntity(){
        NextRobotSysUsers user = new NextRobotSysUsers();
        user.setId("2c9f8c0b614183c201614183cbfe0000");
        this.sysService.deleteForUser(user);
    }

    @Test
    public void update(){
//        User user = new User();
//        user.setId("2c9f8c0b614183c201614183cbfe0000");
//        user.setUsername("xioayang2");
//        user.setName("121笑傲1212");
//        user.setPwd("111111");
//        User update = this.sysService.update(user);

    }

//    @Test
    public void save(){
//        User user = new User();
//        user.setUsername("xioayang12121");
//        user.setName("笑傲");
//        user.setPwd("12321321");
//        User save = this.sysService.save(user);

    }

}
