package top.uhyils.usher.mysql.pojo.DTO;

import java.io.Serializable;
import java.util.List;

/**
 * 局部字符串解析结果
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年08月31日 12时26分
 */
public class ExprParseResultInfo<T> implements Serializable {

    /**
     * 是否是常量
     */
    private boolean isConstant;

    private T result;

    private List<T> listResult;


    private ExprParseResultInfo() {
    }

    /**
     * 快捷创建
     */
    public static <T> ExprParseResultInfo<T> buildConstant(T result) {
        ExprParseResultInfo<T> build = new ExprParseResultInfo<>();
        build.isConstant = true;
        build.result = result;
        return build;
    }

    /**
     * 快捷创建
     */
    public static <T> ExprParseResultInfo<T> buildListConstant(List<T> result) {
        ExprParseResultInfo<T> build = new ExprParseResultInfo<>();
        build.isConstant = false;
        build.listResult = result;
        return build;
    }

    public boolean isConstant() {
        return isConstant;
    }

    public T get() {
        return result;
    }

    public List<T> getListResult() {
        return listResult;
    }

    public T get(Integer index) {
        return listResult.get(index);
    }
}
