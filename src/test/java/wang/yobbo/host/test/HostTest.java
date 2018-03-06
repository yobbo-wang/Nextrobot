package wang.yobbo.host.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import wang.yobbo.host.entity.NextRobotHost;
import wang.yobbo.host.service.NextRobotHostService;

import java.util.List;

/**
 * Created by xiaoyang on 2018/3/3.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring-root.xml")
public class HostTest {
    @Autowired
    private NextRobotHostService nextRobotHostService;

    @Test
    public void testCount(){
        this.nextRobotHostService.count();
    }

    @Test
    public void test_1(){
        List<NextRobotHost> all = this.nextRobotHostService.findAll();
        printJson(all);
    }

    @Test
    public void test_2(){
        NextRobotHost nextRobotHost = new NextRobotHost();
        nextRobotHost.setName("201");
        List<NextRobotHost> all = this.nextRobotHostService.findAll(nextRobotHost);
        printJson(all);
    }

    @Test
    public void Test_3(){
        NextRobotHost byId = this.nextRobotHostService.findById("12111111");
        System.out.println(byId);
        printJson(byId);
    }

    @Test
    public void Test_4(){
        NextRobotHost nextRobotHost = new NextRobotHost();
        nextRobotHost.setName("23-12");
        nextRobotHost.setIp("192.168.10.10");
        NextRobotHost byId = this.nextRobotHostService.save(nextRobotHost);
        printJson(byId);
    }

    @Test
    public void Test_5(){
        Pageable pageable = new Pageable(1, 20);
        Sortable sortable = new Sortable();
        sortable.add(Sortable.Sort.DESC, "createDate");
        Searchable searchable = new Searchable(pageable, sortable);
        searchable.addSearchRule("name", "20", SearchOperator.like);
        Page<NextRobotHost> page = this.nextRobotHostService.getPage(searchable);
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
