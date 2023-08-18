package team.opentech.usher.protocol.rpc;

import team.opentech.usher.pojo.DTO.ApiSubscribeDTO;
import team.opentech.usher.pojo.DTO.request.SubscribeRequest;
import team.opentech.usher.protocol.rpc.base.DTOProvider;

/**
 * api订阅表(ApiSubscribe)表 Rpc对外访问层
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年09月02日 19时46分55秒
 */
public interface ApiSubscribeProvider extends DTOProvider<ApiSubscribeDTO> {

    /**
     * 订阅
     *
     * @param request 订阅请求
     *
     * @return 是否订阅成功
     *
     * @throws InterruptedException
     */
    Boolean subscribe(SubscribeRequest request);

}

