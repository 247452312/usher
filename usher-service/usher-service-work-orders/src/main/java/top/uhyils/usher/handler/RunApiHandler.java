package top.uhyils.usher.handler;

import top.uhyils.usher.pojo.temp.InitToRunApiTemporary;
import top.uhyils.usher.pojo.temp.RunToSaveApiTemporary;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年11月22日 13时56分
 */
public interface RunApiHandler {

    /**
     * 自动节点运行
     *
     * @param requestTemporary
     *
     * @return
     */
    RunToSaveApiTemporary run(InitToRunApiTemporary requestTemporary);
}
