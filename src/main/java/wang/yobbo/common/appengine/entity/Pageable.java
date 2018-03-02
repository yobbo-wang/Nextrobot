package wang.yobbo.common.appengine.entity;

import java.io.Serializable;

/**
 * 分页和排序
 */
public class Pageable implements Serializable{
    private Integer page;
    private Integer size;
    private Integer total;

    public Pageable(){ }

    public Pageable(Integer page, Integer size){
        this.page = page;
        this.size = size;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
