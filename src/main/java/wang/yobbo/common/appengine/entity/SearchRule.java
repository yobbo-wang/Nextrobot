package wang.yobbo.common.appengine.entity;

import wang.yobbo.common.appengine.plugin.SearchOperator;

import java.io.Serializable;

public class SearchRule implements Serializable{
    private String key;
    private Object value;
    private SearchOperator searchOperator;

    public SearchRule(){
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public SearchOperator getSearchOperator() {
        return searchOperator;
    }

    public void setSearchOperator(SearchOperator searchOperator) {
        this.searchOperator = searchOperator;
    }
}
