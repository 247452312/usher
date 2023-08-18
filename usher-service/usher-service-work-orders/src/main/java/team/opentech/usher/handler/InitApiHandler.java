package team.opentech.usher.handler;

import team.opentech.usher.pojo.temp.InitApiRequestTemporary;
import team.opentech.usher.pojo.temp.InitToRunApiTemporary;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年11月22日 13时56分
 */
public interface InitApiHandler {

    /**
     * 自动节点初始化
     *
     * @param requestTemporary
     *
     * @return
     */
    InitToRunApiTemporary init(InitApiRequestTemporary requestTemporary);


}
