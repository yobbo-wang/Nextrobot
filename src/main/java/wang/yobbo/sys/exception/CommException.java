package wang.yobbo.sys.exception;

/**
 * Created by xiaoyang on 2017/12/23.
 * 定义公共异常处理类
 */
public class CommException extends RuntimeException{

    public CommException(String message){
        super(message);
    }

    public CommException(){
        super("系统异常");
    }
}
