package wang.yobbo.sys.exception;

/**
 * Created by xiaoyang on 2017/12/23.
 */
public class ProcessInstancesIsNotInactiveException extends CommException{
    private static final long serialVersionUID = 1L;
    public ProcessInstancesIsNotInactiveException(String message){
        super("当前流程实例正在运行，不能删除！异常信息："+message);
    }
}
