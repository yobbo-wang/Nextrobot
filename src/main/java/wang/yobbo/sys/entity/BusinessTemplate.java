package wang.yobbo.sys.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Type;
import wang.yobbo.common.appengine.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Clob;
import java.util.Date;

@Entity
@Table(name = "NR_BUSINESS_TEMPLATE")
public class BusinessTemplate extends BaseEntity<String> {
    private static final long serialVersionUID = 1L;

    public BusinessTemplate(){}

    public BusinessTemplate(String name, String fileType, String template_json, String id,Date createDate,Date updateDate ) {
        this.name = name;
        this.fileType = fileType;
        this.template_json = template_json;
        super.setId(id);
        super.setCreateDate(createDate);
        super.setUpdateDate(updateDate);
    }

    @Column(name = "NAME", length = 50, nullable = false)
    private String name;

    @Column(name = "FILE_TYPE", length = 30, nullable = false)
    private String fileType;

    //Clob 写入方法：用Clob实现类 SerialClob(char[])
    // 读取方法：Clob.getCharacterStream() 流传给 org.apache.commons.io.IOUtils.toByteArray(Reader, Charset.forName("utf-8"))
    @Column(name = "FILE_CONTENT")
    @JsonIgnore
    private Clob fileContent;

    @Column(name = "TEMPLATE_JSON")
    private String template_json;

    @Column(name = "DISABLE", nullable = false)
    @Type(type = "yes_no")
    private Boolean disable = Boolean.FALSE;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public Clob getFileContent() {
        return fileContent;
    }

    public void setFileContent(Clob fileContent) {
        this.fileContent = fileContent;
    }

    public String getTemplate_json() {
        return template_json;
    }

    public void setTemplate_json(String template_json) {
        this.template_json = template_json;
    }

    public Boolean getDisable() {
        return disable;
    }

    public void setDisable(Boolean disable) {
        this.disable = disable;
    }
}
