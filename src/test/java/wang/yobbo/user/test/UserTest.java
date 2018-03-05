package wang.yobbo.user.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.apache.shiro.codec.Base64;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
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
import wang.yobbo.common.spring.SpringContextUtil;
import wang.yobbo.sys.entity.NextRobotBusinessTemplate;
import wang.yobbo.sys.entity.NextRobotSysMenu;
import wang.yobbo.sys.entity.NextRobotSysUsers;
import wang.yobbo.sys.service.NextRobotSysMenuService;
import wang.yobbo.sys.service.SysUserService;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.charset.Charset;
import java.security.NoSuchAlgorithmException;
import java.sql.Clob;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
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

    @Autowired
    private SysUserService sysService;

    @Autowired
    NextRobotSysMenuService sysMenuService;

    @Autowired
    private PropertyConfigurer propertyConfigurer;

    private short a;

    @Test
    public void testFindTemplate(){
        NextRobotBusinessTemplate template = this.sysMenuService.findTemplate("402881c261f52fd00161f530a00d0000");
        Clob content = template.getFileContent();
        try {
            Reader characterStream = content.getCharacterStream();
            byte[] bytes = IOUtils.toByteArray(characterStream, Charset.forName("utf-8"));
            System.out.println(new String(bytes));
            characterStream.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCount(){
        Searchable searchable = new Searchable();
        //多条件查询
        searchable.addSearchRule("name", "任", SearchOperator.like);
        searchable.addSearchRule("type", "tab", SearchOperator.eq);  //eq
        Long count = this.sysMenuService.getCount(searchable);
        System.out.println("count: " + count);
    }

    @Test
    public void testGetPage(){
        /*
        SELECT
	nextrobots0_.ID AS ID1_1_,
	nextrobots0_.CREATE_DATE AS CREATE_D2_1_,
	nextrobots0_.UPDATE_DATE AS UPDATE_D3_1_,
	nextrobots0_.ORDER_NUMBER AS ORDER_NU4_1_,
	nextrobots0_.PARENT_ID AS PARENT_I5_1_,
	nextrobots0_.REMARK AS REMARK6_1_,
	nextrobots0_. NAME AS NAME7_1_,
	nextrobots0_.TYPE AS TYPE8_1_,
	nextrobots0_.URL AS URL9_1_
    FROM
        NEXT_ROBOT_SYS_MENU nextrobots0_
    WHERE
        1 = 1
    AND (NAME LIKE ?)
    AND nextrobots0_.TYPE =?
    AND (nextrobots0_.ID IN(?, ?, ?))
    ORDER BY
        nextrobots0_.CREATE_DATE DESC,
        NAME ASC
    LIMIT ?
         */
        //分页
        Pageable pageable = new Pageable(1, 6);
        //排序
        Sortable sortable = new Sortable();
        sortable.add(Sortable.Sort.DESC, "createDate");
        sortable.add(Sortable.Sort.ASC, "name");

        Searchable searchable = new Searchable(pageable, sortable);
        //多条件查询
        searchable.addSearchRule("name", "任", SearchOperator.like);
        searchable.addSearchRule("type", "tab", SearchOperator.eq);  //eq
        String[] ids = new String[]{"402881c261e4b0e50161e4b1978c0001","402881c261e4b0e50161e4b1ed2e0002","402881c261e4b0e50161e4b2224f0003"};
        List<String> strings = Arrays.asList(ids);

        searchable.addSearchRule("id", Arrays.asList(ids), SearchOperator.in); //in

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
        NextRobotSysMenuService sysMenuService = SpringContextUtil.getBean(NextRobotSysMenuService.class);
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
