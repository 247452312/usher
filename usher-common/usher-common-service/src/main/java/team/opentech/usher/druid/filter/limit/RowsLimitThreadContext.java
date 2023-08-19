package team.opentech.usher.druid.filter.limit;

import team.opentech.usher.UsherThreadLocal;

/**
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月08日 13时35分
 */
public class RowsLimitThreadContext {

    private static UsherThreadLocal<Boolean> rowsLimitEnableThreadLocal = new UsherThreadLocal();

    private static UsherThreadLocal<Integer> rowsLimitSizeThreadLocal = new UsherThreadLocal();

    public static Integer getRowsLimitSizeInCurrentThread() {
        return rowsLimitSizeThreadLocal.get();
    }

    public static void setRowsLimitSizeInCurrentThread(int limitCount) {
        rowsLimitSizeThreadLocal.set(limitCount);
    }

    public static Boolean getRowsLimitEnableInCurrentThread() {
        return rowsLimitEnableThreadLocal.get();
    }

    public static void setRowsLimitEnableInCurrentThread(Boolean isLimit) {
        rowsLimitEnableThreadLocal.set(isLimit);
    }

    public static void clearAll() {
        rowsLimitEnableThreadLocal.remove();
        rowsLimitSizeThreadLocal.remove();
    }

}
