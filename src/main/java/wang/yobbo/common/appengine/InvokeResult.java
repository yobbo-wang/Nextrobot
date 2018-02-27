package wang.yobbo.common.appengine;

public class InvokeResult {
    private Object data;
    private String errorMessage;
    private boolean success;

    public InvokeResult() {
    }

    public static InvokeResult success(Object data) {
        InvokeResult result = new InvokeResult();
        result.data = data;
        result.success = true;
        return result;
    }

    public static InvokeResult success() {
        InvokeResult result = new InvokeResult();
        result.success = true;
        return result;
    }

    public static InvokeResult failure(String message) {
        InvokeResult result = new InvokeResult();
        result.success = false;
        result.errorMessage = message;
        return result;
    }

    public static InvokeResult failure(String message, Object data) {
        InvokeResult result = new InvokeResult();
        result.success = false;
        result.errorMessage = message;
        result.data = data;
        return result;
    }

    public Object getData() {
        return this.data;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public boolean isHasErrors() {
        return !this.success;
    }

    public boolean isSuccess() {
        return this.success;
    }
}
