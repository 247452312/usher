package team.opentech.usher.handler;

import team.opentech.usher.pojo.temp.RunToSaveApiTemporary;
import team.opentech.usher.pojo.temp.SaveToTransApiTemporary;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年11月22日 13时56分
 */
public interface SaveApiHandler {

    /**
     * 自动节点保存
     *
     * @param requestTemporary
     *
     * @return
     */
    SaveToTransApiTemporary save(RunToSaveApiTemporary requestTemporary);

}
