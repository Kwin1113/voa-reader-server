package xyz.kwin.voa.exception;

/**
 * @author Kwin
 * @since 2020/11/28 上午11:05
 */
public class HttpException extends RuntimeException {
    public HttpException(String message) {
        super(message);
    }

    public HttpException(String message, Throwable cause) {
        super(message, cause);
    }
}
