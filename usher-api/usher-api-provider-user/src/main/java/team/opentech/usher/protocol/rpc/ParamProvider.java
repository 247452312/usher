package team.opentech.usher.protocol.rpc;

import team.opentech.usher.pojo.DTO.ParamDTO;
import team.opentech.usher.pojo.cqe.FlushAllSysEvent;
import team.opentech.usher.protocol.rpc.base.DTOProvider;

/**
 * 系统参数表(Param)表 Rpc对外访问层
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2022年06月17日 15时53分
 */
public interface ParamProvider extends DTOProvider<ParamDTO> {

    /**
     * 刷新所有系统参数
     *
     * @param event
     *
     * @return
     */
    Boolean flushAllSys(FlushAllSysEvent event);
}
