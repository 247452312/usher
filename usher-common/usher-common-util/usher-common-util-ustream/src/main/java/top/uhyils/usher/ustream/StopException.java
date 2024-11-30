package top.uhyils.usher.ustream;

/**
 * 由于需要流在某些情况下需要停止, 所以需要抛异常,故做一个单例异常减小开销
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年11月28日 09时19分
 */
public class StopException extends RuntimeException {

    public static final StopException INSTANCE = new StopException();

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
