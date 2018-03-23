package wang.yobbo.message.dao.Impl;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import wang.yobbo.common.appengine.entity.Searchable;
import wang.yobbo.common.appengine.dao.Impl.BaseDaoImpl;
import wang.yobbo.message.dao.MessageDao;
import wang.yobbo.message.entity.Message;

import java.util.List;
/**
* 此文件由应用引擎生成，严禁修改，如重复生成时，该文件将被覆盖。
* MessageDaoImpl
* 应用引擎消息管理数据访问实现类
* @author 应用引擎自动生成
* @date 2018-03-23 13:41:12
*
*/
@Component
public class MessageDaoImpl extends BaseDaoImpl<Message, String> implements MessageDao{

    public Message save(Message message) throws Exception{
        return super.save(message);
    }

    public Message findById(String id) {
        return super.findById(id);
    }

    public int delete(String... ids) {
        return super.delete(ids);
    }

    public void delete(Message message) {
        super.delete(message);
    }

    public Long count(Searchable searchable) {
        return super.count(searchable);
    }

    public long count() {
        return super.count();
    }

    public List<Message> findPageWithoutCount(Searchable searchable) {
        return super.findPageWithoutCount(searchable);
    }

    public List<Message> findAll() {
        return super.findAll();
    }

    public List<Message> findAll(Message message) {
        return super.findAll(message);
    }

    public Page<Message> findPage(Searchable searchable) {
        return super.findPage(searchable);
    }

}