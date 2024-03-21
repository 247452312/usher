package team.opentech.usher.util;

import team.opentech.usher.lang.LongByte;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年03月21日 17时38分
 */
public final class LongByteUtil {

    private LongByteUtil() {
        throw new RuntimeException("util不能被实例化");
    }

    /**
     * 交换
     *
     * @param first       第一个byte数组
     * @param firstIndex  开始位置
     * @param second      第二个byte数组
     * @param secondIndex 开始位置
     * @param size        长度
     */
    public static void swap(LongByte first, int firstIndex, LongByte second, int secondIndex, int size) {
        LongByte firstByte = first.get(firstIndex, size);
        LongByte secondByte = second.get(secondIndex, size);
        first.set(firstIndex, secondByte);
        second.set(secondIndex, firstByte);
    }

}
