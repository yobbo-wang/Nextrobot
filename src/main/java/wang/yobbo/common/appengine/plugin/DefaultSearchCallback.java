package wang.yobbo.common.appengine.plugin;

import wang.yobbo.common.appengine.entity.SearchRule;
import wang.yobbo.common.appengine.entity.Searchable;

import javax.persistence.Query;
import java.util.List;
import java.util.Map;

public class DefaultSearchCallback implements SearchCallback{
    public void prepareHQL(StringBuilder hql, Searchable searchable) {
        if (searchable.hasSearchFilter()) {
            List<Map<String, SearchRule>> searchList = searchable.getSearchList();
            for(int i=0;i<searchList.size();i++){
                Map<String, SearchRule> searchRuleMap = searchList.get(i);
                for (Map.Entry<String, SearchRule> entry : searchRuleMap.entrySet()) {
                    String key = entry.getKey();
                    SearchRule searchRule = entry.getValue();
                    SearchOperator operator = searchRule.getSearchOperator();
                    hql.append(" and ").append(key).append(" ").append(searchRule.getSearchOperator().getRule());
                    hql.append(":param_").append(key).append(i+1);
                }
            }
        }
    }

    public void setValues(Query query, Searchable searchable) {
        List<Map<String, SearchRule>> searchList = searchable.getSearchList();
        for(int i=0;i<searchList.size();i++){
            Map<String, SearchRule> searchRuleMap = searchList.get(i);
            for (Map.Entry<String, SearchRule> entry : searchRuleMap.entrySet()) {
                this.setValues(query, entry.getKey(), entry.getValue(), i+1);
            }
        }
    }

    private int setValues(Query query, String key, SearchRule searchRule, int paramIndex) {
        SearchOperator searchOperator = searchRule.getSearchOperator();
        query.setParameter("param_" + key + paramIndex, this.formtValue(searchOperator, searchRule.getValue()));
        return paramIndex;
    }

    private Object formtValue(SearchOperator operator,Object value) {
        if (operator != SearchOperator.like && operator != SearchOperator.notLike) {
            if (operator != SearchOperator.prefixLike && operator != SearchOperator.prefixNotLike) {
                return operator != SearchOperator.suffixLike && operator != SearchOperator.suffixNotLike ? value : "%" + value;
            } else {
                return value + "%";
            }
        } else {
            return "%" + value + "%";
        }
    }

    public void setPageable(Query query, Searchable searchable) {
        if(searchable.hasPageable()){
            wang.yobbo.common.appengine.entity.Pageable pageAble = searchable.getPage();
            query.setFirstResult((pageAble.getPage()-1) * pageAble.getSize()); //偏移量
            query.setMaxResults(pageAble.getSize());
        }
    }
}
