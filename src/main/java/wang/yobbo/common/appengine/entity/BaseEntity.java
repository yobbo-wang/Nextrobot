package wang.yobbo.common.appengine.entity;

/**
 * Created by xiaoyang on 2017/12/28.
 *
 */

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

@MappedSuperclass
public abstract class BaseEntity<ID extends Serializable> extends AbstractEntity<ID> {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "ID", length = 32)
    private ID id;

    @Column(name = "CREATE_DATE")
    private Date createDate = new Date();

    @Column(name = "UPDATE_DATE")
    private Date updateDate = new Date();

    public BaseEntity() {
    }

   public ID getId() {
        return this.id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    public Date getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return this.updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getCreateDateStr() {
        if(this.createDate != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
            return simpleDateFormat.format(this.createDate);
        } else {
            return "";
        }
    }

    public String getUpdateDateStr() {
        if(this.updateDate != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
            return simpleDateFormat.format(this.updateDate);
        } else {
            return "";
        }
    }
}
