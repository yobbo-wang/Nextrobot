package wang.yobbo.host.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import wang.yobbo.host.service.NextRobotHostService;

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

}
