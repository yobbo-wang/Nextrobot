package wang.yobbo.message.service.Impl;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import wang.yobbo.common.appengine.entity.Searchable;
import wang.yobbo.message.dao.MessageDao;
import wang.yobbo.message.entity.Message;
import wang.yobbo.message.service.MessageService;


import java.util.List;

/**
* 此文件由应用引擎生成，严禁修改，如重复生成时，该文件将被覆盖。
* MessageServiceImpl
* 应用引擎消息管理服务实现类
* @author 应用引擎自动生成
* @date 2018-03-23 13:41:12
*
*/
@Service("messageService")
public class MessageServiceImpl implements MessageService{
    @Autowired
    private MessageDao messageDao;

    public Message save(Message message) throws Exception{
        return this.messageDao.save(message);
    }

    public Message findById(String id) {
        return this.messageDao.findById(id);
    }

    public int delete(String... ids) {
        return this.messageDao.delete(ids);
    }

    public void delete(Message message) {
        this.messageDao.delete(message);
    }

    public Long count(Searchable searchable) {
        return this.messageDao.count(searchable);
    }

    public long count() {
        return this.messageDao.count();
    }

    public List<Message> findPageWithoutCount(Searchable searchable) {
        return this.messageDao.findPageWithoutCount(searchable);
    }

    public List<Message> findAll() {
        return this.messageDao.findAll();
    }

    public List<Message> findAll(Message message) {
        return this.messageDao.findAll(message);
    }

    public Page<Message> findPage(Searchable searchable) {
        return this.messageDao.findPage(searchable);
    }

}
