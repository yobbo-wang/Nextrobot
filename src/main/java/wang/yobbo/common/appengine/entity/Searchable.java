package wang.yobbo.common.appengine.entity;

import org.apache.commons.lang3.StringUtils;
import wang.yobbo.common.appengine.plugin.SearchOperator;
import wang.yobbo.common.exception.SearchException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 条件查询、分页和排序公共封装
 */
public class Searchable {
    private List<Map<String, SearchRule>> searchList = new ArrayList<Map<String, SearchRule>>();
    private Pageable page;
    private Sortable sort;
    public Searchable(){ }

    public Searchable(Pageable pageable, Sortable sortable){
        this.sort = sortable;
        this.page = pageable;
    }

    public Searchable(Pageable pageable){
        this.page = pageable;
    }

    public Searchable(Sortable sortable){
        this.sort = sortable;
    }

    private void setSearchList(SearchRule searchRule){
        if(searchRule == null || StringUtils.isEmpty(searchRule.getKey())|| searchRule.getValue() == null || searchRule.getSearchOperator() == null)
        {
            return;
        }
        Map<String, SearchRule> map = new HashMap<String, SearchRule>();
        map.put(searchRule.getKey(), searchRule);
        this.searchList.add(map);
    }

    public Searchable addSearchRule(String key, Object value, SearchOperator searchOperator) throws SearchException {
        SearchRule searchRule = new SearchRule();
        searchRule.setKey(key);
        searchRule.setValue(value);
        searchRule.setSearchOperator(searchOperator);
        this.setSearchList(searchRule);
        return this;
    }

    public  boolean hasPageable() {
        return this.page != null && this.page.getSize() > 0;
    }

    public Pageable getPage() {
        return page;
    }

    public Sortable getSort() {
        return sort;
    }

    public List<Map<String, SearchRule>> getSearchList() {
        return searchList;
    }

    public boolean hasSearchFilter() {
        return this.searchList.size() > 0;
    }
}
