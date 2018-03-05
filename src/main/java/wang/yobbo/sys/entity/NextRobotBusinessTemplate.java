package wang.yobbo.sys.entity;

import wang.yobbo.common.appengine.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Clob;

@Entity
@Table(name = "NEXT_ROBOT_BUSINESS_TEMPLATE")
public class NextRobotBusinessTemplate extends BaseEntity<String> {
    private static final long serialVersionUID = 1L;

    @Column(name = "NAME", length = 50, unique = true, nullable = false)
    private String name;

    @Column(name = "FILE_TYPE", length = 30, nullable = false)
    private String fileType;

    //Clob 写入方法：用Clob实现类 SerialClob(char[])
    // 读取方法：Clob.getCharacterStream() 流传给 org.apache.commons.io.IOUtils.toByteArray(inputStream, Charset.forName("utf-8"))
    @Column(name = "FILE_CONTENT")
    private Clob fileContent;

    @Column(name = "TEMPLATE_JSON")
    private String template_json;

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
}
