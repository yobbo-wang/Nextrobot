package wang.yobbo.common.entity;

import org.springframework.data.domain.Sort;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 框架排序规则
 * 用法：
 *      new SortAble(Sort.ASC, "name");
 */
public class SortAble implements Serializable{

    public enum Sort {
        ASC, DESC;
    }

    public SortAble(){
    }

    public SortAble(Sort sortRelu, String name){
        if("ASC".equals(sortRelu.name())){
            orders.add(new org.springframework.data.domain.Sort.Order(org.springframework.data.domain.Sort.Direction.ASC,name));
        }else{
            orders.add(new org.springframework.data.domain.Sort.Order(org.springframework.data.domain.Sort.Direction.DESC,name));
        }
    }

    public void add(Sort sortRelu, String name){
        if("ASC".equals(sortRelu.name())){
            orders.add(new org.springframework.data.domain.Sort.Order(org.springframework.data.domain.Sort.Direction.ASC,name));
        }else{
            orders.add(new org.springframework.data.domain.Sort.Order(org.springframework.data.domain.Sort.Direction.DESC,name));
        }
    }

    public void add(String name){
        orders.add(new org.springframework.data.domain.Sort.Order(org.springframework.data.domain.Sort.Direction.ASC,name));
    }

    private List<org.springframework.data.domain.Sort.Order> orders = new ArrayList<org.springframework.data.domain.Sort.Order>();

    public List<org.springframework.data.domain.Sort.Order> getOrders() {
        return orders;
    }
}
