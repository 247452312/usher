package team.opentech.usher.ustream;

import java.util.Optional;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年11月27日 09时31分
 */
public class Uobject<T> {


    private T t;

    public Uobject(T t) {
        this.t = t;
    }

    public Uobject() {
    }

    public static <E> Uobject<E> of(E t) {
        return new Uobject<>(t);
    }

    public T get() {
        return t;
    }

    public void set(T t) {
        this.t = t;
    }

    public Optional<T> opt() {
        return Optional.ofNullable(t);
    }
}
