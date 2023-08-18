package team.opentech.usher.service;


import team.opentech.usher.pojo.DTO.ParamDTO;
import team.opentech.usher.pojo.cqe.FlushAllSysEvent;

/**
 * 系统参数表(Param)表 内部服务接口
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2022年06月17日 15时53分
 */
public interface ParamService extends BaseDoService<ParamDTO> {

    /**
     * @param event
     *
     * @return
     */
    Boolean flushAllGlobal(FlushAllSysEvent event);

    /**
     * 刷新所有的系统参数
     *
     * @return
     */
    Boolean flushAllGlobal();


}
