package top.uhyils.usher.util.proxy;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年11月22日 12时31分
 */
public interface ProxySelfObserver<T extends ProxySelfObserver<T>> {

    /**
     * 传本体
     *
     * @param observer
     */
    void selfObserver(T observer);

}
