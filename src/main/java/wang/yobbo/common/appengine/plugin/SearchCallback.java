package wang.yobbo.common.appengine.plugin;

import wang.yobbo.common.appengine.entity.Searchable;

import javax.persistence.Query;

public interface SearchCallback {
    SearchCallback DEFAULT = new DefaultSearchCallback(); //默认实例化
    void prepareHQL(StringBuilder hql, Searchable searchable);

    void setValues(Query query, Searchable searchable);

    void setPageable(Query query, Searchable searchable);
}
