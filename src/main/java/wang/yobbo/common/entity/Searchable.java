package wang.yobbo.common.entity;

import com.google.common.collect.Maps;
import wang.yobbo.common.exception.SearchException;

import java.util.Iterator;
import java.util.Map;

/**
 * 条件查询、分页和排序公共封装
 */
public class Searchable {
    private Map<String, Object> searchFilterMap;
    private PageAble page;
    private SortAble sort;
    public Searchable(){ }

    public Searchable(Map<String, Object> searchParams){

    }
    public Searchable(Map<String, Object> searchParams, PageAble pageAble){

    }
    public Searchable(Map<String, Object> searchParams, SortAble sortAble){

    }
    public Searchable(Map<String, Object> searchParams,PageAble pageAble, SortAble sortAble){
        this.searchFilterMap = Maps.newHashMap();
        this.toSearchFilters(searchParams);
        this.page = pageAble;
        this.sort = sortAble;
    }

    private void toSearchFilters(Map<String, Object> searchParams) throws SearchException {
        if (searchParams != null && searchParams.size() != 0) {
            Iterator var2 = searchParams.entrySet().iterator();
            while(var2.hasNext()) {
                Map.Entry<String, Object> entry = (Map.Entry)var2.next();
                String key = (String)entry.getKey();
                Object value = entry.getValue();
                this.searchFilterMap.put(key, value);
            }
        }
    }

    public Searchable addSearchParam(String key, Object value) throws SearchException {
        this.searchFilterMap.put(key, value);
        return this;
    }

    public Searchable addSearchParams(Map<String, Object> searchParams) throws SearchException {
        this.toSearchFilters(searchParams);
        return this;
    }

    public  boolean hasPageable() {
        return this.page != null && this.page.getSize() > 0;
    }

    public Map<String, Object> getSearchFilterMap() {
        return searchFilterMap;
    }

    public PageAble getPage() {
        return page;
    }

    public SortAble getSort() {
        return sort;
    }
}
