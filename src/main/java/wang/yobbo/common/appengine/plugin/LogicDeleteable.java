package wang.yobbo.common.appengine.plugin;

public interface LogicDeleteable {
    Boolean getDeleted();

    void setDeleted(Boolean var1);

    void markDeleted();
}
