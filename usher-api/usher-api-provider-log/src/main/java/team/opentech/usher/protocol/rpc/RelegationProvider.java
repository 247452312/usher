package team.opentech.usher.protocol.rpc;

import team.opentech.usher.pojo.DTO.RelegationDTO;
import team.opentech.usher.pojo.cqe.command.RelegationCoverCommand;
import team.opentech.usher.pojo.cqe.command.RelegationDemotionCommand;
import team.opentech.usher.protocol.rpc.base.DTOProvider;

/**
 * 接口降级策略(Relegation)表 Rpc对外访问层
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年09月27日 09时33分24秒
 */
public interface RelegationProvider extends DTOProvider<RelegationDTO> {

    /**
     * 降级
     *
     * @param cqe
     *
     * @return
     */
    Boolean demotion(RelegationDemotionCommand cqe);


    /**
     * 恢复
     *
     * @param cqe
     *
     * @return
     */
    Boolean recover(RelegationCoverCommand cqe);
}

