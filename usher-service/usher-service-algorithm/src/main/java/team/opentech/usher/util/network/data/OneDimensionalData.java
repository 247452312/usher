package team.opentech.usher.util.network.data;

import team.opentech.usher.util.network.core.Datable;

/**
 * 一维数据
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年07月25日 10时43分
 */
public class OneDimensionalData<T> implements Datable<T> {

    T[] data;

    @Override
    public T[] getData() {
        return data;
    }

    public void setData(T[] data) {
        this.data = data;
    }

    @Override
    public int[] getSize() {
        return new int[]{data.length};
    }

}
